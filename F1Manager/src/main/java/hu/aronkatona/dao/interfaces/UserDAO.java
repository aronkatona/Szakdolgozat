package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.User;

import java.util.List;

public interface UserDAO {

	public void saveUser(User user);
	public List<User> getUsers();
	public User getUserById(long id);
	public User getUserByActivationCode(String activationCode);
	public void deleteUser(long id);
}
