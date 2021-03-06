package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.ResultPointService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserResultHistoryService;
import hu.aronkatona.service.interfaces.UserService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaticInformationsController {

	private Logger logger = Logger.getLogger(StaticInformationsController.class);
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResultHistoryService userResultHistoryService;
	
	@Autowired
	private ResultPointService resultPointService;
	
	@RequestMapping(value="/user&id={userId}")
	public String viewOtherUser(Model model,@PathVariable long userId){
		try{
			User user = userService.getUserById(userId);
			if(user != null){
				model.addAttribute("urhList", userResultHistoryService.getUserResultHistorysByUserId(userId));
				model.addAttribute("user", user);
				return "game/myTeam";		
			}
			else{
				return "redirect:users&page=" + 1;
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/drivers")
	public String listDrivers(Model model){
		try{
			model.addAttribute("drivers", driverService.getActiveDriversOrderByPrice());
			return "game/drivers";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/teams")
	public String listTeams(Model model){
		try{
			model.addAttribute("teams", teamService.getActiveTeamsOrderByPrice());
			return "game/teams";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/users&page={pageNumber}")
	public String results(Model model, @PathVariable int pageNumber){
		try{
			int numberOfPages = (int) Math.ceil((double)userService.getNumberOfRows() / 5);
			model.addAttribute("numberOfPages", numberOfPages);
			
			pageNumber = pageNumber < 1 ? 1 : pageNumber > numberOfPages ? numberOfPages : pageNumber;
			
			int previousPageNumber = pageNumber == 1 ? 1 : pageNumber - 1;
			model.addAttribute("previousPageNumber", previousPageNumber);
			
			int nextPageNumber = pageNumber == numberOfPages ? numberOfPages : pageNumber + 1; 
			model.addAttribute("nextPageNumber", nextPageNumber);
			model.addAttribute("pagination", true);
			
			model.addAttribute("users", userService.getUsersOrderByActualPoint(pageNumber));
			return "game/users";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/searchByUserName", method = RequestMethod.POST)
	public String searchByUserName(Model model,@RequestParam String userName){
		try{
			List<User> users = userService.findUsersByName(userName);
			
			model.addAttribute("users", users);
			return "game/users";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/points")
	public String points(Model model){
		model.addAttribute("points", resultPointService.getResultPoints());
		return "game/points";
	}
	
	@RequestMapping(value="/rules")
	public String rules(){
		return "game/rules";
	}

	
	
}
