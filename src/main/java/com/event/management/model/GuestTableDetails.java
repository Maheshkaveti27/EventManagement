package com.event.management.model;

public class GuestTableDetails {

	private int guestId;
	private String guestName;
	private String guestPhone;
	private int eventId;
	private Integer tableId;
	private Integer seatNo;
	private int enabled;
	private String allottedTableNo;

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getAllottedTableNo() {
		return allottedTableNo;
	}

	public void setAllottedTableNo(String allottedTableNo) {
		this.allottedTableNo = allottedTableNo;
	}

}
