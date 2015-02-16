package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;

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
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/drivers")
	public String drivers(Model model){
		return "admin/drivers";
	}


	@RequestMapping(value="/newDriver")
	public String newDriver(Model model){
		model.addAttribute("driver", new Driver());
		return "admin/newDriver";
	}
	
	@RequestMapping(value="/modifyDriver.{id:[0-9]+}")
	public String modifyDriver(Model model, @PathVariable long id){
		try{
			Driver driver = driverService.getDriverById(id);
			if(driver == null) return "redirect:/drivers";
			model.addAttribute("driver", driver);
			return "admin/newDriver";
		}
		catch(Exception e){
			e.printStackTrace();
			return "admin/newDriver";
		}
		
	}
	
	@RequestMapping(value="/testDriver")
	public String testDriver(){
		
		Driver driver = driverService.getDriverById(1);
		driver.setTeam(teamService.getTeamById(1));
		driverService.saveDriver(driver);
		
		System.out.println(driverService.getDriverById(1).getTeam());
		return "admin/menu";
	}
	
	
	@RequestMapping(value="/saveDriver", method = RequestMethod.POST)
	public String saveDriver(@Valid @ModelAttribute Driver driver, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
		    return "admin/newDriver";
		}
		
		try{
			driverService.saveDriver(driver);
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			model.addAttribute("existingDriver","existingDriver");
			return "admin/newDriver";
		}
		catch(Exception e){
			e.printStackTrace();
			return "admin/newDriver";
		}
		
		return "admin/menu";
	}

}
