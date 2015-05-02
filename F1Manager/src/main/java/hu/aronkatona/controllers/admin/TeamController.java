package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class TeamController {
	
	private Logger logger = Logger.getLogger(TeamController.class);

	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/teams")
	public String teams(Model model){
		try{
			model.addAttribute("teams", teamService.getTeams());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		return "admin/teams";
	}


	@RequestMapping(value="/newTeam")
	public String newTeam(Model model){
		model.addAttribute("team", new Team());
		return "admin/newTeam";
	}
	
	@RequestMapping(value="/modifyTeam&id={id:[0-9]+}")
	public String modifyTeam(Model model, @PathVariable long id){
		try{
			Team team = teamService.getTeamById(id);
			if(team == null) return "redirect:/admin/teams";
			model.addAttribute("team", team);
			return "admin/newTeam";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:/admin/home";
		}		
	}
	
	@RequestMapping(value="/saveTeam", method = RequestMethod.POST)
	public String saveTeam(@Valid @ModelAttribute Team team, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
		    return "admin/newTeam";
		}
		
		if(teamService.existingTeamByIdAndName(team.getId(),team.getName())){
			model.addAttribute("existingTeam",true);
			model.addAttribute("team", team);
			return "admin/newTeam";
		}
		
		try{
			teamService.saveTeam(team);
			logger.info("Csapat mentve: " + team.toString());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "admin/newTeam";
		}
		
		return "redirect:teams";
	}

}
