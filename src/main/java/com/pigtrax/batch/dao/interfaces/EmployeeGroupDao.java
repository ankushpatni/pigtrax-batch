package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface EmployeeGroupDao {
	public Integer getEmployeeGroupPKId(Integer companyId, String groupId) throws SQLException;

	public Integer getEmployeeGroupPKId(final String employeeGroup) throws SQLException;
}
