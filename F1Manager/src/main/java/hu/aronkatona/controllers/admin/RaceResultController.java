package hu.aronkatona.controllers.admin;

import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceResultService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.utils.RaceResultFormModel;

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
public class RaceResultController {
	
	private Logger logger = Logger.getLogger(RaceResultController.class);

	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private RaceResultService raceResultService;
	
	@RequestMapping(value="/newRaceResult")
	public String newRaceResult(Model model){
		
		try{
			model.addAttribute("raceResultFormModel", new RaceResultFormModel());
			model.addAttribute("races", raceService.getRacesWithoutResults());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newRaceResult";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		
	}
	
	@RequestMapping(value="/saveRaceResult", method = RequestMethod.POST)
	public String saveRaceResult(@ModelAttribute RaceResultFormModel raceResultFormModel, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("races", raceService.getRacesWithoutResults());
			model.addAttribute("drivers", driverService.getDrivers());
		    return "admin/newRaceResult";
		}
		
		try{
			if(raceResultService.checkSameDriver(raceResultFormModel)){
				model.addAttribute("sameDriver", true);
				model.addAttribute("races", raceService.getRacesWithoutResults());
				model.addAttribute("drivers", driverService.getDrivers());
			    return "admin/newRaceResult";
			}
			
			raceResultService.saveRaceResult(raceResultFormModel);
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			model.addAttribute("races", raceService.getRacesWithoutResults());
			model.addAttribute("drivers", driverService.getDrivers());
			model.addAttribute("unknownError", true);
			return "admin/newRaceResult";
		}
		
		return "redirect:races";
	}
	
	@RequestMapping(value="/resultRace&id={raceId}")
	public String resultRace(@PathVariable long raceId,Model model){
		try{
			model.addAttribute("drivers", raceResultService.getRaceResultByRaceId(raceId));
			return "admin/raceResult";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
}
