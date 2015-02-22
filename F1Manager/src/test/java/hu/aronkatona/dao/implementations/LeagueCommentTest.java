package hu.aronkatona.dao.implementations;

import hu.aronkatona.hibernateModel.LeagueComment;
import hu.aronkatona.service.interfaces.LeagueCommentService;
import hu.aronkatona.service.interfaces.LeagueService;
import hu.aronkatona.service.interfaces.UserService;

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
public class LeagueCommentTest {

	@Autowired
	private LeagueCommentService leagueCommentService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test1(){
		LeagueComment lc = new LeagueComment();
		lc.setComment("firstComment");
		lc.setDate(new Date());
		lc.setUser(userService.getUserById(2));
		lc.setLeague(leagueService.getLeagueById(1));
		leagueCommentService.saveLeagueComment(lc);
	}
}
