package hu.aronkatona.dao.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.DriverDAO;
import hu.aronkatona.hibernateModel.Driver;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DriverDAOImpl implements DriverDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveDriver(Driver driver) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(driver);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Driver> getDrivers() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Driver.class,"driver").list();
	}

	@Override
	public Driver getDriverById(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Driver) session.get(Driver.class,new Long(id));
	}

	@Override
	public void deleteDriver(long id) {
		Session session = sessionFactory.getCurrentSession();
		Driver driver = (Driver) session.get(Driver.class,new Long(id));
		if(driver != null){
			session.delete(driver);
		}
		
	}

}