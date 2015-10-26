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

import com.pigtrax.batch.dao.interfaces.BreedingEventDao;

@Repository
@Transactional
public class BreedingEventDaoImpl implements BreedingEventDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);

	@Override
	public Integer getBreedingEventPKId(Integer pigInfoId, Date serviceDate)
			throws SQLException {	
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("Select BE.\"id\" from pigtrax.\"BreedingEvent\" BE  where BE.\"id_PigInfo\" = ? and BE.\"serviceStartDate\"::date = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (pigInfoId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pigInfoId);
					ps.setDate(2, new java.sql.Date(serviceDate.getTime()));
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;
		
	}

}
