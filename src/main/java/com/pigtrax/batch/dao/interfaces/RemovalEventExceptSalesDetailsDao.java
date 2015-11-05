package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.RemovalEventExceptSalesDetails;

public interface RemovalEventExceptSalesDetailsDao {
	
	
	int addRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws SQLException;
}
