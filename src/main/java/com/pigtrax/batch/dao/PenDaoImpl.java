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

import com.pigtrax.batch.dao.interfaces.PenDao;

@Repository
@Transactional
public class PenDaoImpl implements PenDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(PenDaoImpl.class);

	@Override
	public Integer getPenPKId(final Integer penId) throws SQLException {
		List<Integer> retValList = new ArrayList<Integer>(0);
		logger.debug("penId is :" + penId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Pen\" where \"penId\" = ?");
		final String qry = qryBuffer.toString();
		if (penId != null) {
			retValList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, penId.toString());
				}
			}, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rownum) throws SQLException {
					return rs.getInt(rownum);
				}
			});
		}
		logger.debug("retVal is :" + retValList.get(0));
		return retValList.get(0);
	}
}
