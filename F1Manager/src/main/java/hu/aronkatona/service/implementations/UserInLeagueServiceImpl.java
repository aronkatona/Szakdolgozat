package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserInLeagueDAO;
import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.hibernateModel.UserInLeague;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
import hu.aronkatona.service.interfaces.UserService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserInLeagueServiceImpl implements UserInLeagueService{

	@Autowired
	private UserInLeagueDAO userInLeagueDAO;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserService userService;
	

	@Override
	public void saveUserInLeague(UserInLeague userInLeague) {
		userInLeagueDAO.saveUserInLeague(userInLeague);
	}

	@Override
	public List<UserInLeague> getUserInLeagues() {
		return userInLeagueDAO.getUserInLeagues();
	}

	@Override
	public UserInLeague getUserInLeagueById(long id) {
		return userInLeagueDAO.getUserInLeagueById(id);
	}

	@Override
	public void deleteUserInLeague(long id) {
		userInLeagueDAO.deleteUserInLeague(id);
	}

	@Override
	public void joinToLeague(long leagueId, long userId) {
		UserInLeague userInLeague = new UserInLeague();
		userInLeague.setJoinDate(new Date());
		userInLeague.setRole(false);
		userInLeague.setCommentsRight(true);
		userInLeague.setLeague(leagueService.getLeagueById(leagueId));
		userInLeague.setUser(userService.getUserById(userId));
		saveUserInLeague(userInLeague);
		
		League league = leagueService.getLeagueById(leagueId);
		league.increaseNumberOfUsers();
	}
	
	@Override
	public void leaveTheLeague(long leagueId, long userId) {
		League league = leagueService.getLeagueById(leagueId);
		league.decreaseNumberOfUsers();
		userInLeagueDAO.leaveTheLeague(leagueId, userId);
	}

	@Override
	public boolean isUserInLeague(long leagueId, long userId) {
		return userInLeagueDAO.isUserInLeague(leagueId, userId);
	}

	@Override
	public List<User> getUsersInLeaguesByLeagueId(long leagueId) {
		return userInLeagueDAO.getUsersInLeaguesByLeagueId(leagueId);
	}






	
	
	
}
