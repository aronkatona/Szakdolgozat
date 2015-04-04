package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
import hu.aronkatona.service.interfaces.UserService;
import hu.aronkatona.utils.UserInSession;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LeagueController {
	
	private Logger logger = Logger.getLogger(LeagueController.class);

	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserInLeagueService userInLeagueService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/allLeagues")
	public String allLeagues(Model model){
		try{
			model.addAttribute("leagues", leagueService.getLeagues());
		}
		catch(Exception e){
			logger.error("", e);
			return "redirect:";
		}
		return "game/leagues";
	}
	
	
	@RequestMapping(value="/myLeagues")
	public String myLeagues(Model model,HttpSession session,final RedirectAttributes redirectAttributes){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			if(userInSession != null){
				model.addAttribute("leagues", leagueService.getLeaguesByUserId(userInSession.getId()));				
			}
			else{
				redirectAttributes.addFlashAttribute("firstLogin", true);
				return "redirect:home";
			}
		}
		catch(Exception e){
			logger.error("", e);
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
	public String saveLeague(Model model,@Valid @ModelAttribute League league, BindingResult errors,HttpSession session){
		
		if(errors.hasErrors()){
			return "game/createLeague";
		}
		
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			leagueService.saveLeague(league,userInSession.getId());
			return "redirect:";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "game/createLeague";
		}
		
	}
	
	@RequestMapping(value="/league&id={leagueId}")
	public String leagueStatistic(@PathVariable long leagueId, Model model,HttpSession session){
		try{
			model.addAttribute("league", leagueService.getLeagueById(leagueId));
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			model.addAttribute("isUserIn",userInLeagueService.isUserInLeague(leagueId, userInSession.getId()));
			
			return "game/leagueStatistic";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/joinToLeague&id={leagueId}")
	public String joinToLeague(@PathVariable long leagueId,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userInLeagueService.joinToLeague(leagueId,  userInSession.getId());
			return "redirect:myLeagues";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:myLeagues";
		}
	}
	
	@RequestMapping(value="/leaveTheLeague&id={leagueId}")
	public String leaveTheLeague(@PathVariable long leagueId,HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			userInLeagueService.leaveTheLeague(leagueId, userInSession.getId());
			return "redirect:myLeagues";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		
	}
	
	

}
