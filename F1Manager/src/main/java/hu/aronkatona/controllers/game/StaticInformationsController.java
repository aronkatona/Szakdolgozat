package hu.aronkatona.controllers.game;

import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticInformationsController {

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/drivers")
	public String listDrivers(Model model){
		try{
			model.addAttribute("drivers", driverService.getDrivers());
			return "game/drivers";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/teams")
	public String listTeams(Model model){
		try{
			model.addAttribute("teams", teamService.getTeams());
			return "game/teams";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/results")
	public String results(Model model){
		try{
			model.addAttribute("users", userService.getUsers());
			return "game/results";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/rules")
	public String rules(){
		return "game/rules";
	}
	
	
	
}
