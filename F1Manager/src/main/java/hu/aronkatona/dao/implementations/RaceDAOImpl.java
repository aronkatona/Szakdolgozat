package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.RaceDAO;
import hu.aronkatona.hibernateModel.Race;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RaceDAOImpl implements RaceDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveRace(Race race) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(race);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Race> getRaces() {
		return sessionFactory.getCurrentSession().createCriteria(Race.class).addOrder(Order.asc("date")).list();
	}

	@Override
	public Race getRaceById(long id) {
		return (Race) sessionFactory.getCurrentSession().get(Race.class, new Long(id));
	}

	@Override
	public void deleteRace(long id) {
		Session session = sessionFactory.getCurrentSession();
		Race race = (Race) session.get(Race.class, new Long(id));
		if(race != null){
			session.delete(race);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Race> getRacesWithoutResults() {
		return sessionFactory.getCurrentSession().createCriteria(Race.class,"race").add(Restrictions.eq("race.resultSet", false)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existingRaceByIdAndDate(long id, Date date) {
		Session session = sessionFactory.getCurrentSession();
		
		List<Race> races = session.createCriteria(Race.class).add(Restrictions.eq("date", date)).list();
		
		if(races.isEmpty()) return false;
		
		session.evict(races.get(0));
		
		if(id == 0 ) return true;
		else return races.get(0).getId() != id;		
	}

}
