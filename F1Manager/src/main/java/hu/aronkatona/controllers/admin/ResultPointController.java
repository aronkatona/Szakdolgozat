package hu.aronkatona.controllers.admin;

import hu.aronkatona.hibernateModel.ResultPoint;
import hu.aronkatona.service.interfaces.ResultPointService;
import hu.aronkatona.utils.ResultPointFormModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		try{
			for(int i = 0; i < resultPointFormModel.getDriverQualificationPoints().length; ++i){
				if(resultPointFormModel.getIds()[i] != 0){
					ResultPoint resultPoint = resultPointService.getResultPointById(resultPointFormModel.getIds()[i]);
					resultPoint.setResult(resultPointFormModel.getResults()[i]);
					resultPoint.setDriverQualificationPoint(resultPointFormModel.getDriverQualificationPoints()[i]);
					resultPoint.setDriverRacePoint(resultPointFormModel.getDriverRacePoints()[i]);
					resultPoint.setTeamQualificationPoint(resultPointFormModel.getTeamQualificationPoints()[i]);
					resultPoint.setTeamRacePoint(resultPointFormModel.getTeamRacePoints()[i]);
					resultPoint.setRate(resultPointFormModel.getRates()[i]);
					resultPointService.saveResultPoint(resultPoint);
				}
				else{
					ResultPoint newResultPoint = new ResultPoint(
								resultPointFormModel.getResults()[i],
								resultPointFormModel.getDriverRacePoints()[i],
								resultPointFormModel.getDriverQualificationPoints()[i],
								resultPointFormModel.getTeamRacePoints()[i],
								resultPointFormModel.getTeamQualificationPoints()[i],
								resultPointFormModel.getRates()[i]
							);
					resultPointService.saveResultPoint(newResultPoint);
				}
			}
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
		}
		return "redirect:resultPoints";
	}
	
	@RequestMapping(value="/deleteResultPoint&id={id}" ,method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteResultPoint(@PathVariable long id){
		try{
			resultPointService.deleteResultPoint(id);
			return true;
		}
		catch(Exception e){
			logger.error("", e);
			e.printStackTrace();
			return false;
		}
	}
}
