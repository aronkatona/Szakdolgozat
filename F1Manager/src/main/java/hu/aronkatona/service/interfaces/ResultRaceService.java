package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.ResultRace;

import java.util.List;

public interface ResultRaceService {

	public void saveResultRace(ResultRace resultRace);
	public List<ResultRace> getResultRaces();
	public ResultRace getResultRaceById(long id);
	public void deleteResultRace(long id);
}
