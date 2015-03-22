package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.ResultPoint;
import hu.aronkatona.service.interfaces.ResultPointService;
import hu.aronkatona.utils.ResultPointFormModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ResultPointController {
	
	private Logger logger = Logger.getLogger(ChampionshipController.class);

	@Autowired
	private ResultPointService resultPointService;
	
	@RequestMapping(value="/resultPoints")
	public String resultPoints(Model model){
		try{
			model.addAttribute("points", resultPointService.getResultPoints());
			return "admin/resultPoints";
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:";
	}
	
	@RequestMapping(value="/saveResultPoints")
	public String saveResultPoints(@ModelAttribute ResultPointFormModel resultPointFormModel){
		for(int i = 0; i < resultPointFormModel.getDriverQualificationPoints().length; ++i){
			ResultPoint resultPoint = resultPointService.getResultPointById(resultPointFormModel.getIds()[i]);
			resultPoint.setDriverQualificationPoint(resultPointFormModel.getDriverQualificationPoints()[i]);
			resultPoint.setDriverRacePoint(resultPointFormModel.getDriverRacePoints()[i]);
			resultPoint.setTeamQualificationPoint(resultPointFormModel.getTeamQualificationPoints()[i]);
			resultPoint.setTeamRacePoint(resultPointFormModel.getTeamRacePoints()[i]);
			resultPoint.setRate(resultPointFormModel.getRates()[i]);
			resultPointService.saveResultPoint(resultPoint);
		}
		return "redirect:";
	}
}
