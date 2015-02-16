package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.service.interfaces.RaceService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class RaceController {
	
	@Autowired
	private RaceService raceService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value="/races")
	public String races(Model model){
		return "admin/races";
	}


	@RequestMapping(value="/newRace")
	public String newRace(Model model){
		model.addAttribute("race", new Race());
		return "admin/newRace";
	}
	
	@RequestMapping(value="/modifyRace.{id:[0-9]+}")
	public String modifyRace(Model model, @PathVariable long id){
		try{
			Race race = raceService.getRaceById(id);
			if(race == null) return "redirect:/races";
			model.addAttribute("race", race);
			return "admin/newRace";
		}
		catch(Exception e){
			e.printStackTrace();
			return "admin/newRace";
		}
		
	}
	
	@RequestMapping(value="/saveRace", method = RequestMethod.POST)
	public String saveRace(@Valid @ModelAttribute Race race, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
		    return "admin/newRace";
		}
		
		try{
			raceService.saveRace(race);
		}
		catch(Exception e){
			e.printStackTrace();
			return "admin/newRace";
		}
		
		return "admin/menu";
	}

}
