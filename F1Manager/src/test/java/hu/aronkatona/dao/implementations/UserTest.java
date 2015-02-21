package hu.aronkatona.dao.implementations;

import static org.junit.Assert.assertEquals;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
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
public class UserTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Test
	public void test1(){
		User user = userService.getUserById(2);
		assertEquals("vettel", user.getActualDriver1().getName());
		assertEquals("button", user.getActualDriver2().getName());
		assertEquals("ferrari", user.getActualTeam1().getName());
		assertEquals("mclaren", user.getActualTeam2().getName());
		assertEquals("mercedes", user.getActualTeam3().getName());
	}
}
