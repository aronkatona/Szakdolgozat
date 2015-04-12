package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.UserInLeagueDAO;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInLeagueDAOImpl implements UserInLeagueDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveUserInLeague(UserInLeague userInLeague) {
		sessionFactory.getCurrentSession().saveOrUpdate(userInLeague);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInLeague> getUserInLeagues() {
		return sessionFactory.getCurrentSession().createCriteria(UserInLeague.class).list();
	}

	@Override
	public UserInLeague getUserInLeagueById(long id) {
		return (UserInLeague) sessionFactory.getCurrentSession().get(UserInLeague.class, new Long(id));
	}

	@Override
	public void deleteUserInLeague(long id) {
		Session session = sessionFactory.getCurrentSession();
		UserInLeague userInLeague = (UserInLeague) session.get(UserInLeague.class, new Long(id));
		if(userInLeague != null){
			session.delete(userInLeague);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUserInLeague(long leagueId, long userId) {
		List<UserInLeague> list = sessionFactory.getCurrentSession().createCriteria(UserInLeague.class,"userInLeague")
				 .add(Restrictions.eq("userInLeague.league.id", leagueId))
				 .add(Restrictions.eq("userInLeague.user.id", userId)).list();
		return list.size() > 0;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void leaveTheLeague(long leagueId, long userId) {
		List<UserInLeague> userInLeagues = sessionFactory.getCurrentSession().createCriteria(UserInLeague.class,"userInLeague")
								.add(Restrictions.eq("userInLeague.league.id", leagueId))
								.add(Restrictions.eq("userInLeague.user.id", userId)).list();
		if(!userInLeagues.isEmpty()) deleteUserInLeague(userInLeagues.get(0).getId());		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersInLeaguesByLeagueId(long leagueId) {
		return sessionFactory.getCurrentSession().createCriteria(UserInLeague.class)
								.add(Restrictions.eq("league.id",leagueId))
								.createAlias("user", "u")
								.addOrder(Order.desc("u.actualPoint"))
								.setProjection(Projections.property("user")).list();
	}
	
	
}
