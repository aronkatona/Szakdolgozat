package hu.aronkatona.dao.implementations;

import static org.junit.Assert.*;

import java.util.UUID;

import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;

import javax.validation.ConstraintViolationException;

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
	
	@Rollback
	@Test(expected=ConstraintViolationException.class)
	public void testWrongEmail(){
		User user1 = new User();
		user1.setName("wqvdwqdwq");
		user1.setEmail("asvq");
		user1.setPassword("wqvweqvwqe");
		user1.setPasswordAgain("wqvweqvwqe");
		userService.saveUser(user1);
	}
	
	@Rollback
	@Test
	public void testAjaxEmailCheckNull(){
		UUID randomEmail = UUID.randomUUID();
		assertEquals(null,userService.userExistByEmail(randomEmail.toString()));
	}
	
	@Rollback
	@Test
	public void testAjaxNameCheckNull(){
		UUID randomName = UUID.randomUUID();
		assertEquals(null,userService.userExistByName(randomName.toString()));
	}

	
}
