package hu.aronkatona.service.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.ChampionshipDAO;
import hu.aronkatona.hibernateModel.Championship;
import hu.aronkatona.service.interfaces.ChampionshipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampionshipServiceImpl implements ChampionshipService{

	@Autowired
	private ChampionshipDAO championshipDAO;

	@Override
	public void saveChampionship(Championship championship) {
		championshipDAO.saveChampionship(championship);
	}

	@Override
	public List<Championship> getChampionships() {
		return championshipDAO.getChampionships();
	}

	@Override
	public Championship getChampionshipById(long id) {
		return championshipDAO.getChampionshipById(id);
	}

	@Override
	public void deleteChampionship(long id) {
		championshipDAO.deleteChampionship(id);
	}
	
	
}
