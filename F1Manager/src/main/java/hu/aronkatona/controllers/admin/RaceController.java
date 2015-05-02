package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Championship;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.Track;
import hu.aronkatona.service.interfaces.ChampionshipService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.TrackService;

import java.util.List;

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

@Controller
@RequestMapping("/admin")
public class RaceController {
	
	private Logger logger = Logger.getLogger(RaceController.class);
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private TrackService trackService;
	
	@Autowired
	private ChampionshipService championshipService;	
	
	@RequestMapping(value="/races")
	public String races(Model model){
		try{
			model.addAttribute("races", raceService.getRaces());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		return "admin/races";
	}


	@RequestMapping(value="/newRace")
	public String newRace(Model model){
		try{
			List<Championship> championships = championshipService.getChampionships();
			List<Track> tracks = trackService.getTracks();
			model.addAttribute("race", new Race());
			
			boolean error = false;
			if(championships.isEmpty()){
				model.addAttribute("needChampionship", true);
				error = true;
			}
			if(tracks.isEmpty()){
				model.addAttribute("needTrack", true);
				error = true;
			}
			
			if(error) {
				model.addAttribute("error", true);
				return "admin/newRace";
			}
			
			model.addAttribute("championships", championships);
			model.addAttribute("tracks", tracks);
			return "admin/newRace";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/modifyRace&id={id:[0-9]+}")
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
			logger.error("", e);
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
		
		if(raceService.existingRaceByIdAndDate(race.getId(), race.getDate())){
			model.addAttribute("race", race);
			model.addAttribute("existingRace",true);
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		
		
		try{
			raceService.saveRace(race);
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			model.addAttribute("championships", championshipService.getChampionships());
			model.addAttribute("tracks", trackService.getTracks());
			return "admin/newRace";
		}
		
		return "redirect:races";
	}
	


}
