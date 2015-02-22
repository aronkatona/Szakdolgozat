package hu.aronkatona.dao.implementations;

import static org.junit.Assert.*;
import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.service.interfaces.LeagueService;

import java.util.Date;

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
public class LeagueTest {

	@Autowired
	private LeagueService leagueService;
	
	@Test
	public void test1(){
		League l = new League();
		l.setAvgPoints((short) 5);
		l.setDate(new Date());
		l.setDescription("ezleszaz");
		l.setName("mostkomolyan");
		leagueService.saveLeague(l);
		
		assertEquals("mostkomolyan",leagueService.getLeagueById(1).getName());
	}
}
