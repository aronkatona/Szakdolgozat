package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final String regLink = "http://localhost:8080/controllers/activationConfirm.";
	
	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}
	
	@Override
	public void saveNewUser(User user) {
		user.setRegistrationDate(new Date());
		UUID activationCode = UUID.randomUUID();
		user.setActivationCode(activationCode.toString());
		user.setActivated(false);
		userDAO.saveUser(user);
		sendMail(user.getEmail(), "Udv a csapatban", "A kovetkezo linken tudsz regisztralni: <a href=" + regLink  + activationCode + ">reg</a>");
		
	}
	
	@Override
	public void activateUser(User user) {
		user.setActivated(true);
		userDAO.saveUser(user);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Override
	public User getUserById(long id) {
		return userDAO.getUserById(id);
	}

	@Override
	public void deleteUser(long id) {
		userDAO.deleteUser(id);
	}
	
	private void sendMail(final String address, final String subject, final String text){
		mailSender.send(new MimeMessagePreparator() {
			  public void prepare(MimeMessage mimeMessage) throws MessagingException {
			    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			    message.setTo(address);
			    message.setSubject(subject);
			    message.setText(text, true);
			  }
			});
	}

	@Override
	public User getUserByActivationCode(String activationCode) {
		return userDAO.getUserByActivationCode(activationCode);
	}
	
}
