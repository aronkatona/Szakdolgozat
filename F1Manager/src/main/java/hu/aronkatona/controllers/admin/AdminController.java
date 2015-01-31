package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/home")
	public String homePage(Model model){
		return "admin/menu";
	}
	
	@RequestMapping(value="/teams")
	public String teams(Model model){
		return "admin/teams";
	}


	@RequestMapping(value="/modifyTeam")
	public String modifyTeam(Model model){
		System.out.println("newteam");
		return "admin/newTeam";
	}
	
	@RequestMapping(value="/modifyTeam/id/{id:[0-9]+}")
	public String newTeam(Model model, @PathVariable long id){
		Team team = teamService.getTeamById(id);
		model.addAttribute("team", team);
		return "admin/newTeam";
	}
	
	@RequestMapping(value="/saveTeam", method = RequestMethod.POST)
	public String saveTeam(@ModelAttribute Team team, BindingResult errors){
		
		if (errors.hasErrors()) {
		    return "admin/newTeam";
		  }
		
		teamService.saveTeam(team);
		return "admin/menu";
	}

}
