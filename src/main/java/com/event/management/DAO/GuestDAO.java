package com.event.management.DAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.EventGuest;
import com.event.management.model.GuestNewTableDetails;
import com.event.management.model.GuestTableDetails;

public class GuestDAO {

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

	public boolean saveEventGuest(EventGuest eventGuest) {
		entityManager = ConnectionUtil.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.persist(eventGuest);
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

	public List<EventGuest> getAllGuestsByEventId(int eventId) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery("from EventGuest e where e.enabled=1 and e.eventId=:eventid ");
		query.setParameter("eventid", eventId);
		List<EventGuest> eventGuests = query.list();
		return eventGuests;
	}

	public void arrangeSeating(List<EventGuest> eventGuests) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
			Query query = session.createQuery("update EventGuest e set e.tableId=:tableid,e.seatNo=:seatno,"
					+ "e.allottedTableNo=:allottedTableno,e.sameTable=:sametable,e.notSameTable=:notsame where e.guestId = :guestid");
			tx.begin();
			for (EventGuest eventGuest : eventGuests) {
				query.setParameter("tableid", eventGuest.getTableId());
				query.setParameter("seatno", eventGuest.getSeatNo());
				query.setParameter("allottedTableno", eventGuest.getAllottedTableNo());
				query.setParameter("guestid", eventGuest.getGuestId());
				query.setParameter("sametable", eventGuest.getSameTable());
				query.setParameter("notsame", eventGuest.getNotSameTable());
				count = query.executeUpdate();
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}

	public int updateGuestTable(GuestNewTableDetails guestNewTableDetails) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		tx.begin();
		int count = 0;
		try {
			Query query = session
					.createQuery("from EventGuest e where e.enabled=1 and e.eventId=:eventid and e.allottedTableNo=:allotedtabNo and "
							+ " e.seatNo=:seatno");
			query.setParameter("eventid", guestNewTableDetails.getEventId());
			query.setParameter("allotedtabNo", guestNewTableDetails.getNewAllottedTableNo());
			query.setParameter("seatno", guestNewTableDetails.getNewSeatNo());
			//query.setParameter("eventid", guestNewTableDetails.getEventId());
			List<EventGuest> eventGuests = query.list();
			if (!eventGuests.isEmpty()) {
						if(!canMoveGuest(eventGuests,guestNewTableDetails))
							return 0;
						if(!canMoveGuest(null, guestNewTableDetails))
							return 0;
				Query updateQuery = session.createQuery("update EventGuest e set e.seatNo=:seatno,"
						+ "e.allottedTableNo=:allottedTableno where e.guestId = :guestid and e.eventId=:eventid");
				updateQuery.setParameter("seatno", guestNewTableDetails.getExistingSeatNo());
				updateQuery.setParameter("allottedTableno", guestNewTableDetails.getExistingAllottedTableNo());
				updateQuery.setParameter("guestid", eventGuests.get(0).getGuestId());
				updateQuery.setParameter("eventid", guestNewTableDetails.getEventId());
				updateQuery.executeUpdate();
			}
			Query newUpdateQuery = session.createQuery("update EventGuest e set e.seatNo=:seatno,"
					+ "e.allottedTableNo=:allottedTableno where e.guestId = :guestid and e.eventId=:eventid");
			newUpdateQuery.setParameter("seatno", guestNewTableDetails.getNewSeatNo());
			newUpdateQuery.setParameter("allottedTableno", guestNewTableDetails.getNewAllottedTableNo());
			newUpdateQuery.setParameter("guestid", guestNewTableDetails.getGuestId());
			newUpdateQuery.setParameter("eventid", guestNewTableDetails.getEventId());
			count = newUpdateQuery.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return count;
	}

	private boolean canMoveGuest(List<EventGuest> eventGuests, GuestNewTableDetails guestNewTableDetails) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		boolean flag = true;
		if(eventGuests != null && !eventGuests.isEmpty()) {
		Query query = session
				.createQuery("from EventGuest e where e.enabled=1 and e.eventId=:eventid and e.allottedTableNo=:allotedtabNo");
		query.setParameter("eventid", guestNewTableDetails.getEventId());
		query.setParameter("allotedtabNo", guestNewTableDetails.getExistingAllottedTableNo());
		query.setParameter("eventid", guestNewTableDetails.getEventId());
		List<EventGuest> eventGuests1 = query.list();
		for(EventGuest eventGuest : eventGuests1){
			if(eventGuest.getNotSameTable()!=null && checkGuestIdExists(eventGuest.getNotSameTable(), String.valueOf(eventGuests.get(0).getGuestId())))
				return false;
		}
		} else {
			Query query = session
					.createQuery("from EventGuest e where e.enabled=1 and e.eventId=:eventid and e.allottedTableNo=:allotedtabNo");
			query.setParameter("eventid", guestNewTableDetails.getEventId());
			query.setParameter("allotedtabNo", guestNewTableDetails.getNewAllottedTableNo());
			//query.setParameter("eventid", guestNewTableDetails.getEventId());
			List<EventGuest> eventGuests1 = query.list();
			for(EventGuest eventGuest : eventGuests1){
				if(eventGuest.getNotSameTable()!=null && checkGuestIdExists(eventGuest.getNotSameTable(), String.valueOf(guestNewTableDetails.getGuestId())))
					return false;
			}
			
		}
		return true;
	}

	private boolean checkGuestIdExists(String notSameTable, String guestId) {
		String strarr[] = notSameTable.split(",");
		for(int i=0;i<strarr.length; i++){
			if (strarr[i].equalsIgnoreCase(guestId))
				return true;
		}
		return false;
	}
}