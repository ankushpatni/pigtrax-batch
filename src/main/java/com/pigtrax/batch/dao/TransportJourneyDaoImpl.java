package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;

@Repository
@Transactional
public class TransportJourneyDaoImpl implements TransportJourneyDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(TransportJourneyDaoImpl.class);

	@Override
	public Integer getTranportJrnyIdByStartDat(Map<String, Object> criteriaMap) {

		Date jrnyStartDate = (Date) criteriaMap.get("jrnyStartDate");
		logger.debug("jrnyStartDate is :" + jrnyStartDate);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select \"id\" from pigtrax.\"TransportJourney\" where \"journeyStartTime\" = ? ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (jrnyStartDate != null) {
					ps.setDate(1, new java.sql.Date(jrnyStartDate.getTime()));
				} else {
					ps.setNull(1, java.sql.Types.DATE);
				}

			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
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
