package hu.aronkatona.controllers.error;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping(value="/error404")
	public String errorPage(Locale locale){
		 return "error404";
	}

}
