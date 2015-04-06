package hu.aronkatona.service.implementations;

import hu.aronkatona.dao.interfaces.UserResultHistoryDAO;
import hu.aronkatona.hibernateModel.UserResultHistory;
import hu.aronkatona.service.interfaces.UserResultHistoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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

	@Override
	public List<UserResultHistory> getUserResultHistorysByUserId(long userId) {
		return userResultHistoryDAO.getUserResultHistorysByUserId(userId);
	}
	
	
}
