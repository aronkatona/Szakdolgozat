package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.ResultRace;

import java.util.List;

public interface ResultRaceService {

	public void saveResultRace(ResultRace resultRace);
	public List<ResultRace> getResultRaces();
	public List<ResultRace> getResultRacesByRaceId(long raceId);
	public ResultRace getResultRaceById(long id);
	public void deleteResultRace(long id);
}
