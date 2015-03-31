package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.DriverDAO;
import hu.aronkatona.hibernateModel.Driver;
import hu.aronkatona.service.interfaces.DriverService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverDAO driverDAO;
	
	@Override
	public void saveDriver(Driver driver) {
		driverDAO.saveDriver(driver);
	}

	@Override
	public List<Driver> getDrivers() {
		return driverDAO.getDrivers();
	}
	
	@Override
	public List<Driver> getDriversOrderByPrice() {
		return driverDAO.getDriversOrderByPrice();
	}

	@Override
	public Driver getDriverById(long id) {
		return driverDAO.getDriverById(id);
	}

	@Override
	public void deleteDriver(long id) {
		driverDAO.deleteDriver(id);
	}

	

}
