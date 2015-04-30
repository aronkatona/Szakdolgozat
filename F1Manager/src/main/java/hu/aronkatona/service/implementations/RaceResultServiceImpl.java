package hu.aronkatona.service.implementations;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.ResultPoint;
import hu.aronkatona.hibernateModel.ResultQualifying;
import hu.aronkatona.hibernateModel.ResultRace;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.hibernateModel.UserResultHistory;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceResultService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultPointService;
import hu.aronkatona.service.interfaces.ResultQualifyingService;
import hu.aronkatona.service.interfaces.ResultRaceService;
import hu.aronkatona.service.interfaces.UserResultHistoryService;
import hu.aronkatona.service.interfaces.UserService;
import hu.aronkatona.utils.RaceResultFormModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RaceResultServiceImpl implements RaceResultService{

	@Autowired
	private ResultQualifyingService resultQualifyingService;
	
	@Autowired
	private ResultRaceService resultRaceService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResultPointService resultPointService;
	
	@Autowired
	private UserResultHistoryService userResultHistoryService;
	
	private final int MONEYPERPOINT = 2000;


	@Override
	public void saveRaceResult(RaceResultFormModel raceResultFormModel) {
		
		List<ResultPoint> resultPoints = resultPointService.getResultPoints();
		
		List<Driver> qualificationResultDrivers = new ArrayList<>();
		List<Driver> raceResultDrivers = new ArrayList<>();
		
		ResultQualifying resultQualifying;
		Driver driver;
		Race race = raceService.getRaceById(raceResultFormModel.getRace().getId());
		for(int i = 0; i < raceResultFormModel.getQualifyingDrivers().length; ++i){
			resultQualifying = new ResultQualifying();
			resultQualifying.setRace(race);
			driver = driverService.getDriverById(raceResultFormModel.getQualifyingDrivers()[i]);
			driver.increasePoint(resultPoints.get(i).getDriverQualificationPoint());
			driver.setPrice(calculateNewPrice(driver.getPrice(),resultPoints.get(i).getRate()));
			driver.getTeam().increasePoint(resultPoints.get(i).getTeamQualificationPoint());
			driver.getTeam().setPrice(calculateNewPrice(driver.getTeam().getPrice(),resultPoints.get(i).getRate()));
			qualificationResultDrivers.add(driver);
			resultQualifying.setDriver(driver);
			resultQualifying.setTeam(driver.getTeam());
			resultQualifying.setResult(i+1);
			resultQualifyingService.saveResultQualifying(resultQualifying);
		}
		
		ResultRace resultRace;
		for(int i = 0; i < raceResultFormModel.getRaceDrivers().length; ++i){
			resultRace = new ResultRace();
			resultRace.setRace(race);
			driver = driverService.getDriverById(raceResultFormModel.getRaceDrivers()[i]);
			driver.increasePoint(resultPoints.get(i).getDriverRacePoint());
			driver.setPrice(calculateNewPrice(driver.getPrice(),resultPoints.get(i).getRate()));
			driver.getTeam().increasePoint(resultPoints.get(i).getTeamRacePoint());
			driver.getTeam().setPrice(calculateNewPrice(driver.getTeam().getPrice(),resultPoints.get(i).getRate()));
			raceResultDrivers.add(driver);
			resultRace.setDriver(driver);
			resultRace.setTeam(driver.getTeam());
			resultRace.setResult(i+1);
			resultRaceService.saveResultRace(resultRace);
		}
		
		
		List<User> users = userService.getUsers();
		
		UserResultHistory userResultHistory;
		int index;
		for(User user : users){
			
			long startMoney = user.getActualMoney();
			long startPoint = user.getActualPoint();
			
			index = 0;
			for(Driver d : qualificationResultDrivers){
				if(Driver.equals(user.getActualDriver1(),d) || Driver.equals(user.getActualDriver2(),d)){
					user.increaseActualPoint(resultPoints.get(index).getDriverQualificationPoint());
					user.increaseActualMoney(resultPoints.get(index).getDriverQualificationPoint() * MONEYPERPOINT);
				}
				if(Team.equals(user.getActualTeam1(),d.getTeam()) || Team.equals(user.getActualTeam2(),d.getTeam()) || Team.equals(user.getActualTeam3(),d.getTeam())){
					user.increaseActualPoint(resultPoints.get(index).getTeamQualificationPoint());
					user.increaseActualMoney(resultPoints.get(index).getTeamQualificationPoint() * MONEYPERPOINT);
				}
				index++;
			}
			index = 0;
			for(Driver d : raceResultDrivers){
				if(Driver.equals(user.getActualDriver1(),d)|| Driver.equals(user.getActualDriver2(),d)){
					user.increaseActualPoint(resultPoints.get(index).getDriverRacePoint());
					user.increaseActualMoney(resultPoints.get(index).getDriverRacePoint() * MONEYPERPOINT);
				}
				if(Team.equals(user.getActualTeam1(),d.getTeam()) || Team.equals(user.getActualTeam2(),d.getTeam()) || Team.equals(user.getActualTeam3(),d.getTeam())){
					user.increaseActualPoint(resultPoints.get(index).getTeamRacePoint());
					user.increaseActualMoney(resultPoints.get(index).getTeamRacePoint() * MONEYPERPOINT);
				}
				index++;
			}
			
			userResultHistory = new UserResultHistory();
			userResultHistory.setUser(user);
			userResultHistory.setDriver1(user.getActualDriver1());
			userResultHistory.setDriver2(user.getActualDriver2());
			userResultHistory.setTeam1(user.getActualTeam1());
			userResultHistory.setTeam2(user.getActualTeam2());
			userResultHistory.setTeam3(user.getActualTeam3());
			userResultHistory.setRace(race);
			userResultHistory.setMoney(user.getActualMoney() - startMoney);
			userResultHistory.setPoint(user.getActualPoint() - startPoint);
			userResultHistory.setPosition(user.getActualPosition());
			userResultHistoryService.saveUserResultHistory(userResultHistory);
		}
		
		
		Collections.sort(users);
		int actualPosition = 1;
		for(User user: users){
			user.setActualPosition(actualPosition++);
			userService.saveUser(user);
		}
		
		for(Driver d : qualificationResultDrivers){
			driverService.saveDriver(d);
		}
		for(Driver d : raceResultDrivers){
			driverService.saveDriver(d);
		}

		race.setResultSet(true);
		raceService.saveRace(race);
	}



	@Override
	public List<String[]> getRaceResultByRaceId(long raceId) {
		List<String[]> results = new ArrayList<>();
		List<ResultQualifying> resultQualifyings = resultQualifyingService.getResultQualifyingsByRaceId(raceId) ;
		List<ResultRace> resultRaces = resultRaceService.getResultRacesByRaceId(raceId) ;
		for(int i = 0; i < resultRaces.size(); ++i){
			String[] driverNames = new String[2];
			driverNames[0] = resultQualifyings.get(i).getDriver().getName();
			driverNames[1] = resultRaces.get(i).getDriver().getName();
			results.add(driverNames);
		}
		return results;
	}
	
	
	@Override
	public boolean checkSameDriver(RaceResultFormModel raceResultFormModel) {
		int[] qualifyings = raceResultFormModel.getQualifyingDrivers();
		for(int i = 0; i < qualifyings.length - 1 ; ++i){
			for(int j = i + 1; j < qualifyings.length; ++j ){
				if(qualifyings[i] == qualifyings[j]) return true;
			}
		}
		
		int[] races = raceResultFormModel.getRaceDrivers();
		for(int i = 0; i < races.length - 1 ; ++i){
			for(int j = i + 1; j < races.length; ++j ){
				if(races[i] == races[j]) return true;
			}
		}
		
		return false;
	}
	
	private long calculateNewPrice(long actualPrice,int rate){
		long newPrice = (long) ((long) actualPrice + ((double)rate / 100 * actualPrice)); 
		return newPrice;
	}
	
	
	
}
