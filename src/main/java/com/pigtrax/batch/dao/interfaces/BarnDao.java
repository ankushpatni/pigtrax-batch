package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface BarnDao {
	public Integer getBarnPKId(final String barnId) throws SQLException;
	
	public Integer getBarnIdByRoom(final Integer roomId) throws SQLException;
}
