package hu.aronkatona.controllers.game;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;
import hu.aronkatona.utils.RegistrationMarshall;
import hu.aronkatona.utils.UserFormName;
import hu.aronkatona.utils.UserInSession;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
	
	@RequestMapping(value="/profile")
	public String profile(Model model, HttpSession session){
		try{
			UserInSession userInSession = (UserInSession) session.getAttribute("userInSession");
			if(userInSession == null) return "redirect:";
			model.addAttribute("user", userService.getUserById(userInSession.getId()));
			return "game/profile";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return "redirect:";
		}
	}
	
	@RequestMapping(value="/updateProfileName")
	public String updateProfileName(@Valid @ModelAttribute UserFormName userModel,BindingResult errors,Model model,final RedirectAttributes redirectAttributes){
		
		try{
			User user = userService.getUserById(userModel.getId());
			if(user != null){
				user.setName(userModel.getName());
				user.setEmail(userModel.getEmail());
				userService.saveUser(user);
				redirectAttributes.addFlashAttribute("successNewName", true);
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:home";
	}
	
	
	@RequestMapping(value="/updateProfilePassword")
	public String updateProfilePassword(@Valid @ModelAttribute User userModel, BindingResult errors,Model model,final RedirectAttributes redirectAttributes){
		
		if (errors.hasErrors()) {
			if(errors.getFieldErrors().size() == 1 && errors.getFieldErrors().get(0).getField().equals("passwordSame")){
				model.addAttribute("isPasswordSame", true);
			}
			model.addAttribute("user", userService.getUserById(userModel.getId()));
		    return "game/profile";
		}
		
		try{
			User user = userService.getUserById(userModel.getId());
			if(user != null){
				user.setPassword(userModel.getPassword());
				user.setPasswordAgain(userModel.getPassword());
				userService.saveUserWithNewPassword(user);
			}
			
			redirectAttributes.addFlashAttribute("successNewPassword", true);
			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:home";
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
	
	@RequestMapping(value="/checkExistUserName", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkExistUserName(@RequestBody RegistrationMarshall registrationMarshall){
		try{
			return userService.userExistByName(registrationMarshall.getName()) != null;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/checkExistEmail", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkExistEmail(@RequestBody RegistrationMarshall registrationMarshall){
		try{
			return userService.userExistByEmail(registrationMarshall.getEmail()) != null;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/checkExistUserNameUpdateProfile", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkExistUserNameUpdateProfile(@RequestBody RegistrationMarshall registrationMarshall){
		try{
			return userService.userExistByNameUpdateProfile(registrationMarshall.getId(),registrationMarshall.getName());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/checkExistEmailUpdateProfile", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkExistEmailUpdateProfile(@RequestBody RegistrationMarshall registrationMarshall){
		try{
			return userService.userExistByEmailUpdateProfile(registrationMarshall.getId(),registrationMarshall.getEmail());
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute User user, BindingResult errors,Model model,final RedirectAttributes redirectAttributes){
		
		if (errors.hasErrors()) {
			if(errors.getFieldErrors().size() == 1 && errors.getFieldErrors().get(0).getField().equals("passwordSame")){
				model.addAttribute("isPasswordSame", true);
			}
			model.addAttribute("user", user);
		    return "game/registration";
		}
		
		boolean fail = false;
		try{
			if(userService.userExistByEmail(user.getEmail()) != null){
				model.addAttribute("existEmail", true);
				fail = true;
			}
			
			if(userService.userExistByName(user.getName()) != null){
				model.addAttribute("existName", true);
				fail = true;
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		
		if(fail){
			model.addAttribute("user", user);
			return "game/registration";
		}
		
		try{
			userService.saveNewUser(user);
			redirectAttributes.addFlashAttribute("successRegistration", true);
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			model.addAttribute("user", user);
			return "game/registration";
		}
		
		return "redirect:home";
	}
	
	@RequestMapping(value="/loginPage")
	public String loginPage(Model model){
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
				redirectAttributes.addFlashAttribute("successLogin", true);
				session.setAttribute("userInSession", new UserInSession(user.getId(),user.getName(),user.getEmail()));
				return "redirect:home";
			}
			else{
				model.addAttribute("user", userModel);
				model.addAttribute("notSuccessLogin",true);
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
				redirectAttributes.addFlashAttribute("accountActivated", true);
				userService.saveUser(user);
				return "redirect:home";
			}
			else{
				model.addAttribute("activateFail", true);
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
	public String activationConfirm(@PathVariable String activationCode,final RedirectAttributes redirectAttributes){
		try{
			User user = userService.getUserByActivationCode(activationCode);
			if(user != null){
				userService.activateUser(user);
				redirectAttributes.addFlashAttribute("accountActivated", true);
			}
			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:home";
	}
	
	
	@RequestMapping(value="/sendNewActivationCode",method = RequestMethod.POST)
	public @ResponseBody boolean sendNewActivationCode(@RequestBody final User user){
		try{
			userService.sendMailToUserWithNewActivationCode(user.getId());	
			return true;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping(value="/newPassword.{changePasswordToken}")
	public String changePasswordToken(@PathVariable String changePasswordToken,Model model){
		try{
			User user = userService.getUserByChangePasswordToken(changePasswordToken);
			if(user != null){
				model.addAttribute("user", user);
				return "game/newPassword";
			}
			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "game/home";
		
	}
	
	@RequestMapping(value="/saveNewPassword")
	public String saveNewPassword(@Valid @ModelAttribute User userModel, BindingResult errors,Model model,final RedirectAttributes redirectAttributes){
		
		if (errors.hasErrors()) {
			if(errors.getFieldErrors().size() == 1 && errors.getFieldErrors().get(0).getField().equals("passwordSame")){
				model.addAttribute("isPasswordSame", true);
			}
			model.addAttribute("user", userModel);
		    return "game/newPassword";
		}
		
		try{
			User user = userService.getUserById(userModel.getId());
			if(user != null){
				user.setPassword(userModel.getPassword());
				user.setPasswordAgain(userModel.getPassword());
				userService.saveUserWithNewPassword(user);
			}
			
			redirectAttributes.addFlashAttribute("successNewPassword", true);
			
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:home";
	}
	
	@RequestMapping(value="/sendNewPasswordToken" , method= RequestMethod.POST)
	public @ResponseBody boolean sendNewPasswordToken(@RequestBody final User user){
		try{
			userService.sendNewPasswordToken(user.getEmail());
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
