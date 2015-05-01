package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.exceptions.AlreadyHaveThisDriverException;
import hu.aronkatona.exceptions.AlreadyHaveThisTeamException;
import hu.aronkatona.exceptions.DriverInSameTeamException;
import hu.aronkatona.exceptions.NotEnoughMoneyException;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.saltAndHash.SaltAndHash;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
	private LeagueService leagueService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MessageSource messageSource;
	
	private String DOMAIN;
	private String NEWPASSWORDLINK = "newPassword.";
	private String REGLINK = "activationConfirm.";
	private final long USERSTARTMONEY = 10000;
	
	private final String EMAILREGISTRATIONSUBJECT = "**message.email.registration.subject**";
	private final String EMAILREGISTRATIONCONTENT1 = "**message.email.registration.content1**";
	private final String EMAILREGISTRATIONCONTENT2 = "**message.email.registration.content2**";
	private final String EMAILREGISTRATIONHREF = "**message.email.registration.href**";
	
	private final String EMAILACTIVATIONSUBJECT = "**message.email.activation.subject**";
	private final String EMAILACTIVATIONCONTENT1 = "**message.email.activation.content1**";
	private final String EMAILACTIVATIONCONTENT2 = "**message.email.activation.content2**";
	private final String EMAILACTIVATIONHREF = "**message.email.activation.href**";
	
	private final String EMAILNEWPASSWORDSUBJECT = "**message.email.new_password.subject**";
	private final String EMAILNEWPASSWORDCONTENT = "**message.email.new_password.content**";
	private final String EMAILNEWPASSWORDHREF = "**message.email.new_password.href**";
	
	private final String EMAILLEAGUEINVITESUBJECT = "**message.email.league_invite.subject**";
	private final String EMAILLEAGUEINVITECONTENT1 = "**message.email.league_invite.content1**";
	private final String EMAILLEAGUEINVITECONTENT2 = "**message.email.league_invite.content2**";
	private final String EMAILLEAGUEINVITECONTENT3 = "**message.email.league_invite.content3**";
	private final String EMAILLEAGUEINVITEHREF = "**message.email.league_invite.href**";
	
	@Override
	public void saveNewUser(final User user) {
		user.setRegistrationDate(new Date());
		final UUID activationCode = UUID.randomUUID();
		user.setActivationCode(activationCode.toString());
		user.setActivated(false);
		user.setActualMoney(USERSTARTMONEY);
		try{
			user.setPassword(SaltAndHash.createHash(user.getPassword()));	
		}
		catch(Exception e){
			logger.error("SaltAndHash error", e);
			e.printStackTrace();
		}
		saveUser(user);
	
		DOMAIN = getPropertyValueFromApplicationProperties("domainName");	
		final String EMAILSUBJECTTMP = messageSource.getMessage(EMAILREGISTRATIONSUBJECT, null , Locale.forLanguageTag("hu"));
		final String EMAILCONTENT1TMP = messageSource.getMessage(EMAILREGISTRATIONCONTENT1, null , Locale.forLanguageTag("hu"));
		final String EMAILCONTENT2TMP = messageSource.getMessage(EMAILREGISTRATIONCONTENT2, null , Locale.forLanguageTag("hu"));
		final String EMAILREGISTRATIONHREFTMP = messageSource.getMessage(EMAILREGISTRATIONHREF, null , Locale.forLanguageTag("hu"));
		new Thread(){
			@Override
			public void run(){
				sendMail(user.getEmail(), EMAILSUBJECTTMP,EMAILCONTENT1TMP + activationCode + "<br>" +
						 EMAILCONTENT2TMP +  "<a href='" + DOMAIN + REGLINK  + activationCode +  "'>" + EMAILREGISTRATIONHREFTMP + "</a>");				
			};
		}.start();
		
	}
	
	@Override
	public void saveUser(User user) {
		user.setPasswordAgain(user.getPassword());
		userDAO.saveUser(user);
	}
	
	
	@Override
	public void saveUserWithNewPassword(User user) {
		try{
			user.setPassword(SaltAndHash.createHash(user.getPassword()));
			UUID changePasswordToken = UUID.randomUUID();
			user.setChangePasswordToken(changePasswordToken.toString());
		}
		catch(Exception e){
			logger.error("SaltAndHash error", e);
			e.printStackTrace();
		}
		saveUser(user);
	}
	
	
	@Override
	public User getUserByChangePasswordToken(String changePasswordToken) {
		User user = userDAO.getUserByChangePasswordToken(changePasswordToken);
		return user != null ? user: null;
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
		saveUser(user);
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
		saveUser(user);
	}
	
	@Override
	public void activateUser(User user) {
		user.setActivated(true);
		saveUser(user);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
	
	@Override
	public List<User> getUsersOrderByActualPoint(int pageNumber) {
		return userDAO.getUsersOrderByActualPoint(pageNumber);
	}

	@Override
	public User getUserById(long id) {
		User user = userDAO.getUserById(id);
		return user != null ? user: null;
	}

	@Override
	public void deleteUser(long id) {
		userDAO.deleteUser(id);
	}

	@Override
	public User getUserByActivationCode(String activationCode) {
		User user = userDAO.getUserByActivationCode(activationCode);
		return user != null ? user: null;
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
		saveUser(user);
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
		saveUser(user);
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
	public void sendMailToUserWithNewActivationCode(long id) {
		
		final String EMAILACTIVATIONSUBJECTTMP = messageSource.getMessage(EMAILACTIVATIONSUBJECT, null , Locale.forLanguageTag("hu"));
		final String EMAILACTIVATIONCONTENT1TMP = messageSource.getMessage(EMAILACTIVATIONCONTENT1, null , Locale.forLanguageTag("hu"));
		final String EMAILACTIVATIONCONTENT2TMP = messageSource.getMessage(EMAILACTIVATIONCONTENT2, null , Locale.forLanguageTag("hu"));
		final String EMAILACTIVATIONHREFTMP = messageSource.getMessage(EMAILACTIVATIONHREF, null , Locale.forLanguageTag("hu"));
		
		User user = getUserById(id);
		UUID activationCode = UUID.randomUUID();
		user.setActivationCode(activationCode.toString());
		saveUser(user);
		DOMAIN = getPropertyValueFromApplicationProperties("domainName");
		sendMail(user.getEmail(), EMAILACTIVATIONSUBJECTTMP, EMAILACTIVATIONCONTENT1TMP + activationCode + "<br>"+
																EMAILACTIVATIONCONTENT2TMP + " <a href='" + DOMAIN + REGLINK  + activationCode + "'>" + EMAILACTIVATIONHREFTMP + "</a>");
	}

	@Override
	public void sendNewPasswordToken(String email) {
		
		
		
		final String EMAILNEWPASSWORDSUBJECTTMP = messageSource.getMessage(EMAILNEWPASSWORDSUBJECT, null , Locale.forLanguageTag("hu"));
		final String EMAILNEWPASSWORDCONTENTTMP = messageSource.getMessage(EMAILNEWPASSWORDCONTENT, null , Locale.forLanguageTag("hu"));
		final String EMAILNEWPASSWORDHREFTMP = messageSource.getMessage(EMAILNEWPASSWORDHREF, null , Locale.forLanguageTag("hu"));
		
		User user = userDAO.userByEmail(email);
		if(user != null){
			UUID changePasswordToken = UUID.randomUUID();
			user.setChangePasswordToken(changePasswordToken.toString());
			saveUser(user);
			DOMAIN = getPropertyValueFromApplicationProperties("domainName");
			sendMail(user.getEmail(),EMAILNEWPASSWORDSUBJECTTMP,
					EMAILNEWPASSWORDCONTENTTMP + " <a href='" + DOMAIN + NEWPASSWORDLINK  + changePasswordToken + "'>" + EMAILNEWPASSWORDHREFTMP + "</a>");

		}
	}
	
	@Override
	public void inviteUserToLeagueWithEmail(long leagueId, long userId,String inviterName) {		
		
		final String EMAILLEAGUEINVITESUBJECTTMP = messageSource.getMessage(EMAILLEAGUEINVITESUBJECT, null , Locale.forLanguageTag("hu"));
		final String EMAILLEAGUEINVITECONTENT1TMP = messageSource.getMessage(EMAILLEAGUEINVITECONTENT1, null , Locale.forLanguageTag("hu"));
		final String EMAILLEAGUEINVITECONTENT2TMP = messageSource.getMessage(EMAILLEAGUEINVITECONTENT2, null , Locale.forLanguageTag("hu"));
		final String EMAILLEAGUEINVITECONTENT3TMP = messageSource.getMessage(EMAILLEAGUEINVITECONTENT3, null , Locale.forLanguageTag("hu"));
		final String EMAILLEAGUEINVITEHREFTMP = messageSource.getMessage(EMAILLEAGUEINVITEHREF, null , Locale.forLanguageTag("hu"));
		
		User user = getUserById(userId);
		League league = leagueService.getLeagueById(leagueId);
		DOMAIN = getPropertyValueFromApplicationProperties("domainName");
		sendMail(user.getEmail(),EMAILLEAGUEINVITESUBJECTTMP, inviterName + EMAILLEAGUEINVITECONTENT1TMP + league.getName() + EMAILLEAGUEINVITECONTENT2TMP
						+ "<br>" +
						EMAILLEAGUEINVITECONTENT3TMP + " <a href='" + DOMAIN + "joinToLeague&leagueId=" + leagueId + "&userId=" + userId + "'>" + EMAILLEAGUEINVITEHREFTMP + "</a>");
	}

	@Override
	public long getNumberOfRows() {
		return userDAO.getNumberOfRows();
	}

	@Override
	public List<User> findUsersByName(String userName) {
		return userDAO.findUsersByName(userName);
	}

	@Override
	public List<User> findUsersByNameAndNotInLeague(String userName,long leagueId) {
		return userDAO.findUsersByNameAndNotInLeague(userName, leagueId);
	}

	
	private String getPropertyValueFromApplicationProperties(String property){
		Properties properties=new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		try{
			properties.load(inputStream);			
			inputStream.close();
		}
		catch(Exception e){
			logger.error("Application Properties error", e);
			e.printStackTrace();
			return "";
		}
		return properties.getProperty(property);
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
	public boolean userExistByNameUpdateProfile(long id, String name) {
		return userDAO.userExistByNameUpdateProfile(id,name);
	}

	@Override
	public boolean userExistByEmailUpdateProfile(long id, String email) {
		return userDAO.userExistByEmailUpdateProfile(id,email);
	}

	
}
