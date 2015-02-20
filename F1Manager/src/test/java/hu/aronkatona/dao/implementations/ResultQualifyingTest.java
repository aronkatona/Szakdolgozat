package hu.aronkatona.dao.implementations;

import hu.aronkatona.hibernateModel.Race;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.ResultQualifyingService;
import hu.aronkatona.service.interfaces.ResultRaceService;
import hu.aronkatona.service.interfaces.TeamService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class) 
@TransactionConfiguration(defaultRollback=false,transactionManager="transactionManager") 
@Transactional
public class ResultQualifyingTest {

	@Autowired
	private ResultRaceService resultRaceService;
	
	@Autowired
	private ResultQualifyingService resultQualifyingService;
	
	@Autowired
	private DriverService driverService;

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RaceService raceService;
	
	@Test
	public void firstTest(){
		Race race1 = raceService.getRaceById(1);
		System.out.println(race1.getResultQualifying().size());
		System.out.println(race1.getResultRaces().size());
		
	}
}
