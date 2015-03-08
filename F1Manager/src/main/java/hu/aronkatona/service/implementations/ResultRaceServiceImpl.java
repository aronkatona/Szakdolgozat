package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.ResultRaceDAO;
import hu.aronkatona.hibernateModel.ResultRace;
import hu.aronkatona.service.interfaces.ResultRaceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ResultRaceServiceImpl implements ResultRaceService{

	@Autowired
	private ResultRaceDAO resultRaceDAO;
	
	@Override
	public void saveResultRace(ResultRace resultRace) {
		resultRaceDAO.saveResultRace(resultRace);
	}

	@Override
	public List<ResultRace> getResultRaces() {
		return resultRaceDAO.getResultRaces();
	}

	@Override
	public ResultRace getResultRaceById(long id) {
		return resultRaceDAO.getResultRaceById(id);
	}

	@Override
	public void deleteResultRace(long id) {
		resultRaceDAO.deleteResultRace(id);
	}

	
}
