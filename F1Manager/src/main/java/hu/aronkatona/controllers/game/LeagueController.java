package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
import hu.aronkatona.service.interfaces.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LeagueController {

	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserInLeagueService userInLeagueService;
	
	@Autowired
	private UserService userService;
	
	private final long USERID1 = 1;
	private final long USERID2 = 2;
	
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
	
	
	@RequestMapping(value="/myLeagues")
	public String myLeagues(Model model){
		try{
			model.addAttribute("leagues", leagueService.getLeaguesByUserId(USERID2));
		}
		catch(Exception e){
			return "redirect:";
		}
		return "game/leagues";
	}
	
	@RequestMapping(value="/createLeague")
	public String createLeague(Model model){
		model.addAttribute("league", new League());
		return "game/createLeague";
	}
	
	@RequestMapping(value="/saveLeague", method = RequestMethod.POST)
	public String saveLeague(Model model,@Valid @ModelAttribute League league, BindingResult errors){
		
		if(errors.hasErrors()){
			return "game/createLeague";
		}
		
		try{
			leagueService.saveLeague(league,USERID1);
			return "redirect:";
		}
		catch(Exception e){
			e.printStackTrace();
			return "game/createLeague";
		}
		
	}
	
	@RequestMapping(value="/league&id={leagueId}")
	public String leagueStatistic(@PathVariable long leagueId, Model model){
		try{
			model.addAttribute("league", leagueService.getLeagueById(leagueId));
			model.addAttribute("isUserIn",userInLeagueService.isUserInLeague(leagueId, USERID2));
			
			return "game/leagueStatistic";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/joinToLeague&id={leagueId}")
	public String joinToLeague(@PathVariable long leagueId){
		try{
			userInLeagueService.joinToLeague(leagueId, USERID2);
			return "redirect:myLeagues";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:myLeagues";
		}
	}
	
	@RequestMapping(value="/leaveTheLeague&id={leagueId}")
	public String leaveTheLeague(@PathVariable long leagueId){
		try{
			userInLeagueService.leaveTheLeague(leagueId, USERID2);
			return "redirect:myLeagues";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:";
		}
		
	}
	

}
