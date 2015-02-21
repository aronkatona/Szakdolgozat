package hu.aronkatona.service.implementations;

import java.util.List;

import hu.aronkatona.dao.interfaces.UserResultHistoryDAO;
import hu.aronkatona.hibernateModel.UserResultHistory;
import hu.aronkatona.service.interfaces.UserResultHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResultHistoryServiceImpl implements UserResultHistoryService{

	@Autowired
	private UserResultHistoryDAO userResultHistoryDAO;

	@Override
	public void saveUserResultHistory(UserResultHistory userResultHistory) {
		userResultHistoryDAO.saveUserResultHistory(userResultHistory);
	}

	@Override
	public List<UserResultHistory> getUserResultHistorys() {
		return userResultHistoryDAO.getUserResultHistorys();
	}

	@Override
	public UserResultHistory getUserResultHistoryById(long id) {
		return userResultHistoryDAO.getUserResultHistoryById(id);
	}

	@Override
	public void deleteUserResultHistory(long id) {
		userResultHistoryDAO.deleteUserResultHistory(id);
	}
	
	
}
