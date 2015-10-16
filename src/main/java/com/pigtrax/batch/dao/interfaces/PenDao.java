package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface PenDao {
	public Integer getPenPKId(final String penId) throws SQLException;
}
