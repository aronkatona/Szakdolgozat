package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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
	
	private Logger logger = Logger.getLogger(DriverController.class);

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/drivers")
	public String drivers(Model model){
		try{
			model.addAttribute("drivers", driverService.getDrivers());
			return "admin/drivers";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		
	}


	@RequestMapping(value="/newDriver")
	public String newDriver(Model model){
		try{
			model.addAttribute("driver", new Driver());
			model.addAttribute("teams", teamService.getTeams());
			return "admin/newDriver";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "admin/home";
		}
		
	}
	
	@RequestMapping(value="/modifyDriver&id={id:[0-9]+}")
	public String modifyDriver(Model model, @PathVariable long id){
		try{
			Driver driver = driverService.getDriverById(id);
			if(driver == null) return "redirect:/admin/drivers";
			model.addAttribute("driver", driver);
			model.addAttribute("teams", teamService.getTeams());
			return "admin/newDriver";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "admin/home";
		}
		
	}
	
	
	@RequestMapping(value="/saveDriver", method = RequestMethod.POST)
	public String saveDriver(@Valid @ModelAttribute Driver driver, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("teams", teamService.getTeams());
		    return "admin/newDriver";
		}
		
		try{
			driverService.saveDriver(driver);
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			model.addAttribute("existingDriver","existingDriver");
			model.addAttribute("teams", teamService.getTeams());
			return "admin/newDriver";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			model.addAttribute("teams", teamService.getTeams());
			return "admin/newDriver";
		}
		
		return "redirect:drivers";
	}

}
