package hu.aronkatona.controllers.admin;

import hu.aronkatona.exceptions.NotSupportedTypeException;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.utils.ExcelUploadInformations;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class ExcelController {
	
	private Logger logger = Logger.getLogger(ExcelController.class);
	
	@Autowired
	private TeamService teamService;

	@RequestMapping(value="/excelFiles")
	public String excelFiles(){
		return "admin/excel";
	}
	
	@RequestMapping(value="/downloadExcelTemplateTeam", method = RequestMethod.POST)
	public void downloadExcelTemplateTeams(HttpServletResponse response){
		teamService.downloadExcelTemplateTeams(response, false);
	}
	
	@RequestMapping(value="/downloadExcelTemplateWithTeams", method = RequestMethod.POST)
	public void downloadExcelTemplateWithTeams(HttpServletResponse response){
		teamService.downloadExcelTemplateTeams(response, true);
	}
	
	@RequestMapping(value="/uploadExcelTeam", method = RequestMethod.POST)
	public String uploadExcelTeam(@RequestParam("file") MultipartFile file, Model model){
		try{
			ExcelUploadInformations<Team> returnList = teamService.uploadExcelTeams(file);	
			if(! returnList.getExcelErrorMessages().isEmpty()){
				model.addAttribute("errors", returnList.getExcelErrorMessages());
			}
			else{
				model.addAttribute("successUpload", true);
			}
		}
		catch(NotSupportedTypeException e){
			model.addAttribute("notSupportedType", true); 
		}
		catch(Exception e){
			logger.error("Excel iras letoltes", e);
			e.printStackTrace();
		}
		return "admin/excel";
	}
}
