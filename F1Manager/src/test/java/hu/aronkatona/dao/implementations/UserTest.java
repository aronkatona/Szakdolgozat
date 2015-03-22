package hu.aronkatona.dao.implementations;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.DriverService;
import hu.aronkatona.service.interfaces.TeamService;
import hu.aronkatona.service.interfaces.UserService;

import org.hibernate.exception.ConstraintViolationException;
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
public class UserTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private TeamService teamService;
	
	@Rollback
	@Test
	public void testNormal(){
		User user1 = new User();
		user1.setName("wqvdwqdwq");
		user1.setEmail("asvq");
		user1.setPassword("wqvweqvwqe");
		userService.saveUser(user1);
	}
	
	@Rollback
	@Test(expected=ConstraintViolationException.class)
	public void testNotNull(){
		User user1 = new User();
		user1.setEmail("asvq");
		user1.setPassword("wqvweqvwqe");
		userService.saveUser(user1);
	}
	
	@Rollback
	@Test(expected=ConstraintViolationException.class)
	public void testNotUnique() {
		User u = new User();
		u.setName("wqveqvweqweqweew");
		userService.saveUser(u);
		
		User user = new User();
		user.setName(userService.getUserById(u.getId()).getName());
	    userService.saveUser(user);
	    userService.deleteUser(user.getId());
	    
	}
}
