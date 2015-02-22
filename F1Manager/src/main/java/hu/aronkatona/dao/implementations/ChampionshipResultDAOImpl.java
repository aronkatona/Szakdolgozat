package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.ChampionshipResultDAO;
import hu.aronkatona.hibernateModel.ChampionshipResult;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ChampionshipResultDAOImpl implements ChampionshipResultDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveChampionshipResult(ChampionshipResult championshipResult) {
		sessionFactory.getCurrentSession().saveOrUpdate(championshipResult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChampionshipResult> getChampionshipResults() {
		return sessionFactory.getCurrentSession().createCriteria(ChampionshipResult.class).list();
	}

	@Override
	public ChampionshipResult getChampionshipResultById(long id) {
		return (ChampionshipResult) sessionFactory.getCurrentSession().get(ChampionshipResult.class, new Long(id));
	}

	@Override
	public void deleteChampionshipResult(long id) {
		Session session = sessionFactory.getCurrentSession();
		ChampionshipResult c = (ChampionshipResult) session.get(ChampionshipResult.class, new Long(id));
		if(c != null){
			session.delete(c);
		}
	}

}
