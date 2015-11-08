package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface TransportTrailerDao {
	
	/**
	 * Load the TransportTrailer information based on auto generated generatedTransportTrailerId
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	
	public Integer findByTransportTrailerByTrailerNumberPlate(final String TrailerNumberPlate) throws SQLException;




}
