package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	private final long USERID = 1;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "game/welcome";
	}
	
	@RequestMapping(value="/units")
	public String units(Model model){
		try{
			addUnitToModel(model);
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/units";
	}
	
	@RequestMapping(value="/listDriver.{position}")
	public String buyDriver(Model model,@PathVariable int position){
		try{
			addUnitToModel(model);
			model.addAttribute("position", position);
			model.addAttribute("drivers", driverService.getDrivers());
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/buyDriver";
	}
	
	@RequestMapping(value="/listTeam.{position}")
	public String buyTeam(Model model,@PathVariable int position){
		try{
			addUnitToModel(model);
			model.addAttribute("position", position);
			model.addAttribute("teams", teamService.getTeams());
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/buyTeam";
	}
	
	//na majd kitoljuk jsonba ha lesz ido
	private void addUnitToModel(Model model){
		User user = userService.getUserById(USERID);
		model.addAttribute("driver1", user.getActualDriver1());
		model.addAttribute("driver2", user.getActualDriver2());
		model.addAttribute("team1", user.getActualTeam1());
		model.addAttribute("team2", user.getActualTeam2());
		model.addAttribute("team3", user.getActualTeam3());
	}
	
	@RequestMapping(value="/sellDriver.{position}")
	public String sellDriver(@PathVariable int position){
		User user = userService.getUserById(USERID);
		switch(position){
			case 1: user.setActualDriver1(null); break;
			case 2: user.setActualDriver2(null); break;
		}
		userService.saveUser(user);
		return "redirect:units";
	}
	
	@RequestMapping(value="/sellTeam.{position}")
	public String sellTeam(@PathVariable int position){
		User user = userService.getUserById(USERID);
		switch(position){
			case 1: user.setActualTeam1(null); break;
			case 2: user.setActualTeam2(null); break;
			case 3: user.setActualTeam3(null); break;
		}
		userService.saveUser(user);
		return "redirect:units";
	}
	
	@RequestMapping(value="/buyDriver&id={id}&pos={position}")
	public String buyDriver(@PathVariable long id,@PathVariable int position){
		User user = userService.getUserById(USERID);
		switch(position){
			case 1: user.setActualDriver1(driverService.getDriverById(id)); break;
			case 2: user.setActualDriver2(driverService.getDriverById(id)); break;
		}
		userService.saveUser(user);
		return "redirect:units";
	}
	
	@RequestMapping(value="/buyTeam&id={id}&pos={position}")
	public String buyTeam(@PathVariable long id,@PathVariable int position){
		User user = userService.getUserById(USERID);
		switch(position){
			case 1: user.setActualTeam1(teamService.getTeamById(id)); break;
			case 2: user.setActualTeam2(teamService.getTeamById(id)); break;
			case 3: user.setActualTeam3(teamService.getTeamById(id)); break;
		}
		userService.saveUser(user);
		return "redirect:units";
	}
}
