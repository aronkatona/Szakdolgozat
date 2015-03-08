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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class FormAdvices {
	
	@Autowired
	private TeamService teamService;
	
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

}
