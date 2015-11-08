package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.TransportTruckDao;

@Repository
@Transactional
public class TransportTruckDaoImpl implements TransportTruckDao{
	
	private static final Logger logger = Logger.getLogger(TransportTruckDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Integer findByTransportTruckByTruckNumber(String trucknumber)
			throws SQLException {
		logger.debug("trucknumber is :" + trucknumber);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer
				.append("select \"id\" from pigtrax.\"TransportTruck\" where \"truckId\" = ? ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (trucknumber != null) {
					ps.setString(1, trucknumber);
				} 

			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException,
					DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
				return null;
			}
		});
		logger.debug("jrnyStartDate retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}

		return null;
	}
}
