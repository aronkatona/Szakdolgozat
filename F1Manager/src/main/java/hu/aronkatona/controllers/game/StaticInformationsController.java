package hu.aronkatona.controllers.game;

import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticInformationsController {

	private Logger logger = Logger.getLogger(StaticInformationsController.class);
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/drivers")
	public String listDrivers(Model model){
		try{
			model.addAttribute("drivers", driverService.getDriversOrderByPrice());
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
			model.addAttribute("teams", teamService.getTeamsOrderByPrice());
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
			
			model.addAttribute("users", userService.getUsersOrderByActualPoint(pageNumber));
			return "game/users";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/rules")
	public String rules(){
		return "game/rules";
	}
	
	
	
}
