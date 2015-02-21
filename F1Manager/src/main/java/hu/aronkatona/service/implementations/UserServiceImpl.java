package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserDAO;
import hu.aronkatona.hibernateModel.User;
import hu.aronkatona.service.interfaces.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Override
	public User getUserById(long id) {
		return userDAO.getUserById(id);
	}

	@Override
	public void deleteUser(long id) {
		userDAO.deleteUser(id);
	}

	
}
