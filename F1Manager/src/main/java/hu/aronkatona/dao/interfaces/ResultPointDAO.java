package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.ResultPoint;

import java.util.List;

public interface ResultPointDAO {

	public void saveResultPoint(ResultPoint resultPoint);
	public ResultPoint getResultPointById(long id);
	public List<ResultPoint> getResultPoints();
}
