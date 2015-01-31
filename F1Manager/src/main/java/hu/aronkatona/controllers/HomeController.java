package hu.aronkatona.controllers;

import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.interfaces.TeamService;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	@Autowired
	private TeamService teamService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		Team t1 = new Team();
		t1.setName("a");
		t1.setPicture("url");
		teamService.saveTeam(t1);
		
		System.out.println(teamService.getTeamById(1).getName());
		
		System.out.println(teamService.getTeams().size());
		
		teamService.deleteTeam(1);
		
		System.out.println(teamService.getTeams().size());
		
		return "admin/menu";
	}
	
}
