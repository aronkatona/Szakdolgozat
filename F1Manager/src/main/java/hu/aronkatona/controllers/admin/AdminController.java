package hu.aronkatona.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value="")
	public String homeRedirect(){
		return "redirect:admin/home";
	}
	
	@RequestMapping(value="/")
	public String homeRedirect2(){
		return "redirect:home";
	}

	@RequestMapping(value="/home")
	public String homePage(Model model){
		return "redirect:teams";
	}
}
