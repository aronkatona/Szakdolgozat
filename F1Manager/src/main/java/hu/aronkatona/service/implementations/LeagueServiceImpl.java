package hu.aronkatona.service.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.LeagueDAO;
import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.service.interfaces.LeagueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueServiceImpl implements LeagueService {

	@Autowired
	private LeagueDAO leagueDAO;

	@Override
	public void saveLeague(League league) {
		leagueDAO.saveLeague(league);
	}

	@Override
	public List<League> getLeagues() {
		return leagueDAO.getLeagues();
	}

	@Override
	public League getLeagueById(long id) {
		return leagueDAO.getLeagueById(id);
	}

	@Override
	public void deleteLeague(long id) {
		leagueDAO.deleteLeague(id);
	}
}
