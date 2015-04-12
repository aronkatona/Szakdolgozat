package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.LeagueDAO;
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
public class LeagueServiceImpl implements LeagueService {

	@Autowired
	private LeagueDAO leagueDAO;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserInLeagueService userInLeagueService;
	
	@Override
	public void saveLeague(League league,long id) {
		league.setDate(new Date());
		User creator = userService.getUserById(id);
		league.setCreator(creator);
		league.increaseNumberOfUsers();
		leagueDAO.saveLeague(league);
	
		UserInLeague creatorInLeague = new UserInLeague();
		creatorInLeague.setUser(creator);
		creatorInLeague.setLeague(league);
		creatorInLeague.setJoinDate(new Date());
		creatorInLeague.setRole(true);
		creatorInLeague.setCommentsRight(true);
		userInLeagueService.saveUserInLeague(creatorInLeague);
		
	
	}

	@Override
	public List<League> getLeagues() {
		return leagueDAO.getLeagues();
	}
	
	@Override
	public List<League> getLeaguesByUserId(long id) {
		return leagueDAO.getLeaguesByUserId(id);
	}

	@Override
	public League getLeagueById(long id) {
		return leagueDAO.getLeagueById(id);
	}

	@Override
	public void deleteLeague(long id) {
		leagueDAO.deleteLeague(id);
	}

	@Override
	public boolean isUserCreated(long leagueId,long userId) {
		return leagueDAO.isUserCreated(leagueId,userId);
	}

	@Override
	public boolean leagueExistByName(String leagueName) {
		return leagueDAO.leagueExistByName(leagueName);
	}

	
}
