package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;
import hu.aronkatona.utils.UserInSession;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home2(Model model) {
		return "game/welcome";
	}

	@RequestMapping(value="/registration")
	public String registartion(Model model){
		try{
			model.addAttribute("user", new User());
			return "game/registration";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "/";
		}
		
	}
	
	@RequestMapping(value="/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute User user, BindingResult errors,Model model){
		
		if (errors.hasErrors()) {
			if(errors.getFieldErrors().size() == 1 && errors.getFieldErrors().get(0).getField().equals("passwordSame")){
				model.addAttribute("isPasswordSame", errors.getFieldErrors().get(0).getDefaultMessage());
			}
			model.addAttribute("user", user);
		    return "game/registration";
		}
		
		boolean fail = false;
		try{
			if(userService.userExistByEmail(user.getEmail()) != null){
				model.addAttribute("existEmail", "Az e-mail cÃ­m mÃ¡r foglalt!");
				fail = true;
			}
			
			if(userService.userExistByName(user.getName()) != null){
				model.addAttribute("existName", "A nÃ©v mÃ¡r foglalt!");
				fail = true;
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		
		if(fail) return "game/registration";
		
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
			logger.error("", e);
			e.printStackTrace();
			model.addAttribute("user", user);
			return "game/registration";
		}
		
		return "redirect:";
	}
	
	@RequestMapping(value="/loginPage")
	public String loginPage(){
		return "game/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String userLogin(@ModelAttribute User userModel, Model model,final RedirectAttributes redirectAttributes,HttpSession session){
		try{
			User user = userService.userLogin(userModel.getName(), userModel.getPassword());
			if(user != null && !user.isActivated()){
				model.addAttribute("userId", user.getId());
				return "game/activateAccount";
			}
			else if(user != null){
				redirectAttributes.addFlashAttribute("successLogin", "Sikeres bejelentkezés");
				session.setAttribute("userInSession", new UserInSession(user.getId(),user.getName(),user.getEmail()));
				return "redirect:home";
			}
			else{
				model.addAttribute("notSuccessLogin","Sikertelen bejelentkezés");
				return "game/login";
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "game/login";
	}
	
	@RequestMapping(value="/activateAccount",method = RequestMethod.POST)
	public String activateAccount(@ModelAttribute User userModel, Model model ,final RedirectAttributes redirectAttributes){
		try{
			User user = userService.getUserById(userModel.getId());
			if(user.getActivationCode().equals(userModel.getActivationCode())){
				user.setActivated(true);
				redirectAttributes.addFlashAttribute("accountActivated", "Az accountod sikeresen aktiváltad");
				userService.saveUser(user);
				return "redirect:home";
			}
			else{
				model.addAttribute("activateFail", "Rossz az aktiváló kód");
				model.addAttribute("userId", user.getId());
				return "game/activateAccount";
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "game/activateAccount";
		}
		
	}
	
	@RequestMapping(value="/activationConfirm.{activationCode}")
	public String activationConfirm(@PathVariable String activationCode,Model model){
		try{
			User user = userService.getUserByActivationCode(activationCode);
			if(user != null){
				userService.activateUser(user);
				model.addAttribute("successActivate", "sikeres aktivacio");
			}
			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "game/welcome";
	}
	
	
	@RequestMapping(value="/sendNewActivationCode",method = RequestMethod.POST)
	public @ResponseBody boolean sendNewActivationCode(@RequestBody final User user){
		try{
			
			new Thread(){
				@Override
				public void run(){
					userService.sendMailToUser(user.getId() );					
					}
			}.start();
			
			return true;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.setAttribute("userInSession",null);
		return "redirect:home";
	}
	
	
}
