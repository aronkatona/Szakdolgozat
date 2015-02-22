package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.LeagueDAO;
import hu.aronkatona.hibernateModel.League;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class LeagueDAOImpl implements LeagueDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveLeague(League league) {
		sessionFactory.getCurrentSession().saveOrUpdate(league);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<League> getLeagues() {
		return sessionFactory.getCurrentSession().createCriteria(League.class).list();
	}

	@Override
	public League getLeagueById(long id) {
		return (League) sessionFactory.getCurrentSession().get(League.class,new Long(id));
	}

	@Override
	public void deleteLeague(long id) {
		Session session = sessionFactory.getCurrentSession();
		League league =  (League) session.get(League.class, new Long(id));
		if(league != null){
			session.delete(league);
		}
	}
	
	
}
