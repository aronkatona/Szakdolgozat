package hu.aronkatona.controllers.game;

import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LeagueController {

	@Autowired
	private LeagueService leagueService;
	
	private final long USERID = 1;
	
	@RequestMapping(value="/leagues")
	public String leagues(Model model){
		try{
			model.addAttribute("leagues", leagueService.getLeagues());
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/leagues";
	}
	
	@RequestMapping(value="/myLeagues")
	public String myLeagues(Model model){
		try{
			//TODO:
			model.addAttribute("leagues", leagueService.getLeagues());
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/leagues";
	}
	
	@RequestMapping(value="/allLeagues")
	public String allLeagues(Model model){
		try{
			model.addAttribute("leagues", leagueService.getLeagues());
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/leagues";
	}
}
