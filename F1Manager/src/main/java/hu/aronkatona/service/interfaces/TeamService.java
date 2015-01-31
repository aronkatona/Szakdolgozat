package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.Team;

import java.util.List;


public interface TeamService {
	
	public void saveTeam(Team team);
	public List<Team> getTeams();
	public Team getTeamById(long id);
	public void deleteTeam(long id);


}
