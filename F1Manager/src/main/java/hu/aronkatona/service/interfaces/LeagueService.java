package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.League;

import java.util.List;

public interface LeagueService {
	
	public void saveLeague(League league);
	public List<League> getLeagues();
	public League getLeagueById(long id);
	public void deleteLeague(long id);
}
