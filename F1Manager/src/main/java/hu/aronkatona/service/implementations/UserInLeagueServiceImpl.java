package hu.aronkatona.service.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.UserInLeagueDAO;
import hu.aronkatona.hibernateModel.UserInLeague;
import hu.aronkatona.service.interfaces.UserInLeagueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInLeagueServiceImpl implements UserInLeagueService{

	@Autowired
	private UserInLeagueDAO userInLeagueDAO;

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
	
	
}
