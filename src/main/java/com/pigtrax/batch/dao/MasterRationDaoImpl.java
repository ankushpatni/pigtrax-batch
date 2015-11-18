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

import com.pigtrax.batch.dao.interfaces.MasterRationDao;

@Repository
@Transactional
public class MasterRationDaoImpl implements MasterRationDao {
	
	private static final Logger logger = Logger.getLogger(MasterRationDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public Integer getPKRationId(final String rationValue) {
		String query = "SELECT \"id\" from pigtrax.\"MasterRation\" where  lower(\"rationValue\")=? ";
		  	   
		  Long id = jdbcTemplate.query(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if (rationValue != null) {
						ps.setString(1, rationValue.trim().toLowerCase());
					} else {
						ps.setNull(1, java.sql.Types.VARCHAR);
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
		   
		   
		   if (id != null) {
				return Integer.decode(id.toString());
			}
		   return null;
	}   
}
