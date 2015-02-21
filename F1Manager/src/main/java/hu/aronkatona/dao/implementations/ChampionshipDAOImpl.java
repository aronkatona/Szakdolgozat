package hu.aronkatona.dao.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.ChampionshipDAO;
import hu.aronkatona.hibernateModel.Championship;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ChampionshipDAOImpl implements ChampionshipDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveChampionship(Championship championship) {
		sessionFactory.getCurrentSession().saveOrUpdate(championship);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Championship> getChampionships() {
		return sessionFactory.getCurrentSession().createCriteria(Championship.class).list();
	}

	@Override
	public Championship getChampionshipById(long id) {
		return (Championship) sessionFactory.getCurrentSession().get(Championship.class, new Long(id));
	}

	@Override
	public void deleteChampionship(long id) {
		Session session = sessionFactory.getCurrentSession();
		Championship c = (Championship) session.get(Championship.class, new Long(id));
		if(c != null){
			session.delete(c);
		}
	}
	
	
}