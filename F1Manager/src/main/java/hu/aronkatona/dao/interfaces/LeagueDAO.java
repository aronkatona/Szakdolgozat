package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.League;

import java.util.List;

public interface LeagueDAO {

	public void saveLeague(League league);
	public List<League> getLeagues();
	public League getLeagueById(long id);
	public void deleteLeague(long id);
}
