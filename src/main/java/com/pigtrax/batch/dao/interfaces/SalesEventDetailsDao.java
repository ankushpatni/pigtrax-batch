package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.batch.beans.SalesEventDetails;

public interface SalesEventDetailsDao {
	
	int addSalesEventDetails(final SalesEventDetails salesEventDetails) throws SQLException;
	
}
