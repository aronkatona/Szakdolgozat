package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.Driver;

import java.util.List;

public interface DriverDAO {

	public void saveDriver(Driver driver);
	public List<Driver> getDrivers();
	public List<Driver> getActiveDriversOrderByPrice();
	public List<Driver> getActiveDriversOrderByName();
	public Driver getDriverById(long id);
	public void deleteDriver(long id);
	public boolean existingDriverByIdAndName(long id, String name);
}
