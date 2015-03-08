package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.ChampionshipResultDAO;
import hu.aronkatona.hibernateModel.ChampionshipResult;
import hu.aronkatona.service.interfaces.ChampionshipResultService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ChampionshipResultServiceImpl implements ChampionshipResultService{

	@Autowired
	private ChampionshipResultDAO championshipResultDAO;
	
	@Override
	public void saveChampionshipResult(ChampionshipResult championshipResult) {
		championshipResultDAO.saveChampionshipResult(championshipResult);
	}

	@Override
	public List<ChampionshipResult> getChampionshipResults() {
		return championshipResultDAO.getChampionshipResults();
	}

	@Override
	public ChampionshipResult getChampionshipResultById(long id) {
		return championshipResultDAO.getChampionshipResultById(id);
	}

	@Override
	public void deleteChampionshipResult(long id) {
		championshipResultDAO.deleteChampionshipResult(id);
	}

	
}
