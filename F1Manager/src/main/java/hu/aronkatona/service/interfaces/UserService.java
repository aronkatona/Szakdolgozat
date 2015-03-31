package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface UserService {

	public void saveNewUser(User user);
	public void saveUser(User user);
	public void saveUserWithNewPassword(User user);
	public void sellDriver(long userId, int position);
	public void sellTeam(long userId, int position);
	public void buyDriver(long userId, long driverId, int position) throws Exception;
	public void buyTeam(long userId, long teamId, int position) throws Exception;
	public void activateUser(User user);
	public List<User> getUsers();
	public List<User> getUsersOrderByActualPoint(int pageNumber);
	public User getUserById(long id);
	public User getUserByActivationCode(String activationCode);
	public User getUserByChangePasswordToken(String changePasswordToken);
	public void deleteUser(long id);
	public User userExistByEmail(String email);
	public User userExistByName(String name);
	public User userLogin(String name,String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
	public void sendMailToUserWithNewActivationCode(long id);
	public void sendNewPasswordToken(String email);
	public long getNumberOfRows();
}
