package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.ResultRaceDAO;
import hu.aronkatona.hibernateModel.ResultRace;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResultRaceDAOImpl implements ResultRaceDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveResultRace(ResultRace resultRace) {
		sessionFactory.getCurrentSession().saveOrUpdate(resultRace);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultRace> getResultRaces() {
		return sessionFactory.getCurrentSession().createCriteria(ResultRace.class).list();
	}

	@Override
	public ResultRace getResultRaceById(long id) {
		return (ResultRace) sessionFactory.getCurrentSession().get(ResultRace.class,new Long(id));
	}

	@Override
	public void deleteResultRace(long id) {
		Session session = sessionFactory.getCurrentSession();
		ResultRace resultRace = (ResultRace) session.get(ResultRace.class,new Long(id));
		if(resultRace != null){
			session.delete(resultRace);
		}
	}

}
