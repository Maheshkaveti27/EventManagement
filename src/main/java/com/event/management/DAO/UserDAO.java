package com.event.management.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.User;
import com.event.management.resource.MissingFileException;

public class UserDAO {

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

	public boolean saveEventPlanner(User user) throws MissingFileException {
		entityManager = ConnectionUtil.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.persist(user);
			tx.commit();
		} catch (Exception t) {
			t.printStackTrace();
			tx.rollback();
			// return false;
			throw new MissingFileException("message");
		} finally {
			close();
		}
		return true;
	}

	public int updatePasword(User user) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
		Query query = session
				.createQuery("update User u set u.password=:password where u.username = :username and u.enabled=1");
		tx.begin();
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
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

	public List<User> getEventPlanners() {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session
				.createQuery("from User u where u.enabled=1");
		List<User> users = query.list();
		return users;
	}

	public int updateEventPlannerDetails(User user) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
		Query query = session
				.createQuery("update User u set u.firstName=:firstname, u.lastName=:lastname,u.email=:email,u.phone=:phone where u.username = :username and u.enabled=1");
		tx.begin();
		query.setParameter("username", user.getUsername());
		query.setParameter("firstname", user.getFirstName());
		query.setParameter("lastname", user.getLastName());
		query.setParameter("email", user.getEmail());
		query.setParameter("phone", user.getPhone());
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

	public int deleteEventPlanner(String username) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
		Query query = session
				.createQuery("update User u set u.enabled=:enabled where u.username = :username");
		tx.begin();
		query.setParameter("username", username);
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

	public User getEventPannerByUsername(String username) {
		entityManager = ConnectionUtil.getEntityManager();
		User user = null;
		try {
		javax.persistence.Query query = entityManager
				.createQuery("from User u where u.enabled=1 and u.username like'%"+username+"%'");
		user = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
}
