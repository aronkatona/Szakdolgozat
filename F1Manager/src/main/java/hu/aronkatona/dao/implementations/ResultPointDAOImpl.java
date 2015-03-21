package hu.aronkatona.dao.implementations;

import hu.aronkatona.dao.interfaces.ResultPointDAO;
import hu.aronkatona.hibernateModel.ResultPoint;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResultPointDAOImpl implements ResultPointDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveResultPoint(ResultPoint resultPoint) {
		sessionFactory.getCurrentSession().saveOrUpdate(resultPoint);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultPoint> getResultPoints() {
		return sessionFactory.getCurrentSession().createCriteria(ResultPoint.class)
						.addOrder(Order.asc("result")).list();
	}

	@Override
	public ResultPoint getResultPointById(long id) {
		return (ResultPoint) sessionFactory.getCurrentSession().get(ResultPoint.class,new Long(id));
	}
	
	
}
