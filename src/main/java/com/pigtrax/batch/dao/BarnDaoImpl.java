package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
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
		List<Integer> retValList = new ArrayList<Integer>(0);
		logger.debug("barnId is :" + barnId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Barn\" where \"barnId\" = ?");
		final String qry = qryBuffer.toString();
		if (barnId != null) {
			retValList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, barnId.toString());
				}
			}, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rownum) throws SQLException {
					return rs.getInt(rownum);
				}
			});
		}
		if(retValList.size() > 0){
			logger.debug("retVal is :" + retValList.get(0));
			return retValList.get(0);
		}
		return null;
	}

}
