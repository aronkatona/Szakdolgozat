package hu.aronkatona.controllers.game;

import hu.aronkatona.exceptions.AlreadyHaveThisDriverException;
import hu.aronkatona.exceptions.AlreadyHaveThisTeamException;
import hu.aronkatona.exceptions.DriverInSameTeamException;
import hu.aronkatona.exceptions.NotEnoughMoneyException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BuyController {
	
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
	
	@RequestMapping(value="/listDrivers.{position}")
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
	
	@RequestMapping(value="/listTeams.{position}")
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
	
	private void addUnitToModel(Model model){
		User user = userService.getUserById(USERID);
		model.addAttribute("user", user);
	}
	
	@RequestMapping(value="/sellDriver.{position}")
	public String sellDriver(@PathVariable int position){
		try{
			userService.sellDriver(USERID, position);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:units";
	}
	
	@RequestMapping(value="/sellTeam.{position}")
	public String sellTeam(@PathVariable int position){
		try{
			userService.sellTeam(USERID, position);			
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:units";	
		}
		return "redirect:units";	
	}
	
	@RequestMapping(value="/buyDriver&id={driverId}&pos={position}")
	public String buyDriver(@PathVariable long driverId,@PathVariable int position,final RedirectAttributes redirectAttributes){
		try{
			userService.buyDriver(USERID, driverId, position);
		}
		catch(AlreadyHaveThisDriverException e){
			redirectAttributes.addFlashAttribute("alreadyHaveThisDriver", "alreadyHaveThisDriver");
			return "redirect:listDrivers." + position;
		}
		catch(DriverInSameTeamException e){
			redirectAttributes.addFlashAttribute("driverInSameTeam", "driverInSameTeam");
			return "redirect:listDrivers." + position;
		}
		catch(NotEnoughMoneyException e){
			redirectAttributes.addFlashAttribute("notEnoughMoney", "notEnoughMoney");
			return "redirect:listDrivers." + position;
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:units";	
		}
		return "redirect:units";	
	}
	
	@RequestMapping(value="/buyTeam&id={teamId}&pos={position}")
	public String buyTeam(@PathVariable long teamId,@PathVariable int position,final RedirectAttributes redirectAttributes){
		try{
			userService.buyTeam(USERID, teamId, position);
		}
		catch(AlreadyHaveThisTeamException e){
			redirectAttributes.addFlashAttribute("alreadyHaveThisTeam", "alreadyHaveThisTeam");
			return "redirect:listTeams." + position;
		}
		catch(NotEnoughMoneyException e){
			redirectAttributes.addFlashAttribute("notEnoughMoney", "notEnoughMoney");
			return "redirect:listTeams." + position;
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:units";
		}
		return "redirect:units";
	}
}
