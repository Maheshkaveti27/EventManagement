package com.event.management.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.Customer;

public class CustomerDAO {

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

	public boolean saveCustomer(Customer customer) {
		entityManager = ConnectionUtil.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.persist(customer);
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

	public List<Customer> getAllCustomers() {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session.createQuery("from Customer c where c.enabled=1");
		List<Customer> customers = query.list();
		return customers;
	}

	public int updateCustomerDetails(Customer customer) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
			Query query = session
					.createQuery("update Customer c set c.firstName=:firstname, c.lastName=:lastname,c.customerEmail=:email,c.customerPhoneNo=:phone,c.customerAddress=:address where c.id = :id");
			tx.begin();
			query.setParameter("firstname", customer.getFirstName());
			query.setParameter("lastname", customer.getLastName());
			query.setParameter("email", customer.getCustomerEmail());
			query.setParameter("phone", customer.getCustomerPhoneNo());
			query.setParameter("address", customer.getCustomerAddress());
			query.setParameter("id", customer.getId());
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

	public int deleteCustomer(int customerNo) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Transaction tx = session.getTransaction();
		int count = 0;
		try {
			Query query = session
					.createQuery("update Customer c set c.enabled=:enabled where c.customerNo = :customerno");
			tx.begin();
			query.setParameter("customerno", customerNo);
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

}
