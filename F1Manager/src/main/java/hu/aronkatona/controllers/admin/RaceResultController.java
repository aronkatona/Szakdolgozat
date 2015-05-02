package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.ResultPoint;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceResultService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultPointService;
import hu.aronkatona.utils.RaceResultFormModel;

import java.util.List;

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
@RequestMapping("/admin")
public class RaceResultController {
	
	private Logger logger = Logger.getLogger(RaceResultController.class);

	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private RaceResultService raceResultService;
	
	@Autowired
	private ResultPointService resultPointService;
	
	@RequestMapping(value="/newRaceResult")
	public String newRaceResult(Model model){
		
		try{
			
			List<Race> races = raceService.getRacesWithoutResults();
			List<Driver> drivers = driverService.getActiveDriversOrderByName();
			List<ResultPoint> resultPoints = resultPointService.getResultPoints();
			model.addAttribute("raceResultFormModel", new RaceResultFormModel());
			
			boolean error = false;
			if(races.isEmpty()){
				model.addAttribute("needRaces", true);
				error = true;
			}
			if(drivers.isEmpty()){
				model.addAttribute("needDrivers", true);
				error = true;
			}
			if(drivers.size() > resultPoints.size()){
				model.addAttribute("sizeNotSame", true);
				error = true;
			}
			
			if(error) {
				model.addAttribute("error", true);
				return "admin/newRaceResult";
			}
			
			model.addAttribute("races", races);
			model.addAttribute("drivers", drivers);
			return "admin/newRaceResult";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		
	}
	
	@RequestMapping(value="/saveRaceResult", method = RequestMethod.POST)
	public String saveRaceResult(@ModelAttribute RaceResultFormModel raceResultFormModel, BindingResult errors,Model model,final RedirectAttributes redirectAttributes){
		
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
			redirectAttributes.addFlashAttribute("successResult", true);
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
