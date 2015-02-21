package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.UserResultHistoryDAO;
import hu.aronkatona.hibernateModel.UserResultHistory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserResultHistoryDAOImpl implements UserResultHistoryDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveUserResultHistory(UserResultHistory userResultHistory) {
		sessionFactory.getCurrentSession().saveOrUpdate(userResultHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserResultHistory> getUserResultHistorys() {
		return sessionFactory.getCurrentSession().createCriteria(UserResultHistory.class).list();
	}

	@Override
	public UserResultHistory getUserResultHistoryById(long id) {
		return (UserResultHistory) sessionFactory.getCurrentSession().get(UserResultHistory.class, new Long(id));
	}

	@Override
	public void deleteUserResultHistory(long id) {
		Session session = sessionFactory.getCurrentSession();
		UserResultHistory userResultHistory = (UserResultHistory) session.get(UserResultHistory.class, new Long(id));
		if(userResultHistory != null){
			session.delete(userResultHistory);
		}
	}
	
	
}