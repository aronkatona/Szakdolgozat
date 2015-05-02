package hu.aronkatona.service.interfaces;

import hu.aronkatona.exceptions.NotSupportedTypeException;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.utils.ExcelUploadInformations;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface TeamService {
	
	public void saveTeam(Team team);
	public List<Team> getTeams();
	public List<Team> getActiveTeamsOrderByPrice();
	public Team getTeamById(long id);
	public void deleteTeam(long id);
	public void downloadExcelTemplateTeams(HttpServletResponse response, boolean withTeams);
	public ExcelUploadInformations<Team> uploadExcelTeams(MultipartFile file) throws NotSupportedTypeException;
	public void updateTeams(List<Team> teams);
	public Team getTeamByIdExcel(long id);
	public boolean existTeamByName(String name);
	public boolean existingTeamByIdAndName(long id, String name);
}
