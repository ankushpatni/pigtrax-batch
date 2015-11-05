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

import com.pigtrax.batch.dao.interfaces.PenDao;

@Repository
@Transactional
public class PenDaoImpl implements PenDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(PenDaoImpl.class);

	@Override
	public Integer getPenPKId(final String penId) throws SQLException {
		logger.debug("penId is :" + penId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Pen\" where \"penId\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (penId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, penId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("penId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;
	}
	
	@Override
	public Integer getPenPKId(final String penId, final Integer companyId) throws SQLException {
		logger.debug("penId is :" + penId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"Pen\" where \"penId\" = ? and \"id_Room\" in "
				+ "(select \"id\" from pigtrax.\"Room\" where \"id_Barn\" in "
				+ "(select \"id\" from pigtrax.\"Barn\" where \"id_Premise\" in "
				+ "(select \"id\" from pigtrax.\"Premise\" where \"id_Company\" = ?)))");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (penId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, penId);
					ps.setObject(2, companyId, java.sql.Types.INTEGER);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("penId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;
	}
}
