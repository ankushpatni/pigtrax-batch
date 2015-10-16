package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface BarnDao {
	public Integer getBarnPKId(final Integer barnId) throws SQLException;
}
