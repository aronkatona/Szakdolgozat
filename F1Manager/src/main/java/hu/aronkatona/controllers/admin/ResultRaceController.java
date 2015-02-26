package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.ResultRace;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultRaceService;

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
@RequestMapping("/admin")
public class ResultRaceController {

	@Autowired
	private ResultRaceService resultRaceService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value="resultRaces")
	public String ResultRaces(Model model){
		return "admin/resultRaces";
	}


	@RequestMapping(value="/newResultRace")
	public String newResultRace(Model model){
		try{
			model.addAttribute("resultRace", new ResultRace());
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultRace";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:admin/home";
		}
		
	}
	
	@RequestMapping(value="/modifyResultRace.{id:[0-9]+}")
	public String modifyResultRace(Model model, @PathVariable long id){
		try{
			ResultRace resultRace = resultRaceService.getResultRaceById(id);
			if(resultRace == null) return "redirect:/admin/resultRaces";
			model.addAttribute("resultRace", resultRace);
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultRace";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveResultRace", method = RequestMethod.POST)
	public String saveResultRace(@Valid @ModelAttribute ResultRace resultRace, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
		    return "admin/newResultRace";
		}
		
		try{
			resultRace.setTeam(driverService.getDriverById(resultRace.getDriver().getId()).getTeam());
			resultRaceService.saveResultRace(resultRace);
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultRace";
		}
		
		return "admin/menu";
	}
}
