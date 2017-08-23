package com.event.management.factory;

import java.io.Serializable;

import com.event.management.DAO.AuthTokenStore;
import com.event.management.DAO.AuthenticationTokenManager;
import com.event.management.DAO.CompanyInfoDAO;
import com.event.management.DAO.CustomerDAO;
import com.event.management.DAO.EventDAO;
import com.event.management.DAO.GuestDAO;
import com.event.management.DAO.InMemoryTokenStore;
import com.event.management.DAO.LoginDAO;
import com.event.management.DAO.TableDAO;
import com.event.management.DAO.UserDAO;
import com.event.management.service.CompanyInfoService;
import com.event.management.service.CustomerService;
import com.event.management.service.EventService;
import com.event.management.service.GuestService;
import com.event.management.service.LoginService;
import com.event.management.service.TableService;
import com.event.management.service.UserService;

public class SerializedSingleton implements Serializable {

	private static final long serialVersionUID = -7604766932017737115L;

	private SerializedSingleton() {
	}

	private static class EventServiceHelper {
		private static final EventService instance = new EventService();
	}

	public static EventService getEventServiceInstance() {
		return EventServiceHelper.instance;
	}

	private static class EventDAOHeloper {
		private static final EventDAO instance = new EventDAO();
	}

	public static EventDAO getEventDAOInstance() {
		return EventDAOHeloper.instance;
	}

	private static class AuthTokenHelper {
		private static final AuthTokenStore instance = new InMemoryTokenStore();
	}

	public static AuthTokenStore getAuthTokenStoreInstance() {
		return AuthTokenHelper.instance;
	}

	private static class LoginDAOHelper {
		private static final LoginDAO instance = new LoginDAO();
	}

	public static LoginDAO getLoginDAOInstance() {
		return LoginDAOHelper.instance;
	}

	private static class LoginServiceHelper {
		private static final LoginService instance = new LoginService();
	}

	public static LoginService getLoginServiceInstance() {
		return LoginServiceHelper.instance;
	}

	private static class AuthTokenManager {
		private static final AuthenticationTokenManager instance = new AuthenticationTokenManager();
	}

	public static AuthenticationTokenManager getAuthTokenManager() {
		return AuthTokenManager.instance;
	}

	private static class UserServiceHelper {
		private static final UserService instance = new UserService();
	}

	public static UserService getUserServiceInstance() {
		return UserServiceHelper.instance;
	}

	private static class UserDAOHelper {
		private static final UserDAO instance = new UserDAO();
	}

	public static UserDAO getUserDAOInstance() {
		return UserDAOHelper.instance;
	}

	private static class CompanyInfoServiceHelper {
		private static final CompanyInfoService instance = new CompanyInfoService();
	}

	public static CompanyInfoService getCompanyInfoServiceInstance() {
		return CompanyInfoServiceHelper.instance;
	}

	private static class CompanyInfoDAOHelper {
		private static final CompanyInfoDAO instance = new CompanyInfoDAO();
	}

	public static CompanyInfoDAO getCompanyInfoDAOInstance() {
		return CompanyInfoDAOHelper.instance;
	}

	private static class CustomerServiceHelper {
		private static final CustomerService instance = new CustomerService();
	}

	public static CustomerService getCustomerServiceInstance() {
		return CustomerServiceHelper.instance;
	}

	private static class CustomerDAOHelper {
		private static final CustomerDAO instance = new CustomerDAO();
	}

	public static CustomerDAO getCustomerDAOInstance() {
		return CustomerDAOHelper.instance;
	}
	
	private static class TableServiceHelper {
		private static final TableService instance = new TableService();
	}

	public static TableService getTableServiceInstance() {
		return TableServiceHelper.instance;
	}

	private static class TableDAOHelper {
		private static final TableDAO instance = new TableDAO();
	}

	public static TableDAO getTableDAOInstance() {
		return TableDAOHelper.instance;
	}
	
	private static class GuestServiceHelper {
		private static final GuestService instance = new GuestService();
	}

	public static GuestService getGuestServiceInstance() {
		return GuestServiceHelper.instance;
	}

	private static class GuestDAOHelper {
		private static final GuestDAO instance = new GuestDAO();
	}

	public static GuestDAO getGuestDAOInstance() {
		return GuestDAOHelper.instance;
	}
	
	

}
