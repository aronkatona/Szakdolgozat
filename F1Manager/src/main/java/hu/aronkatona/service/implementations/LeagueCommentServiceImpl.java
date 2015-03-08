package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.LeagueCommentDAO;
import hu.aronkatona.hibernateModel.LeagueComment;
import hu.aronkatona.service.interfaces.LeagueCommentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LeagueCommentServiceImpl implements LeagueCommentService {

	@Autowired
	private LeagueCommentDAO leagueCommentDAO;

	@Override
	public void saveLeagueComment(LeagueComment leagueComment) {
		leagueCommentDAO.saveLeagueComment(leagueComment);
	}

	@Override
	public List<LeagueComment> getLeagueComments() {
		return leagueCommentDAO.getLeagueComments();
	}

	@Override
	public LeagueComment getLeagueCommentById(long id) {
		return leagueCommentDAO.getLeagueCommentById(id);
	}

	@Override
	public void deleteLeagueComment(long id) {
		leagueCommentDAO.deleteLeagueComment(id);
	}
	
	
}
