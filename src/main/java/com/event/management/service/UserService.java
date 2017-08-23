package com.event.management.service;

import java.util.List;

import com.event.management.DAO.UserDAO;
import com.event.management.entity.User;
import com.event.management.factory.SerializedSingleton;
import com.event.management.resource.MissingFileException;

public class UserService {

	public String SaveEventPlanner(User user) throws MissingFileException {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		boolean flag = userDAO.saveEventPlanner(user);
		return flag ? "Event Planner Details Saved" : "Event Planner details Not Saved";
	}

	public int updatePassword(User user) {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		int count = userDAO.updatePasword(user);
		return count;
	}

	public List<User> getEventPanners() {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		return userDAO.getEventPlanners();
	}

	public int updateEventPlannerDetails(User user) {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		int count = userDAO.updateEventPlannerDetails(user);
		return count;
	}
	
	public int deleteEventPlanner(String username) {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		int count = userDAO.deleteEventPlanner(username);
		return count;
	}

	public User getEventPannerByUsername(String username) {
		UserDAO userDAO = SerializedSingleton.getUserDAOInstance();
		User user = userDAO.getEventPannerByUsername(username);
		return user;
	}

}
