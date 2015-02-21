package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.User;

import java.util.List;

public interface UserService {

	public void saveUser(User user);
	public List<User> getUsers();
	public User getUserById(long id);
	public void deleteUser(long id);
}
