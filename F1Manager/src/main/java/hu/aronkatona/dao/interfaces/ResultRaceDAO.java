package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.ResultRace;

import java.util.List;

public interface ResultRaceDAO {

	public void saveResultRace(ResultRace resultRace);
	public List<ResultRace> getResultRaces();
	public ResultRace getResultRaceById(long id);
	public void deleteResultRace(long id);
}
