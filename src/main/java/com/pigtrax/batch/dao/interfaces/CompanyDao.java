package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface CompanyDao {
	public Integer getCompanyId(final String companyId) throws SQLException;
}
