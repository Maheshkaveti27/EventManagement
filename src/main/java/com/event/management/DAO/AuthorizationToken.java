package com.event.management.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.event.management.entity.User;

/**
 * @author saitejamacharla
 *
 */
@XmlRootElement
public class AuthorizationToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Integer DEFAULT_TTL = (60*60); 

	private String token;

	private Date timeCreated;

	private Date expirationDate;

	private Date lastAccessedDate;

	private Integer ttl;

	private User user;

	public AuthorizationToken(User user) {
		this(user, DEFAULT_TTL);
	}

	public AuthorizationToken(User user, Integer timeToLiveInSeconds) {
		this.token = UUID.randomUUID().toString();
		this.user = user;
		this.timeCreated = new Date();
		this.lastAccessedDate = new Date();
		this.expirationDate = new Date(System.currentTimeMillis()
				+ (timeToLiveInSeconds * 1000L));
		this.ttl = timeToLiveInSeconds;
	}

	public void resetExpirationDate() {
		this.lastAccessedDate = new Date();
		this.expirationDate = new Date(System.currentTimeMillis()
				+ (ttl * 1000L));

	}

	public Date getLastAccessedDate() {
		return lastAccessedDate;
	}

	public void setLastAccessedDate(Date lastAccessedDate) {
		this.lastAccessedDate = lastAccessedDate;
	}

	public boolean hasExpired() {
		return this.expirationDate != null
				&& this.expirationDate.before(new Date());
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	@Override
	public String toString() {
		return "AuthorizationToken [token=" + token + ", timeCreated="
				+ timeCreated + ", expirationDate=" + expirationDate
				+ ", lastAccessedDate=" + lastAccessedDate + ", ttl=" + ttl
				+ ", appUser=" + user + "]";
	}

}
