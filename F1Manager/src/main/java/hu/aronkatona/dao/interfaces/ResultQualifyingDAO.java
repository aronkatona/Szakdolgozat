package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.ResultQualifying;

import java.util.List;

public interface ResultQualifyingDAO {

	public void saveResultQualifying(ResultQualifying resultQualifying);
	public List<ResultQualifying> getResultQualifyings();
	public List<ResultQualifying> getResultQualifyingsByRaceId(long raceId);
	public ResultQualifying getResultQualifyingById(long id);
	public void deleteResultQualifying(long id);
}
