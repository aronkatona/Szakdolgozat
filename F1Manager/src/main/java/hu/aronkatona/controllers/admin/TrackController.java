package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.Track;
import hu.aronkatona.service.interfaces.TrackService;

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
@RequestMapping(value="admin")
public class TrackController {

	@Autowired
	private TrackService trackService;
	
	@RequestMapping(value="/tracks")
	public String tracks(Model model){
		return "admin/tracks";
	}


	@RequestMapping(value="/newTrack")
	public String newTrack(Model model){
		model.addAttribute("track", new Track());
		return "admin/newTrack";
	}
	
	@RequestMapping(value="/modifyTrack.{id:[0-9]+}")
	public String modifyTrack(Model model, @PathVariable long id){
		try{
			Track track = trackService.getTrackById(id);
			if(track == null) return "redirect:/admin/tracks";
			model.addAttribute("track", track);
			return "admin/newTrack";
		}
		catch(Exception e){
			e.printStackTrace();
			return "redirect:/admin/home";
		}
		
	}
	
	@RequestMapping(value="/saveTrack", method = RequestMethod.POST)
	public String saveTrack(@Valid @ModelAttribute Track track, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
		    return "admin/newTrack";
		}
		
		try{
			trackService.saveTrack(track);
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			model.addAttribute("existingTrack","existingTrack");
			return "admin/newTrack";
		}
		catch(Exception e){
			e.printStackTrace();
			return "admin/newTrack";
		}
		
		return "admin/menu";
	}
}
