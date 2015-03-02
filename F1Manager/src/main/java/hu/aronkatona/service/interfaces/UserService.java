package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.User;

import java.util.List;

public interface UserService {

	public void saveNewUser(User user);
	public void saveUser(User user);
	public void activateUser(User user);
	public List<User> getUsers();
	public User getUserById(long id);
	public User getUserByActivationCode(String activationCode);
	public void deleteUser(long id);
}
