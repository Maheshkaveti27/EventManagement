package com.event.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "eventguest")
@JsonInclude(Include.NON_NULL)
public class EventGuest {

	@Id
	@NotNull
	@Column(name = "guestid")
	private int guestId;
	@Column(name = "guestname")
	private String guestName;
	@Column(name = "guestphone")
	private String guestPhone;
	@Column(name = "eventid")
	private int eventId;
	@Null
	@Column(name = "tableid")
	private Integer tableId;
	@Column(name = "seatno")
	private Integer seatNo;
	@Column(name = "enabled")
	private int enabled;
	@Column(name = "allottedtableno")
	private String allottedTableNo;
	@Column(name = "sametable")
	private String sameTable;
	@Column(name = "notsametable")
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

	public String getSameTable() {
		return sameTable;
	}

	public void setSameTable(String sameTable) {
		this.sameTable = sameTable;
	}

	public String getNotSameTable() {
		return notSameTable;
	}

	public void setNotSameTable(String notSameTable) {
		this.notSameTable = notSameTable;
	}
}
