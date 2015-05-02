package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.TeamDAO;
import hu.aronkatona.excel.ExcelReader;
import hu.aronkatona.excel.ExcelWriter;
import hu.aronkatona.exceptions.NotSupportedTypeException;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.utils.ExcelUploadInformations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamDAO teamDAO;
	
	@Autowired(required=false)
	private ServletContext context;
	
	@Autowired
	private MessageSource messageSource;

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

	@Override
	public void updateTeams(List<Team> teams) {
		for(Team team : teams){
			saveTeam(team);
		}
	}

	@Override
	public void downloadExcelTemplateTeams(HttpServletResponse response,boolean withTeams) {
		if(withTeams) new ExcelWriter().writeTeam(getTeams(),withTeams, context,response,messageSource);
		else new ExcelWriter().writeTeam(new ArrayList<Team>(),withTeams, context,response,messageSource);
	}

	@Override
	public ExcelUploadInformations<Team> uploadExcelTeams(MultipartFile file) throws NotSupportedTypeException {
		ExcelUploadInformations<Team> returnList = new ExcelReader().readTeams(file, this,messageSource);
		if(returnList.getExcelErrorMessages().isEmpty()){  		
    		updateTeams(returnList.getUpdateObjects());
    	}
		return returnList;
	}

	@Override
	public Team getTeamByIdExcel(long id) {
		return teamDAO.getTeamByIdExcel(id);
	}

	@Override
	public boolean existTeamByName(String name) {
		return teamDAO.existTeamByName(name);
	}


	



}
