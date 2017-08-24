package com.event.management.service;

import com.event.management.DAO.CompanyInfoDAO;
import com.event.management.entity.CompanyInfo;
import com.event.management.factory.SerializedSingleton;
import com.event.management.model.CompanyDetails;

public class CompanyInfoService {

	public String saveCompanyInfo(CompanyInfo companyInfo) {
		CompanyInfoDAO companyInfoDAO = SerializedSingleton.getCompanyInfoDAOInstance();
		boolean flag = companyInfoDAO.saveCompanyInfo(companyInfo);
		return flag ? "Company Info Details Saved" : "Company Info details Not Saved";
	}

	public int updateCompanyInfo(CompanyInfo companyInfo) {
		CompanyInfoDAO companyInfoDAO = SerializedSingleton.getCompanyInfoDAOInstance();
		companyInfoDAO.updateCompanyInfo(companyInfo);
		return 1;
	}

	public CompanyDetails getCompanyInfo() {
		CompanyInfoDAO companyInfoDAO = SerializedSingleton.getCompanyInfoDAOInstance();
		return companyInfoDAO.getCompanyInfo();
	}

}
