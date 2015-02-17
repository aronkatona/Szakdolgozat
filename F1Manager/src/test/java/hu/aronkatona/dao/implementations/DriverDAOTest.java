package hu.aronkatona.dao.implementations;

import static org.junit.Assert.assertEquals;
import hu.aronkatona.dao.interfaces.DriverDAO;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.service.interfaces.DriverService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class) 
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
@Transactional
public class DriverDAOTest {

	@Autowired
	private DriverDAO driverDAO;
	
	@Autowired
	private DriverService driverService;
	
	@Test
	public void firstTest(){
		Driver driver = new Driver();
		driver.setName("testdriver4");
		driverService.saveDriver(driver);
		assertEquals("1","1");
	}
}
