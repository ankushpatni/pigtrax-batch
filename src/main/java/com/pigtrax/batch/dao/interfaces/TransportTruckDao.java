package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface TransportTruckDao {
	
	/**
	 * Load the TransportTruck information based on auto generated generatedTransportTruckId
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	
	public Integer findByTransportTruckByTruckNumber(final String trucknumber) throws SQLException;

}
