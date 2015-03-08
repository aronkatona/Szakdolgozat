package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.List;

public interface UserInLeagueService {

	public void saveUserInLeague(UserInLeague userInLeague);
	public void joinToLeague(long leagueId, long userId);
	public void leaveTheLeague(long leagueId, long userId);
	public List<UserInLeague> getUserInLeagues();
	public UserInLeague getUserInLeagueById(long id);
	public void deleteUserInLeague(long id);
	public boolean isUserInLeague(long leagueId, long userId);
}
