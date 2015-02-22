package hu.aronkatona.dao.implementations;

import static org.junit.Assert.*;
import hu.aronkatona.hibernateModel.ChampionshipResult;
import hu.aronkatona.service.interfaces.ChampionshipResultService;
import hu.aronkatona.service.interfaces.ChampionshipService;
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
public class ChampionshipResultTest {

	@Autowired
	private ChampionshipResultService championshipResultService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChampionshipService championshipService;
	
	@Test
	public void test1(){
		ChampionshipResult cr = new ChampionshipResult();
		cr.setChampionship(championshipService.getChampionshipById(1));
		cr.setUser(userService.getUserById(2));
		cr.setPoint(333);
		cr.setPosition(5);
		championshipResultService.saveChampionshipResult(cr);
		
		assertEquals("bcd@bdcd.com",championshipResultService.getChampionshipResultById(cr.getId()).getUser().getEmail());
		assertEquals(2,cr.getId());
		
	}
}
