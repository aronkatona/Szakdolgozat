package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Championship;
import hu.aronkatona.service.interfaces.ChampionshipService;

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
@RequestMapping(value="admin")
public class ChampionshipController {
	
	private Logger logger = Logger.getLogger(ChampionshipController.class);

	@Autowired
	private ChampionshipService championshipService;
	
	@RequestMapping(value="/championships")
	public String championships(Model model){
		try{
			model.addAttribute("championships", championshipService.getChampionships());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
		return "admin/championships";
	}


	@RequestMapping(value="/newChampionship")
	public String newChampionship(Model model){
		model.addAttribute("championship", new Championship());
		return "admin/newChampionship";
	}
	
	@RequestMapping(value="/modifyChampionship&id={id:[0-9]+}")
	public String modifyChampionship(Model model, @PathVariable long id){
		try{
			Championship championship = championshipService.getChampionshipById(id);
			if(championship == null) return "redirect:/admin/championships";
			model.addAttribute("championship", championship);
			return "admin/newChampionship";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		    return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveChampionship", method = RequestMethod.POST)
	public String saveChampionship(@Valid @ModelAttribute Championship championship, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
		    return "admin/newChampionship";
		}
		
		
		
		if(championshipService.existChampionshipThisYear(championship)){
			model.addAttribute("existChampionship", true);
			return "admin/newChampionship";
		}
		
		try{
			championshipService.saveChampionship(championship);
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "admin/newChampionship";
		}
		
		return "redirect:championships";
	}

}


