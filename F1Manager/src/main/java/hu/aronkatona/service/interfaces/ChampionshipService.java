package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.Championship;

import java.util.List;

public interface ChampionshipService {

	public void saveChampionship(Championship championship);
	public List<Championship> getChampionships();
	public Championship getChampionshipById(long id);
	public void deleteChampionship(long id);
	public boolean existChampionshipThisYear(int year);
}
