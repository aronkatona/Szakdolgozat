package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Team;

import java.util.List;


public interface TeamDAO {
	
	public void saveTeam(Team team);
	public List<Team> getTeams();
	public List<Team> getActiveTeamsOrderByPrice();
	public Team getTeamById(long id);
	public void deleteTeam(long id);
	public Team getTeamByIdExcel(long id);
	public boolean existTeamByName(String name);
	public boolean existingTeamByIdAndName(long id, String name);
}
