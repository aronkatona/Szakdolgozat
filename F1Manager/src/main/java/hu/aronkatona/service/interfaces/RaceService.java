package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.Race;

import java.util.List;

public interface RaceService {

	public void saveRace(Race race);
	public List<Race> getRaces();
	public Race getRaceById(long id);
	public void deleteRace(long id);
}
