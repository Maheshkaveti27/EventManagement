package com.event.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.event.management.DAO.GuestDAO;
import com.event.management.entity.Event;
import com.event.management.entity.EventGuest;
import com.event.management.entity.Table;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.GuestNewTableDetails;

public class GuestService {
	private static Map<Integer, String> proposedGuestMap = null;

	public GuestService() {
		proposedGuestMap = new HashMap<Integer, String>();
		proposedGuestMap.put(20, "2,1");
		proposedGuestMap.put(50, "2,1");
		proposedGuestMap.put(100, "2,2");
		proposedGuestMap.put(200, "3,2");
	}

	public String saveEventGuest(EventGuest eventGuest) {
		GuestDAO guestDAO = SerializedSingleton.getGuestDAOInstance();
		boolean flag = guestDAO.saveEventGuest(eventGuest);
		return flag ? "Event Guest Details Saved" : "Event guest details Not Saved";
	}

	public List<EventGuest> getAllGuestsByEventId(int eventId) {
		GuestDAO guestDAO = SerializedSingleton.getGuestDAOInstance();
		return guestDAO.getAllGuestsByEventId(eventId);
	}

	public String generateSittingArrangment(int eventId, int tableId) {
		EventService eventService = SerializedSingleton.getEventServiceInstance();
		Event event = eventService.getEventByEventId(eventId);
		Table table = SerializedSingleton.getTableServiceInstance().getTableDetails(tableId);
		int count = 0;
		List<EventGuest> eventGuests = new ArrayList(event.getEventGuests());
		String guestsSameOrNot = proposedGuestMap.get(eventGuests.size());
		String str[] = guestsSameOrNot.split(",");
		Integer sameTableGuestCount = Integer.parseInt(str[0]);
		Integer notSameTableGuestCount = Integer.parseInt(str[1]);
		int allottedTableNo = 1;
		int intitialGuestId = 0;
		if(!eventGuests.isEmpty())
			intitialGuestId = eventGuests.get(0).getGuestId()-1;
		while (count < eventGuests.size()) {
			int seatNo = 1;
			for (int j = 0; j < table.getCapacity(); j++) {
				if (count == eventGuests.size())
					break;
				EventGuest eventGuest = eventGuests.get(count);
				eventGuest.setSeatNo(seatNo);
				eventGuest.setTableId(table.getTableId());
				eventGuest.setAllottedTableNo("T" + allottedTableNo);
	
				if(count+eventGuests.size()%table.getCapacity() < eventGuests.size()) {
					StringBuilder sb = new StringBuilder("");
					for (int x = 0; x < sameTableGuestCount.intValue(); x++) {
						/*sb.append(eventGuest.getGuestId() + 1 + x > table.getCapacity()* allottedTableNo ? 
								((eventGuest.getGuestId() + 1 + x) - table.getCapacity()) : 
									(eventGuest.getGuestId() + 1 + x)).
									append(",");*/
						
						sb.append(eventGuest.getGuestId() + 1 + x > intitialGuestId+(table.getCapacity()* allottedTableNo) ? 
								((eventGuest.getGuestId() + 1 + x) - table.getCapacity()) : 
									(eventGuest.getGuestId() + 1 + x)).
									append(",");
						
						
					}
					eventGuest.setSameTable(sb.deleteCharAt(sb.length()-1).toString());
				} else {
					if(eventGuests.size()%table.getCapacity() == 0) {
						StringBuilder sb = new StringBuilder("");
						for (int x = 0; x < sameTableGuestCount.intValue(); x++) {
							sb.append(eventGuest.getGuestId() + 1 + x > table.getCapacity()* allottedTableNo ? 
									((eventGuest.getGuestId() + 1 + x) - table.getCapacity()) : 
										(eventGuest.getGuestId() + 1 + x)).
										append(",");
						}
						eventGuest.setSameTable(sb.deleteCharAt(sb.length()-1).toString());
					} else {
					eventGuest.setSameTable(String.valueOf(eventGuest.getGuestId()+1==intitialGuestId+eventGuests.size() ? intitialGuestId+eventGuests.size() : eventGuest.getGuestId()-1));
					}
				}
					StringBuilder notSameSB = new StringBuilder("");
					if (eventGuest.getGuestId() % 2 != 0) {
					for (int x = 0; x < notSameTableGuestCount.intValue(); x++) {
						if(x==0)
						notSameSB
								.append(eventGuest.getGuestId() + table.getCapacity() > intitialGuestId+eventGuests.size() ? (eventGuest
										.getGuestId() + table.getCapacity() - eventGuests.size())
										: eventGuest.getGuestId() + table.getCapacity()).append(",");
						else 
							notSameSB
							.append(eventGuest.getGuestId() + table.getCapacity()*2 > intitialGuestId+eventGuests.size() ? (eventGuest
									.getGuestId() + table.getCapacity()*2 - eventGuests.size())
									: eventGuest.getGuestId() + table.getCapacity()*2).append(",");
					}
					eventGuest.setNotSameTable(notSameSB.deleteCharAt(notSameSB.length()-1).toString());
					
				}
				count++;
				seatNo++;
			}
			allottedTableNo++;
		}
		GuestDAO guestDAO = SerializedSingleton.getGuestDAOInstance();
		guestDAO.arrangeSeating(eventGuests);

		return "Seating generated successfully";
	}

