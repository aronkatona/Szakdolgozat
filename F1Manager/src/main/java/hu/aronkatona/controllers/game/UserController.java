package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;

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
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/registration")
	public String registartion(Model model){
		try{
			model.addAttribute("user", new User());
			return "game/registration";
		}
		catch(Exception e){
			e.printStackTrace();
			return "/";
		}
		
	}
	
	@RequestMapping(value="/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute User user, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			model.addAttribute("user", user);
		    return "game/registration";
		}
		
		try{
			userService.saveNewUser(user);
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			model.addAttribute("existingUser","existingUser");
			model.addAttribute("user", user);
			return "game/registration";
		}
		catch(Exception e){
			e.printStackTrace();
			model.addAttribute("user", user);
			return "game/registration";
		}
		
		return "redirect:";
	}
	
	@RequestMapping(value="/activationConfirm.{activationCode}")
	public String activationConfirm(@PathVariable String activationCode){
		User user = userService.getUserByActivationCode(activationCode);
		if(user != null) userService.activateUser(user);
		return "game/welcome";
	}
	
	
}
