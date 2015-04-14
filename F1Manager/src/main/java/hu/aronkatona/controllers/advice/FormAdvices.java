package hu.aronkatona.controllers.advice;

import hu.aronkatona.formPropertyEditor.ChampionshipEditor;
import hu.aronkatona.formPropertyEditor.DriverEditor;
import hu.aronkatona.formPropertyEditor.RaceEditor;
import hu.aronkatona.formPropertyEditor.TeamEditor;
import hu.aronkatona.formPropertyEditor.TrackEditor;
import hu.aronkatona.hibernateModel.Championship;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.hibernateModel.Track;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.utils.UserInSession;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FormAdvices {
	
	@Autowired
	private TeamService teamService;
	
	@ModelAttribute
	private void checkUserLoggedIn(Model model,HttpSession session){
		UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
		if(userInSession != null){
			model.addAttribute("loggedin", true);
			model.addAttribute("userName", userInSession.getName());
		}
		else{
			model.addAttribute("loggedin", false);
		}
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.registerCustomEditor(Team.class, new TeamEditor());
	    binder.registerCustomEditor(Championship.class, new ChampionshipEditor());
	    binder.registerCustomEditor(Track.class, new TrackEditor());
	    binder.registerCustomEditor(Driver.class, new DriverEditor());
	    binder.registerCustomEditor(Race.class, new RaceEditor());
	}
	
	@ExceptionHandler(Exception.class)
    public String agentNotFound() {
        return "error404";
    }


}
