package com.event.management.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.User;

public class LoginDAO {
	
	
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

	public User authenticate(User appUserExt) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session
				.createQuery("from User u where u.username = :username and u.password = :password");
		query.setParameter("username", appUserExt.getUsername());
		query.setParameter("password", appUserExt.getPassword());
		List list = query.list();
		return (list != null && list.size() > 0) ? (User) list.get(0) : null;
	}

}
