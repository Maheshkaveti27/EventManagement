package com.event.management.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "event")
public class Event implements Serializable {

	@Id
	@NotNull
	@Column(name = "eventid")
	private int eventId;
	@Column(name = "eventname")
	private String eventName;
	@Column(name = "venue")
	private String venue;
	@Column(name = "eventdate")
	private Date eventDate;
	@Column(name = "noofguests")
	private int noOfGuests;
	@Column(name = "username")
	private String username;

	@Column(name = "cid")
	private String customerId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cid", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	@ManyToOne(optional = false)
	@JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
	private User user;

	@Column(name = "enabled")
	private int enabled;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = EventGuest.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "eventid", referencedColumnName = "eventid")
	@OrderBy("guestId ASC")
	private Set<EventGuest> eventGuests;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public int getNoOfGuests() {
		return noOfGuests;
	}

	public void setNoOfGuests(int noOfGuests) {
		this.noOfGuests = noOfGuests;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			this.eventDate = sdf.parse(eventDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<EventGuest> getEventGuests() {
		return eventGuests;
	}

	public void setEventGuests(Set<EventGuest> eventGuests) {
		this.eventGuests = eventGuests;
	}
}
