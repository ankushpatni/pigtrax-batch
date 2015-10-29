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

import com.pigtrax.batch.dao.interfaces.GroupEventDao;

@Repository
public class GroupEventDaoImpl implements GroupEventDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(GroupEventDaoImpl	.class);

	
	@Override
	public Integer getGroupEventId(String groupId, int companyId) throws SQLException { 
		logger.debug("groupId/company Ids are :" + groupId+"/"+companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"GroupEvent\" where lower(\"groupId\") = ? and \"id_Company\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (groupId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId.trim().toLowerCase());
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
			logger.debug("groupId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;

	}
	
}
