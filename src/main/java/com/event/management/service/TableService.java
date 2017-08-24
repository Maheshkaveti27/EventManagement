package com.event.management.service;

import java.util.List;

import com.event.management.DAO.TableDAO;
import com.event.management.entity.Table;
import com.event.management.factory.SerializedSingleton;

public class TableService {

	public List<Table> getTablesDetails() {
		TableDAO tableDAO = SerializedSingleton.getTableDAOInstance();
		return tableDAO.getTablesDetails();
	}

	public Table getTableDetails(int tableId) {
		TableDAO tableDAO = SerializedSingleton.getTableDAOInstance();
		return tableDAO.getTableDetails(tableId);
	}

}
