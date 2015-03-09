package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Race;

import java.util.List;

public interface RaceDAO {

	public void saveRace(Race race);
	public List<Race> getRaces();
	public List<Race> getRacesWithoutResults();
	public Race getRaceById(long id);
	public void deleteRace(long id);
}
