package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.TeamDAO;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamDAO teamDAO;

	@Override
	public void saveTeam(Team team) {
		teamDAO.saveTeam(team);
	}
	
	@Override
	public List<Team> getTeams() {
		return teamDAO.getTeams();
	}
	
	@Override
	public List<Team> getTeamsOrderByPrice() {
		return teamDAO.getTeamsOrderByPrice();
	}

	@Override
	public Team getTeamById(long id) {
		return teamDAO.getTeamById(id);
	}

	@Override
	public void deleteTeam(long id) {
		teamDAO.deleteTeam(id);
	}

	



}
