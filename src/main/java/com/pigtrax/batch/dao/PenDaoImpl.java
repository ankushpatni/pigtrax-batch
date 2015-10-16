package com.pigtrax.batch.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.PenDao;

@Repository
@Transactional
public class PenDaoImpl implements PenDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(PenDaoImpl.class);

	@Override
	public Integer getPenPKId(final Integer penId) throws SQLException {
		Integer retVal = null;
		logger.debug("penId is :" + penId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Pen\" where \"penId\" = ?");
		final String qry = qryBuffer.toString();
		retVal = jdbcTemplate.queryForObject(qry, Integer.class, penId);
		logger.debug("retVal is :" + retVal);
		return retVal;
	}

}
