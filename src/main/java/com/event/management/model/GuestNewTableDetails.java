package com.event.management.model;

public class GuestNewTableDetails {

	private int guestId;
	private String guestName;
	private String guestPhone;
	private int eventId;
	private Integer tableId;
	private Integer existingSeatNo;
	private int enabled;
	private String existingAllottedTableNo;
	private String newAllottedTableNo;
	private Integer newSeatNo;
	private String notSameTable;
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
	public Integer getExistingSeatNo() {
		return existingSeatNo;
	}
	public void setExistingSeatNo(Integer existingSeatNo) {
		this.existingSeatNo = existingSeatNo;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getExistingAllottedTableNo() {
		return existingAllottedTableNo;
	}
	public void setExistingAllottedTableNo(String existingAllottedTableNo) {
		this.existingAllottedTableNo = existingAllottedTableNo;
	}
	public String getNewAllottedTableNo() {
		return newAllottedTableNo;
	}
	public void setNewAllottedTableNo(String newAllottedTableNo) {
		this.newAllottedTableNo = newAllottedTableNo;
	}
	public Integer getNewSeatNo() {
		return newSeatNo;
	}
	public void setNewSeatNo(Integer newSeatNo) {
		this.newSeatNo = newSeatNo;
	}
	public String getNotSameTable() {
		return notSameTable;
	}
	public void setNotSameTable(String notSameTable) {
		this.notSameTable = notSameTable;
	}
}
