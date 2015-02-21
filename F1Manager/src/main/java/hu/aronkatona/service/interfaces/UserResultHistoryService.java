package hu.aronkatona.service.interfaces;

import hu.aronkatona.hibernateModel.UserResultHistory;

import java.util.List;

public interface UserResultHistoryService {

	public void saveUserResultHistory(UserResultHistory userResultHistory);
	public List<UserResultHistory> getUserResultHistorys();
	public UserResultHistory getUserResultHistoryById(long id);
	public void deleteUserResultHistory(long id);
}
