package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface EmployeeGroupDao {
	Integer getEmployeeGroupPKId(Integer pigInfoId, String groupId) throws SQLException; 
}
