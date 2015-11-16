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

import com.pigtrax.batch.dao.interfaces.OriginDao;
@Repository
@Transactional
public class OriginDaoImpl implements OriginDao {
	
	private static final Logger logger = Logger.getLogger(OriginDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	 @Override
	public Integer getOriginId(String origin) {
			StringBuffer qryBuffer = new StringBuffer();
			qryBuffer.append("select id from pigtrax.\"Origin\" where lower(\"name\") = ?");
			final String qry = qryBuffer.toString();
			Long retValList1 = null;
			if (origin != null) {
				retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, origin.trim().toLowerCase());
					}
				}, new ResultSetExtractor<Long>() {
					public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						if (resultSet.next()) {
							return resultSet.getLong(1);
						}
						return null;
					}
				});
				logger.debug("originId retVal is :" + retValList1);
				if (retValList1 != null) {
					return Integer.decode(retValList1.toString());
				}
			}
			return null;
	}
  
}
