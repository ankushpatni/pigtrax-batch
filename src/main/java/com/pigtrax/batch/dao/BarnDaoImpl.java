package com.pigtrax.batch.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.BarnDao;

@Repository
@Transactional
public class BarnDaoImpl implements BarnDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(BarnDaoImpl.class);

	@Override
	public Integer getBarnPKId(final Integer barnId) throws SQLException {
		Integer retVal = null;
		logger.debug("barnId is :" + barnId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Barn\" where \"barnId\" = ?");
		final String qry = qryBuffer.toString();
		if(barnId != null){
			retVal = jdbcTemplate.queryForObject(qry, Integer.class, barnId.toString());
		}
		logger.debug("retVal is :" + retVal);
		return retVal;
	}

}
