package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.hibernateModel.UserInLeague;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		return sessionFactory.getCurrentSession().createCriteria(User.class,"user").addOrder(Order.desc("user.actualPoint")).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersOrderByActualPoint(int pageNumber) {
		return sessionFactory.getCurrentSession().createCriteria(User.class,"user")
							 .addOrder(Order.desc("user.actualPoint"))
							 .setFirstResult(--pageNumber * 5)
							 .setMaxResults(5)
							 .list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByName(String userName) {
		return sessionFactory.getCurrentSession().createCriteria(User.class)
					.add(Restrictions.like("name", userName, MatchMode.ANYWHERE))
					.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByNameAndNotInLeague(String userName,long leagueId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> userIDS= session.createCriteria(UserInLeague.class)
									   .add(Restrictions.eq("league.id",leagueId))
									   .setProjection(Projections.property("user.id")).list();

		Criteria criteria = session.createCriteria(User.class);
		if(!userIDS.isEmpty()){
			criteria.add(Restrictions.not(Restrictions.in("id", userIDS)));			
		}
		criteria.add(Restrictions.like("name", userName, MatchMode.ANYWHERE)).list();
	    return criteria.list();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByChangePasswordToken(String changePasswordToken) {
		Criteria criteria = sessionFactory.getCurrentSession()	.createCriteria(User.class,"user");
		criteria.add(Restrictions.eq("user.changePasswordToken", changePasswordToken));
		List<User> users = criteria.list();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public User userExistByEmail(String email) {
		return sessionFactory.getCurrentSession().createCriteria(User.class,"user").add(Restrictions.eq("user.email", email)).list().isEmpty() ? null : new User();
	}

	@Override
	public User userExistByName(String name) {
		return sessionFactory.getCurrentSession().createCriteria(User.class,"user").add(Restrictions.eq("user.name", name)).list().isEmpty() ? null : new User();

	}

	@SuppressWarnings("unchecked")
	@Override
	public User userByName(String name) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class,"user").add(Restrictions.eq("user.name", name)).list();
		if(!users.isEmpty()) users.get(0).setPasswordAgain(users.get(0).getPassword());
		return !users.isEmpty() ? users.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User userByEmail(String email) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class,"user").add(Restrictions.eq("user.email", email)).list();
		if(!users.isEmpty()) users.get(0).setPasswordAgain(users.get(0).getPassword());
		return !users.isEmpty() ? users.get(0) : null;
	}

	@Override
	public long getNumberOfRows() {
		return (Long) sessionFactory.getCurrentSession().createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean userExistByNameUpdateProfile(long id, String name) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("name", name)).list();
		if(users.isEmpty()) return false;
		else return users.get(0).getId() != id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean userExistByEmailUpdateProfile(long id, String email) {
		List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).list();
		if(users.isEmpty()) return false;
		else return users.get(0).getId() != id;
	}

	

	

	

	
}
