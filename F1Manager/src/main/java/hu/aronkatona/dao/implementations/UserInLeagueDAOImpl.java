package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.UserInLeagueDAO;
import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
	
	
}
