package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.League;

import java.util.List;

public interface LeagueService {
	
	public void saveLeague(League league,long id);
	public List<League> getLeagues();
	public List<League> getLeaguesOrderByName();
	public List<League> getLeaguesByUserIdOrderByName(long id);
	public League getLeagueById(long id);
	public void deleteLeague(long id);
	public boolean isUserCreated(long leagueId,long userId);
	public boolean leagueExistByName(String leagueName);
}
