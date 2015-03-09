package hu.aronkatona.service.implementations;

import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.hibernateModel.ResultQualifying;
import hu.aronkatona.hibernateModel.ResultRace;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceResultService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultQualifyingService;
import hu.aronkatona.service.interfaces.ResultRaceService;
import hu.aronkatona.utils.RaceResultFormModel;

import java.util.ArrayList;
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


	@Override
	public void saveRaceResult(RaceResultFormModel raceResultFormModel) {
		ResultQualifying resultQualifying;
		Driver driver;
		Race race = raceService.getRaceById(raceResultFormModel.getRace().getId());
		for(int i = 0; i < raceResultFormModel.getQualifyingDrivers().length; ++i){
			resultQualifying = new ResultQualifying();
			resultQualifying.setRace(race);
			driver = driverService.getDriverById(raceResultFormModel.getQualifyingDrivers()[i]);
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
			resultRace.setDriver(driver);
			resultRace.setTeam(driver.getTeam());
			resultRace.setResult(i+1);
			resultRaceService.saveResultRace(resultRace);
		}

		race.setResultSet(true);
		raceService.saveRace(race);
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
}
