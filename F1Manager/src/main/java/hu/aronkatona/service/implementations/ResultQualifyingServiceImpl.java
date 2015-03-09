package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.ResultQualifyingDAO;
import hu.aronkatona.hibernateModel.ResultQualifying;
import hu.aronkatona.service.interfaces.ResultQualifyingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ResultQualifyingServiceImpl implements ResultQualifyingService{

	@Autowired
	private ResultQualifyingDAO resultQualifyingDAO;
	
	@Override
	public void saveResultQualifying(ResultQualifying resultQualifying) {
		resultQualifyingDAO.saveResultQualifying(resultQualifying);
	}

	@Override
	public List<ResultQualifying> getResultQualifyings() {
		return resultQualifyingDAO.getResultQualifyings();
	}

	@Override
	public ResultQualifying getResultQualifyingById(long id) {
		return resultQualifyingDAO.getResultQualifyingById(id);
	}

	@Override
	public void deleteResultQualifying(long id) {
		resultQualifyingDAO.deleteResultQualifying(id);
	}

	@Override
	public List<ResultQualifying> getResultQualifyingsByRaceId(long raceId) {
		return resultQualifyingDAO.getResultQualifyingsByRaceId(raceId);
	}

}
