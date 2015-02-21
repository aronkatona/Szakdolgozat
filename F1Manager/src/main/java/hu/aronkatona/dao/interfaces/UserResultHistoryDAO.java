package hu.aronkatona.dao.interfaces;

import hu.aronkatona.hibernateModel.UserResultHistory;

import java.util.List;

public interface UserResultHistoryDAO {

	public void saveUserResultHistory(UserResultHistory userResultHistory);
	public List<UserResultHistory> getUserResultHistorys();
	public UserResultHistory getUserResultHistoryById(long id);
	public void deleteUserResultHistory(long id);
}
