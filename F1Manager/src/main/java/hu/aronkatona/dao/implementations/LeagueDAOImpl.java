package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.LeagueDAO;
import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		return sessionFactory.getCurrentSession().createCriteria(League.class,"league").addOrder(Order.desc("league.avgPoints")).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<League> getLeaguesByUserId(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserInLeague.class,"userInLeague").add(Restrictions.eq("userInLeague.user.id", id));
		//TODO: tragedia, de majd kitalalom
		List<UserInLeague> userInLeagueList = criteria.list();
		List<League> leagues = new ArrayList<>();
		for(UserInLeague u : userInLeagueList){
			leagues.add(u.getLeague());
		}
		return leagues;
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

	@Override
	public boolean isUserCreated(long userId) {
		return sessionFactory.getCurrentSession().createCriteria(League.class,"league")
				 .add(Restrictions.eq("league.creator.id", userId)).list().size() != 0;
	}

	
	
	
}
