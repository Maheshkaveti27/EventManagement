package com.event.management.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.Table;

public class TableDAO {
	
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

	public List<Table> getTablesDetails() {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session
				.createQuery("from Table t where t.enabled=1");
		List<Table> tables = query.list();
		return tables;
	}

	public Table getTableDetails(int tableId) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session
				.createQuery("from Table t where t.enabled=1 and t.tableId=:tableid");
		query.setParameter("tableid", tableId);
		List<Table> tables = query.list();
		return tables.size() > 0 ? tables.get(0) : null;
	}

}
