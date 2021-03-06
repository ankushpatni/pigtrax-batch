package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface PenDao {
	public Integer getPenPKId(final String penId) throws SQLException;	
	
	public Integer getPenPKId(final String penId, final Integer companyId) throws SQLException;
	
	
	public Integer getPenPKId(final String penId, final Integer companyId, final Integer premiseId) throws SQLException;
}
