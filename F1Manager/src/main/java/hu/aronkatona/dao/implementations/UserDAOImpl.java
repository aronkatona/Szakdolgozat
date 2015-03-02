package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.hibernateModel.User;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		return sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

	@Override
	public User getUserById(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, new Long(id));
	}

	@Override
	public void deleteUser(long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, new Long(id));
		if(user != null){
			session.delete(user);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByActivationCode(String activationCode) {
		Criteria criteria = sessionFactory.getCurrentSession()	.createCriteria(User.class,"user");
		criteria.add(Restrictions.eq("user.activationCode", activationCode));
		List<User> users = criteria.list();
		return users.isEmpty() ? null : users.get(0);
	}

	
}
