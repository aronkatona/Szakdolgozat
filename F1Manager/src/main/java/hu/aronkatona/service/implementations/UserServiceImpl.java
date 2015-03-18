package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.exceptions.AlreadyHaveThisDriverException;
import hu.aronkatona.exceptions.AlreadyHaveThisTeamException;
import hu.aronkatona.exceptions.DriverInSameTeamException;
import hu.aronkatona.exceptions.NotEnoughMoneyException;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.saltAndHash.SaltAndHash;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final String REGLINK = "http://localhost:8080/controllers/activationConfirm.";
	
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
		try{
			user.setPassword(SaltAndHash.createHash(user.getPassword()));	
			user.setPasswordAgain(user.getPassword());
		}
		catch(Exception e){
			logger.error("SaltAndHash error", e);
			e.printStackTrace();
		}
		userDAO.saveUser(user);
		sendMail(user.getEmail(), "Udv a csapatban", "A kovetkezo linken tudsz regisztralni: <a href=" + REGLINK  + activationCode + ">aktival</a>");
		
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
		User user = userDAO.getUserById(id);
		user.setPasswordAgain(user.getPassword());
		return user;
	}

	@Override
	public void deleteUser(long id) {
		userDAO.deleteUser(id);
	}

	@Override
	public User getUserByActivationCode(String activationCode) {
		User user = userDAO.getUserByActivationCode(activationCode);
		user.setPasswordAgain(user.getPassword());
		return user;
	}

	@Override
	public void sellDriver(long userId, int position) {
		User user = getUserById(userId);
		switch(position){
			case 1: 
					user.increaseActualMoney(user.getActualDriver1().getPrice());
					user.setActualDriver1(null); 
					break;
					
			case 2: 
					user.increaseActualMoney(user.getActualDriver2().getPrice());
					user.setActualDriver2(null);
					break;
		}
		userDAO.saveUser(user);
	}

	@Override
	public void sellTeam(long userId, int position) {
		User user = getUserById(userId);
		switch(position){
			case 1: 
					user.increaseActualMoney(user.getActualTeam1().getPrice());
					user.setActualTeam1(null);
					break;
					
			case 2: 
					user.increaseActualMoney(user.getActualTeam2().getPrice());
					user.setActualTeam2(null);
					break;
					
			case 3: 
					user.increaseActualMoney(user.getActualTeam3().getPrice());
					user.setActualTeam3(null);
					break;
					
			
		}
		userDAO.saveUser(user);
	}

	@Override
	public void buyDriver(long userId, long driverId, int position) throws Exception{
		User user = getUserById(userId);
		Driver driver = driverService.getDriverById(driverId);

		switch(position){
			case 1: 
					if(user.getActualDriver2() != null){
						if(driver.equals(user.getActualDriver2())) throw new AlreadyHaveThisDriverException();
						if(driver.getTeam().equals(user.getActualDriver2().getTeam())) throw new DriverInSameTeamException();
					}
					if(user.getActualMoney() < driver.getPrice()) throw new NotEnoughMoneyException();
					user.decreaseActualMoney(driver.getPrice());
					user.setActualDriver1(driver); 
					break;
					
			case 2: 
					if(user.getActualDriver1() != null){
						if(driver.equals(user.getActualDriver1())) throw new AlreadyHaveThisDriverException();
						if(driver.getTeam().equals(user.getActualDriver1().getTeam())) throw new DriverInSameTeamException();
					}
					if(user.getActualMoney() < driver.getPrice()) throw new NotEnoughMoneyException();
					user.decreaseActualMoney(driver.getPrice());
					user.setActualDriver2(driver); 
					break;
		}
		userDAO.saveUser(user);
	}

	@Override
	public void buyTeam(long userId, long teamId, int position) throws Exception{
		User user = getUserById(userId);
		Team team = teamService.getTeamById(teamId);
		
		
		switch(position){
			case 1: 
					if(user.getActualTeam2() != null && team.equals(user.getActualTeam2())) throw new AlreadyHaveThisTeamException();
					if(user.getActualTeam3() != null && team.equals(user.getActualTeam3())) throw new AlreadyHaveThisTeamException();
					if(user.getActualMoney() < team.getPrice()) throw new NotEnoughMoneyException();
					user.decreaseActualMoney(team.getPrice());
					user.setActualTeam1(team); 
					break;
			case 2: 
					if(user.getActualTeam1() != null && team.equals(user.getActualTeam1())) throw new AlreadyHaveThisTeamException();
					if(user.getActualTeam3() != null && team.equals(user.getActualTeam3())) throw new AlreadyHaveThisTeamException();
					if(user.getActualMoney() < team.getPrice()) throw new NotEnoughMoneyException();
					user.decreaseActualMoney(team.getPrice());
					user.setActualTeam2(team); 
					break;
			case 3: 
					if(user.getActualTeam1() != null && team.equals(user.getActualTeam1())) throw new AlreadyHaveThisTeamException();
					if(user.getActualTeam2() != null && team.equals(user.getActualTeam2())) throw new AlreadyHaveThisTeamException();
					if(user.getActualMoney() < team.getPrice()) throw new NotEnoughMoneyException();
					user.decreaseActualMoney(team.getPrice());
					user.setActualTeam3(team); 
					break;
		}
		userDAO.saveUser(user);
	}

	@Override
	public User userExistByEmail(String email) {
		return userDAO.userExistByEmail(email);
	}

	@Override
	public User userExistByName(String name) {
		return userDAO.userExistByName(name);
	}

	@Override
	public User userLogin(String name, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = userDAO.userByName(name);
		return user != null && SaltAndHash.validatePassword(password, user.getPassword())   ? user : null;
	}

	@Override
	public boolean sendMailToUser(long id) {
		User user = getUserById(id);
		UUID activationCode = UUID.randomUUID();
		user.setActivationCode(activationCode.toString());
		userDAO.saveUser(user);
		sendMail(user.getEmail(), "Aktivációs kód", "Az új aktivációs kódod: " + activationCode + "<br>"+
													"Ha idekattintasz is aktivalod: <a href=" + REGLINK  + activationCode + ">aktival</a>");
		return false;
	}
	
}
