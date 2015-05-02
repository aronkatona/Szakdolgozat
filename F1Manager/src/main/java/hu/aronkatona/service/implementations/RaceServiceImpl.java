package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.RaceDAO;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.service.interfaces.RaceService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RaceServiceImpl implements RaceService{

	@Autowired
	private RaceDAO raceDAO;
	
	@Override
	public void saveRace(Race race) {
		raceDAO.saveRace(race);
	}

	@Override
	public List<Race> getRaces() {
		return raceDAO.getRaces();
	}

	@Override
	public Race getRaceById(long id) {
		return raceDAO.getRaceById(id);
	}

	@Override
	public void deleteRace(long id) {
		raceDAO.deleteRace(id);
	}

	@Override
	public List<Race> getRacesWithoutResults() {
		return raceDAO.getRacesWithoutResults();
	}

	@Override
	public boolean existingRaceByIdAndDate(long id, Date date) {
		return raceDAO.existingRaceByIdAndDate(id, date);
	}

}
