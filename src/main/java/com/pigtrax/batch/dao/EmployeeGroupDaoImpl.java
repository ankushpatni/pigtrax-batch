package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;

@Repository
@Transactional
public class EmployeeGroupDaoImpl implements EmployeeGroupDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = Logger.getLogger(EmployeeGroupDaoImpl.class);

	@Override
	public Integer getEmployeeGroupPKId(Integer companyId, String groupId) throws SQLException {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer
				.append("Select EG.\"id\" from pigtrax.\"EmployeeGroup\" EG join pigtrax.\"EmployeeJobFunction\" EJF on EG.\"id_EmployeeJobFunction\" = EJF.\"id\" "
						+ " JOIN pigtrax.\"Employee\" E on EJF.\"id_Employee\" = E.\"id\" where EG.\"groupId\" = ? and E.\"id_Company\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (companyId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId);
					ps.setInt(2, companyId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("Employee Group Id retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}

	@Override
	public Integer getEmployeeGroupPKId(String employeeGroup) throws SQLException {
		logger.debug("employeeGroup is :" + employeeGroup);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"EmployeeGroup\" where upper(\"groupId\") = ? ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (employeeGroup != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, employeeGroup.trim().toUpperCase());
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("employeeGroup retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}
}
