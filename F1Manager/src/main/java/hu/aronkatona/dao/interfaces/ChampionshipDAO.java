package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Championship;

import java.util.List;

public interface ChampionshipDAO {

	public void saveChampionship(Championship championship);
	public List<Championship> getChampionships();
	public Championship getChampionshipById(long id);
	public void deleteChampionship(long id);
}
