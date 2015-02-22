package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.LeagueComment;

import java.util.List;

public interface LeagueCommentDAO {

	public void saveLeagueComment(LeagueComment leagueComment);
	public List<LeagueComment> getLeagueComments();
	public LeagueComment getLeagueCommentById(long id);
	public void deleteLeagueComment(long id);
}
