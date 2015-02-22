package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.ChampionshipResult;

import java.util.List;

public interface ChampionshipResultService {
	
	public void saveChampionshipResult(ChampionshipResult championshipResult);
	public List<ChampionshipResult> getChampionshipResults();
	public ChampionshipResult getChampionshipResultById(long id);
	public void deleteChampionshipResult(long id);

}
