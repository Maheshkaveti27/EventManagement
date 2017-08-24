package com.event.management.service;

import java.util.List;

import javax.naming.NamingException;

import com.event.management.DAO.EventDAO;
import com.event.management.entity.Event;
import com.event.management.factory.SerializedSingleton;

public class EventService {

	public List<Event> getTixQuery() {
		List<Event> events = null;
		try {
			events = SerializedSingleton.getEventDAOInstance().getTixQuery();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return events;
	}

	public String saveEvent(Event event) {
		EventDAO eventDAO = SerializedSingleton.getEventDAOInstance();
		boolean flag = eventDAO.saveEvent(event);
		return flag ? "Event Details Saved" : "Event details Not Saved";
	}

	public List<Event> getAllEvents() {
		EventDAO eventDAO = SerializedSingleton.getEventDAOInstance();
		return eventDAO.getAllEvents();
	}

	public int updateEventDetails(Event event) {
		EventDAO eventDAO = SerializedSingleton.getEventDAOInstance();
		int count = eventDAO.updateEventDetails(event);
		return count;
	}

	public int deleteEvent(int eventId) {
		EventDAO eventDAO = SerializedSingleton.getEventDAOInstance();
		int count = eventDAO.deleteEvent(eventId);
		return count;
	}
	
	public Event getEventByEventId(int eventId) {
		EventDAO eventDAO = SerializedSingleton.getEventDAOInstance();
		List<Event> events = eventDAO.getEventByEventId(eventId);
		return  events != null && events.size() > 0 ? events.get(0) : null;
	}

}
