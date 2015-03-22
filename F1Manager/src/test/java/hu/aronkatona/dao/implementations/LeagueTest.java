package hu.aronkatona.dao.implementations;

import hu.aronkatona.hibernateModel.League;
import hu.aronkatona.service.interfaces.LeagueService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
	
	@Rollback
	@Test
	public void test1(){
		for(League l : leagueService.getLeaguesByUserId(1)){
			System.out.println(l.getDescription());
		}
		
	}
}
