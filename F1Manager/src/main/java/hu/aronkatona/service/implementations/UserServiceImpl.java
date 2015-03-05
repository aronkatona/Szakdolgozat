package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.exceptions.AlreadyHaveThisDriverException;
import hu.aronkatona.exceptions.AlreadyHaveThisTeamException;
import hu.aronkatona.exceptions.DriverInSameTeamException;
import hu.aronkatona.exceptions.NotEnoughMoneyException;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
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
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	/*@Autowired
	private JavaMailSender mailSender;
	
	private final String regLink = "http://localhost:8080/controllers/activationConfirm.";
	*/
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
	//	sendMail(user.getEmail(), "Udv a csapatban", "A kovetkezo linken tudsz regisztralni: <a href=" + regLink  + activationCode + ">reg</a>");
		
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
	
	/*private void sendMail(final String address, final String subject, final String text){
		mailSender.send(new MimeMessagePreparator() {
			  public void prepare(MimeMessage mimeMessage) throws MessagingException {
			    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			    message.setTo(address);
			    message.setSubject(subject);
			    message.setText(text, true);
			  }
			});
	}*/

	@Override
	public User getUserByActivationCode(String activationCode) {
		return userDAO.getUserByActivationCode(activationCode);
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
	
}
