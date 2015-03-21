package hu.aronkatona.service.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.ResultPointDAO;
import hu.aronkatona.hibernateModel.ResultPoint;
import hu.aronkatona.service.interfaces.ResultPointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ResultPointServiceImpl implements ResultPointService{

	@Autowired
	private ResultPointDAO resultPointDAO;

	@Override
	public void saveResultPoint(ResultPoint resultPoint) {
		resultPointDAO.saveResultPoint(resultPoint);
	}

	@Override
	public List<ResultPoint> getResultPoints() {
		return resultPointDAO.getResultPoints();
	}

	@Override
	public ResultPoint getResultPointById(long id) {
		return resultPointDAO.getResultPointById(id);
	}
	
}
