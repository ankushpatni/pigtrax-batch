package com.pigtrax.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.dao.interfaces.CompanyDao;

@Repository
@Transactional
public class CompanyDaoImpl implements CompanyDao {

	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class);

	@Override
	public Integer getCompanyId(String companyId) throws SQLException {
		List<Integer> retValList = new ArrayList<Integer>(0);
		logger.debug("companyId is :" + companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Company\" where \"companyId\" = ?");
		final String qry = qryBuffer.toString();
		if (companyId != null) {
			retValList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, companyId);
				}
			}, new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rownum) throws SQLException {
					return rs.getInt(rownum);
				}
			});
		}
		if (retValList.size() > 0) {
			logger.debug("retVal is :" + retValList.get(0));
			return retValList.get(0);
		}
		return null;
	}

}