	private String getGuestsConfig(int guestsCount) {
		return proposedGuestMap.get(guestsCount);
	}

	/*
	 * public String generateSittingArrangment(int eventId) { EventService
	 * eventService = SerializedSingleton.getEventServiceInstance(); Event event
	 * = eventService.getEventByEventId(eventId); List<Table> tables =
	 * SerializedSingleton.getTableServiceInstance().getTablesDetails(); int
	 * count = 0; List<EventGuest> eventGuests = new
	 * ArrayList(event.getEventGuests()); int allottedTableNo = 1; while (count
	 * < eventGuests.size()) { for (Table table : tables) { int seatNo = 1; for
	 * (int j = 0; j < table.getCapacity(); j++) { if (count ==
	 * eventGuests.size()) break; EventGuest eventGuest =
	 * eventGuests.get(count); eventGuest.setSeatNo(seatNo);
	 * eventGuest.setTableId(table.getTableId());
	 * eventGuest.setAllottedTableNo("T" + allottedTableNo); count++; seatNo++;
	 * } allottedTableNo++; } } GuestDAO guestDAO =
	 * SerializedSingleton.getGuestDAOInstance();
	 * guestDAO.arrangeSeating(eventGuests);
	 * 
	 * return "Seating generated successfully"; }
	 */

	public Map<String, List<EventGuest>> getEventTablesData(int eventId) {
		EventService eventService = SerializedSingleton.getEventServiceInstance();
		Event event = eventService.getEventByEventId(eventId);
		Map<String, List<EventGuest>> tablesAndGuestList = prepareTableAndGuestList(event.getEventGuests());
		return tablesAndGuestList;
	}

	private Map<String, List<EventGuest>> prepareTableAndGuestList(Set<EventGuest> eventGuests) {

		Map<String, List<EventGuest>> tablesAndGuestList = new HashMap<String, List<EventGuest>>();
		for (EventGuest eventGuest : eventGuests) {
			if (eventGuest.getAllottedTableNo() != null && tablesAndGuestList.containsKey(eventGuest.getAllottedTableNo())) {
				List<EventGuest> guests = tablesAndGuestList.get(eventGuest.getAllottedTableNo());
				guests.add(eventGuest);
				tablesAndGuestList.put(eventGuest.getAllottedTableNo(), guests);
			} else {
				if(eventGuest.getAllottedTableNo() != null) {
				List<EventGuest> guests = new ArrayList<EventGuest>();
				guests.add(eventGuest);
				tablesAndGuestList.put(eventGuest.getAllottedTableNo(), guests);
				}
			}
		}
		return tablesAndGuestList;
	}

	public String updateGuestTable(GuestNewTableDetails guestNewTableDetails) {
		GuestDAO guestDAO = SerializedSingleton.getGuestDAOInstance();
		int count = guestDAO.updateGuestTable(guestNewTableDetails);
		return count > 0 ? "Table details updated" : "Table details not updated/Guest not allowed to sit";
	}

	public String generateSittingArrangmentNew(int eventId) {
		EventService eventService = SerializedSingleton.getEventServiceInstance();
		Event event = eventService.getEventByEventId(eventId);
		List<Table> tables = SerializedSingleton.getTableServiceInstance().getTablesDetails();
		int count = 0;
		List<EventGuest> eventGuests = new ArrayList(event.getEventGuests());
		int allottedTableNo = 1;
		while (count < eventGuests.size()) {
			for (Table table : tables) {
				int seatNo = 1;
				for (int j = 0; j < table.getCapacity(); j++) {
					if (count == eventGuests.size())
						break;
					EventGuest eventGuest = eventGuests.get(count);
					eventGuest.setSeatNo(seatNo);
					eventGuest.setTableId(table.getTableId());
					eventGuest.setAllottedTableNo("T" + allottedTableNo);
					if (eventGuest.getGuestId() % 2 != 0) {

					}
					count++;
					seatNo++;
				}
				allottedTableNo++;
			}
		}
		GuestDAO guestDAO = SerializedSingleton.getGuestDAOInstance();
		guestDAO.arrangeSeating(eventGuests);

		return "Seating generated successfully";
	}
}
