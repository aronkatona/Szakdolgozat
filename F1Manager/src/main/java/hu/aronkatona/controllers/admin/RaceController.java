package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.service.interfaces.ChampionshipService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.TrackService;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class RaceController {
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private TrackService trackService;
	
	@Autowired
	private ChampionshipService championshipService;
	
	
	@RequestMapping(value="/races")
	public String races(Model model){
		return "admin/races";
	}


	@RequestMapping(value="/newRace")
	public String newRace(Model model){
		try{
			model.addAttribute("race", new Race());
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/modifyRace.{id:[0-9]+}")
	public String modifyRace(Model model, @PathVariable long id){
		try{
			Race race = raceService.getRaceById(id);
			if(race == null) return "redirect:/admin/races";
			model.addAttribute("race", race);
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveRace", method = RequestMethod.POST)
	public String saveRace(@Valid @ModelAttribute Race race, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
		    return "admin/newRace";
		}
		
		try{
			raceService.saveRace(race);
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			model.addAttribute("existingRace","existingRace");
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		
		return "admin/menu";
	}

}
