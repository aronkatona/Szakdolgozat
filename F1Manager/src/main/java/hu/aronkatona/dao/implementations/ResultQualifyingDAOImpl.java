package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.ResultQualifyingDAO;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.ResultQualifying;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ResultQualifyingDAOImpl implements ResultQualifyingDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveResultQualifying(ResultQualifying resultQualifying) {
		sessionFactory.getCurrentSession().saveOrUpdate(resultQualifying);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultQualifying> getResultQualifyings() {
		return sessionFactory.getCurrentSession().createCriteria(ResultQualifying.class).list();
	}

	@Override
	public ResultQualifying getResultQualifyingById(long id) {
		return (ResultQualifying) sessionFactory.getCurrentSession().get(ResultQualifying.class, new Long(id));
	}

	@Override
	public void deleteResultQualifying(long id) {
		Session session = sessionFactory.getCurrentSession();
		ResultQualifying resultQualifying = (ResultQualifying) session.get(ResultQualifying.class, new Long(id));
		if(resultQualifying != null){
			session.delete(resultQualifying);
		}
	}

}
