package com.event.management.DAO;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.Event;

public class EventDAO {

	private EntityManager entityManager = null;

	private void close() {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		// shutdown();
	}

	private void shutdown() {
		EntityManager em = ConnectionUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("SHUTDOWN").executeUpdate();
		em.close();
	}

	public List<Event> getTixQuery() throws NamingException {
		EntityTransaction tx = entityManager.getTransaction();
		if (!tx.isActive())
			tx.begin();
		List<Event> listEvents = entityManager.createQuery("SELECT e FROM Event e").getResultList();
		entityManager.getTransaction().commit();
		// entityManager.close();
		return listEvents;
	}

	public boolean saveEvent(Event event) {
		entityManager = ConnectionUtil.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.persist(event);
			tx.commit();
		} catch (Exception t) {
			t.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			close();
		}
		return true;
	}

	public List<Event> getAllEvents() {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery("from Event e where e.enabled=1");
		List<Event> events = query.list();
		return events;
	}

	public int updateEventDetails(Event event) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
			Query query = session
					.createQuery("update Event e set e.eventName=:eventname, e.venue=:venue,e.eventDate=:eventdate,e.noOfGuests=:guestscount where e.eventId = :eventid");
			tx.begin();
			query.setParameter("eventname", event.getEventName());
			query.setParameter("venue", event.getVenue());
			query.setParameter("eventdate", event.getEventDate());
			query.setParameter("guestscount", event.getNoOfGuests());
			query.setParameter("eventid", event.getEventId());
			count = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return count;
	}

	public int deleteEvent(int eventId) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
			Query query = session.createQuery("update Event e set e.enabled=:enabled where e.eventId = :eventId");
			tx.begin();
			query.setParameter("eventId", eventId);
			query.setParameter("enabled", 0);
			count = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return count;
	}

	public List<Event> getEventByEventId(int eventId) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery("from Event e where e.enabled=1 and e.eventId=:eventid");
		query.setParameter("eventid", eventId);
		List<Event> events = query.list();
		return events;
	}

}
