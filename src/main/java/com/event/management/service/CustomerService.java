package com.event.management.service;

import java.util.List;

import com.event.management.DAO.CustomerDAO;
import com.event.management.entity.Customer;
import com.event.management.factory.SerializedSingleton;

public class CustomerService {

	public String saveCustomer(Customer customer) {
		CustomerDAO customerDAO = SerializedSingleton.getCustomerDAOInstance();
		boolean flag = customerDAO.saveCustomer(customer);
		return flag ? "Customer Details Saved" : "Customer details Not Saved";
	}

	public List<Customer> getAllCustomers() {
		CustomerDAO customerDAO = SerializedSingleton.getCustomerDAOInstance();
		return customerDAO.getAllCustomers();
	}

	public int updateCustomerDetails(Customer customer) {
		CustomerDAO customerDAO = SerializedSingleton.getCustomerDAOInstance();
		int count = customerDAO.updateCustomerDetails(customer);
		return count;
	}

	public int deleteCustomer(int customerNo) {
		CustomerDAO customerDAO = SerializedSingleton.getCustomerDAOInstance();
		int count = customerDAO.deleteCustomer(customerNo);
		return count;
	}
}
