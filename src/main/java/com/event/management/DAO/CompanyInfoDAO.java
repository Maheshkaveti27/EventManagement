package com.event.management.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Query;
import org.hibernate.Session;

import com.event.management.courierconnection.ConnectionUtil;
import com.event.management.entity.CompanyInfo;
import com.event.management.model.CompanyDetails;

public class CompanyInfoDAO {

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

	public boolean saveCompanyInfo(CompanyInfo companyInfo) {
		entityManager = ConnectionUtil.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.persist(companyInfo);
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

	public void updateCompanyInfo(CompanyInfo companyInfo) {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			entityManager.merge(companyInfo);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			close();
		}
	}

	public CompanyDetails getCompanyInfo() {
		entityManager = ConnectionUtil.getEntityManager();
		Session session = (Session) entityManager.getDelegate();
		Query query = session
				.createQuery("from CompanyInfo c");
		List<CompanyInfo> companyInfos = query.list();
		Long count = (Long) session.createQuery("select count(*) from  User")
                .uniqueResult();
		CompanyDetails companyDetails = null;
		if(companyInfos != null && companyInfos.size() > 0) {
			companyDetails = new CompanyDetails();
			companyDetails.setCompanyAddress(companyInfos.get(0).getCompanyAddress());
			companyDetails.setCompanyEmail(companyInfos.get(0).getCompanyEmail());
			companyDetails.setCompanyId(companyInfos.get(0).getCompanyId());
			companyDetails.setCompanyName(companyInfos.get(0).getCompanyName());
			companyDetails.setCompanyPhone(companyInfos.get(0).getCompanyPhone());
			companyDetails.setCount(Integer.parseInt(count.toString()));
			
		}
		return companyDetails;
	}
}
