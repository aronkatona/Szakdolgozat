package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Driver;

import java.util.List;

public interface DriverDAO {

	public void saveDriver(Driver driver);
	public List<Driver> getDrivers();
	public List<Driver> getDriversOrderByPrice();
	public Driver getDriverById(long id);
	public void deleteDriver(long id);
}
