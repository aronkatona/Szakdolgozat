package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.ResultPoint;

import java.util.List;

public interface ResultPointService {

	public void saveResultPoint(ResultPoint resultPoint);
	public ResultPoint getResultPointById(long id);
	public List<ResultPoint> getResultPoints();
}
