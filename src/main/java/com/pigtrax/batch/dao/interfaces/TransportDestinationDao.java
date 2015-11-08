package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface TransportDestinationDao {
	
	public Integer findByTransportDestinationName(final String destinationName) throws SQLException;

}
