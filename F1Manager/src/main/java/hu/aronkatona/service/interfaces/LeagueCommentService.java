package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.LeagueComment;

import java.util.List;

public interface LeagueCommentService {

	public void saveLeagueComment(LeagueComment leagueComment);
	public List<LeagueComment> getLeagueComments();
	public LeagueComment getLeagueCommentById(long id);
	public void deleteLeagueComment(long id);
}
