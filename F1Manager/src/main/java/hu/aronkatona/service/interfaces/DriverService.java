package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.Driver;

import java.util.List;

public interface DriverService {

	public void saveDriver(Driver driver);
	public List<Driver> getDrivers();
	public Driver getDriverById(long id);
	public void deleteDriver(long id);
}
