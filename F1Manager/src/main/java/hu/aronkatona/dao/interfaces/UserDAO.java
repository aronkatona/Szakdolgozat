package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.User;

import java.util.List;

public interface UserDAO {

	public void saveUser(User user);
	public List<User> getUsers();
	public List<User> getUsersOrderByActualPoint(int pageNumber);
	public User getUserById(long id);
	public User getUserByActivationCode(String activationCode);
	public User getUserByChangePasswordToken(String changePasswordToken);
	public void deleteUser(long id);
	public User userExistByEmail(String email);
	public User userExistByName(String name);
	public User userByName(String name);
	public User userByEmail(String email);
	public long getNumberOfRows();
}
