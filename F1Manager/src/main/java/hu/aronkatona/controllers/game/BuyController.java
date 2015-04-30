package hu.aronkatona.controllers.game;

import hu.aronkatona.exceptions.AlreadyHaveThisDriverException;
import hu.aronkatona.exceptions.AlreadyHaveThisTeamException;
import hu.aronkatona.exceptions.DriverInSameTeamException;
import hu.aronkatona.exceptions.NotEnoughMoneyException;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserResultHistoryService;
import hu.aronkatona.service.interfaces.UserService;
import hu.aronkatona.utils.UserInSession;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BuyController {
	
	private Logger logger = Logger.getLogger(BuyController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserResultHistoryService userResultHistoryService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "game/home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home2(Model model) {
		return "game/home";
	}

	
	@RequestMapping(value="/myTeam")
	public String myTeam(Model model,HttpSession session,final RedirectAttributes redirectAttributes){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			if(userInSession != null){
				model.addAttribute("userInSession", userInSession);
				model.addAttribute("thisIsMe", true);
				model.addAttribute("urhList", userResultHistoryService.getUserResultHistorysByUserId(userInSession.getId()));
				addUnitToModel(model,userInSession.getId());
				return "game/myTeam";
			}
			else{
				redirectAttributes.addFlashAttribute("firstLogin", true);
				return "redirect:loginPage";
			}
			
		}
		catch(Exception e){
			logger.error("", e);
			return "redirect:";
		}
		
	}
	

	@RequestMapping(value="/listDrivers.{position}")
	public String listDrivers(Model model,@PathVariable int position,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			addUnitToModel(model,userInSession.getId());
			model.addAttribute("position", position);
			model.addAttribute("drivers", driverService.getDriversOrderByPrice());
		}
		catch(Exception e){
			logger.error("", e);
			return "redirect:";
		}
		return "game/buyDriver";
	}
	
	@RequestMapping(value="/listTeams.{position}")
	public String listTeams(Model model,@PathVariable int position,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			addUnitToModel(model,userInSession.getId());
			model.addAttribute("position", position);
			model.addAttribute("teams", teamService.getTeamsOrderByPrice());
			
		}
		catch(Exception e){
			logger.error("", e);
			return "redirect:";
		}
		return "game/buyTeam";
	}
	
	
	
	@RequestMapping(value="/sellDriver.{position}")
	public String sellDriver(@PathVariable int position,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userService.sellDriver(userInSession.getId(), position);			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:myTeam";
	}
	
	@RequestMapping(value="/sellTeam.{position}")
	public String sellTeam(@PathVariable int position,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userService.sellTeam(userInSession.getId(), position);			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:myTeam";	
		}
		return "redirect:myTeam";	
	}
	
	@RequestMapping(value="/buyDriver&id={driverId}&pos={position}")
	public String buyDriver(@PathVariable long driverId,@PathVariable int position,final RedirectAttributes redirectAttributes,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userService.buyDriver(userInSession.getId(), driverId, position);
		}
		catch(AlreadyHaveThisDriverException e){
			redirectAttributes.addFlashAttribute("alreadyHaveThisDriver", true);
			return "redirect:listDrivers." + position;
		}
		catch(DriverInSameTeamException e){
			redirectAttributes.addFlashAttribute("driverInSameTeam", true);
			return "redirect:listDrivers." + position;
		}
		catch(NotEnoughMoneyException e){
			redirectAttributes.addFlashAttribute("notEnoughMoney", true);
			return "redirect:listDrivers." + position;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:myTeam";	
		}
		return "redirect:myTeam";	
	}
	
	@RequestMapping(value="/buyTeam&id={teamId}&pos={position}")
	public String buyTeam(@PathVariable long teamId,@PathVariable int position,final RedirectAttributes redirectAttributes,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userService.buyTeam(userInSession.getId(), teamId, position);
		}
		catch(AlreadyHaveThisTeamException e){
			redirectAttributes.addFlashAttribute("alreadyHaveThisTeam", true);
			return "redirect:listTeams." + position;
		}
		catch(NotEnoughMoneyException e){
			redirectAttributes.addFlashAttribute("notEnoughMoney", true);
			return "redirect:listTeams." + position;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:myTeam";
		}
		return "redirect:myTeam";
	}
	
	private void addUnitToModel(Model model,long userId){
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
	}

}
