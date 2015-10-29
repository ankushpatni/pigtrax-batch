package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.FarrowEventDao;

@Repository
@Transactional
public class FarrowEventDaoImpl implements FarrowEventDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(FarrowEventDaoImpl.class);
	
	@Override
	public Integer getFarrowEventId(Integer pigInfoId, Date farrowEventDate) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"FarrowEvent\" where \"id_PigInfo\" = ? and \"farrowDateTime\"::date = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;		
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pigInfoId);
					ps.setDate(2, new java.sql.Date(farrowEventDate.getTime()));
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("farrowEvent Id retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		
		return null;
	}
	
	@Override
	public Integer getFarrowEventId(Integer pigInfoId) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"FarrowEvent\" where \"id_PigInfo\" = ? order by \"id\" desc ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;		
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pigInfoId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("farrowEvent Id retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		
		return null;
	}
}
