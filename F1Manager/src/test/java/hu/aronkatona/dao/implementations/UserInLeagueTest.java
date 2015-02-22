package hu.aronkatona.dao.implementations;

import static org.junit.Assert.assertEquals;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserInLeagueService;
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
public class UserInLeagueTest {

	@Autowired
	private UserInLeagueService userInLeagueService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserService userService;

	@Test
	public void test1(){
		User user = userService.getUserById(2);
		assertEquals(1,user.getUserInLeague().size());
	}

}
