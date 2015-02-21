package hu.aronkatona.dao.implementations;

import static org.junit.Assert.assertEquals;
import hu.aronkatona.hibernateModel.UserResultHistory;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.RaceService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserResultHistoryService;
import hu.aronkatona.service.interfaces.UserService;

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
public class UserResultHistoryTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private UserResultHistoryService userResultHistoryService;
	
	@Test
	public void Test1(){
	}
	
}
