package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.LeagueCommentDAO;
import hu.aronkatona.hibernateModel.LeagueComment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeagueCommentDAOImpl implements LeagueCommentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveLeagueComment(LeagueComment leagueComment) {
		sessionFactory.getCurrentSession().saveOrUpdate(leagueComment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeagueComment> getLeagueComments() {
		return sessionFactory.getCurrentSession().createCriteria(LeagueComment.class).list();
	}

	@Override
	public LeagueComment getLeagueCommentById(long id) {
		return (LeagueComment) sessionFactory.getCurrentSession().get(LeagueComment.class, new Long(id));
	}

	@Override
	public void deleteLeagueComment(long id) {
		Session session = sessionFactory.getCurrentSession();
		LeagueComment leagueComment =  (LeagueComment) session.get(LeagueComment.class, new Long(id));
		if(leagueComment != null){
			session.delete(leagueComment);
		}
	}
	
	
}
