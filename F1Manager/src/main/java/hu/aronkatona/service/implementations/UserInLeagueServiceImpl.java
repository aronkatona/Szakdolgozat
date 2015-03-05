package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserInLeagueDAO;
import hu.aronkatona.hibernateModel.UserInLeague;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
import hu.aronkatona.service.interfaces.UserService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	}
	
	
}
