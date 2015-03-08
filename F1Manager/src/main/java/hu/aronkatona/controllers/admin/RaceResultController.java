package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.ResultQualifying;
import hu.aronkatona.hibernateModel.ResultRace;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultQualifyingService;
import hu.aronkatona.service.interfaces.ResultRaceService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.utils.RaceResultFormModel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class RaceResultController {

	@Autowired
	private ResultQualifyingService resultQualifyingService;
	
	@Autowired
	private ResultRaceService resultRaceService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/newRaceResult")
	public String newRaceResult(Model model){
		try{
			model.addAttribute("raceResultFormModel", new RaceResultFormModel());
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newRaceResult";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveRaceResult", method = RequestMethod.POST)
	public String saveRaceResult(@Valid @ModelAttribute RaceResultFormModel raceResultFormModel, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
		    return "admin/newRaceResult";
		}
		
		try{
			//TODO: kiszervezes.. tranzakcioba
			ResultQualifying resultQualifying;
			Driver driver;
			for(int i = 0; i < raceResultFormModel.getQualifyingDrivers().length; ++i){
				resultQualifying = new ResultQualifying();
				resultQualifying.setRace(raceService.getRaceById(raceResultFormModel.getRace().getId()));
				driver = driverService.getDriverById(raceResultFormModel.getQualifyingDrivers()[i]);
				resultQualifying.setDriver(driver);
				resultQualifying.setTeam(driver.getTeam());
				resultQualifying.setResult(i+1);
				resultQualifyingService.saveResultQualifying(resultQualifying);
			}
			
			ResultRace resultRace;
			for(int i = 0; i < raceResultFormModel.getRaceDrivers().length; ++i){
				resultRace = new ResultRace();
				resultRace.setRace(raceService.getRaceById(raceResultFormModel.getRace().getId()));
				driver = driverService.getDriverById(raceResultFormModel.getRaceDrivers()[i]);
				resultRace.setDriver(driver);
				resultRace.setTeam(driver.getTeam());
				resultRace.setResult(i+1);
				resultRaceService.saveResultRace(resultRace);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("races", raceService.getRaces());
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/newRaceResult";
		}
		
		return "admin/menu";
	}
	
}
