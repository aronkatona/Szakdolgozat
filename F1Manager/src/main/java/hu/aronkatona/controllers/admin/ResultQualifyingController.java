package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.ResultQualifying;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultQualifyingService;

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
public class ResultQualifyingController {

	@Autowired
	private ResultQualifyingService resultQualifyingService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value="resultQualifyings")
	public String resultQualifyings(Model model){
		return "admin/resultQualifyings";
	}


	@RequestMapping(value="/newResultQualifying")
	public String newResultQualifying(Model model){
		try{
			model.addAttribute("resultQualifying", new ResultQualifying());
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultQualifying";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:admin/home";
		}
		
	}
	
	@RequestMapping(value="/modifyResultQualifying.{id:[0-9]+}")
	public String modifyResultQualifying(Model model, @PathVariable long id){
		try{
			ResultQualifying resultQualifying = resultQualifyingService.getResultQualifyingById(id);
			if(resultQualifying == null) return "redirect:/admin/resultQualifyings";
			model.addAttribute("resultQualifying", resultQualifying);
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultQualifying";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveResultQualifying", method = RequestMethod.POST)
	public String saveResultQualifying(@Valid @ModelAttribute ResultQualifying resultQualifying, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
		    return "admin/newResultQualifying";
		}
		
		try{
			resultQualifying.setTeam(driverService.getDriverById(resultQualifying.getDriver().getId()).getTeam());
			resultQualifyingService.saveResultQualifying(resultQualifying);
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newResultQualifying";
		}
		
		return "admin/menu";
	}
}
