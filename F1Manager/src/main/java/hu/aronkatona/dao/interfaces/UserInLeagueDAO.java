package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.List;

public interface UserInLeagueDAO {

	public void saveUserInLeague(UserInLeague userInLeague);
	public List<UserInLeague> getUserInLeagues();
	public UserInLeague getUserInLeagueById(long id);
	public void deleteUserInLeague(long id);
}
