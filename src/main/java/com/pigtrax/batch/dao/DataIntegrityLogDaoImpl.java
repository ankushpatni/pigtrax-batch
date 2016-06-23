package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.DataIntegrityLog;
import com.pigtrax.batch.dao.interfaces.DataIntegrityLogDao;

@Repository
@Transactional
public class DataIntegrityLogDaoImpl implements DataIntegrityLogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = Logger.getLogger(DataIntegrityLogDaoImpl.class);
	
	@Override
	public void insert(DataIntegrityLog log) {
		final String Qry = "insert into pigtrax.\"DataIntegrityLog\"(\"eventType\", \"errorType\", "
				+ "\"eventDate\", \"errorDescription\", \"companyId\", \"userId\") values(?,?,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() { 
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setString(1, log.getEventType());
	    				ps.setString(2, log.getErrorType());
	    				if(log.getEventDate() != null)
	    					ps.setDate(3, new java.sql.Date(log.getEventDate().getTime()));
	    				else
	    					ps.setNull(3, java.sql.Types.DATE);
	    				ps.setString(4, log.getErrorDescription());
	    				ps.setInt(5, log.getCompanyId());
	    				ps.setString(6, log.getUserId());
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
	}
	
	@Override
	public List<DataIntegrityLog> getLog(final Date startDate, final Date endDate) {
		java.sql.Date start = new java.sql.Date(startDate.getTime());
		java.sql.Date end = new java.sql.Date(endDate.getTime());
		
		String qry = "select \"id\", \"eventType\", \"errorType\", \"eventDate\", \"errorDescription\",\"companyId\",\"userId\" from pigtrax.\"DataIntegrityLog\""
				+ " where \"eventDate\" between ? and ?";
		
		List<DataIntegrityLog> logList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setDate(1, start);
				ps.setDate(2, end);
			}}, new EventMapper());
		
		return logList;
	} 
	
	
	@Override
	public List<DataIntegrityLog> getLog(final Date startDate, final Date endDate, Integer companyId) {
		java.sql.Date start = new java.sql.Date(startDate.getTime());
		java.sql.Date end = new java.sql.Date(endDate.getTime());
		
		String qry = "select \"id\", \"eventType\", \"errorType\", \"eventDate\", \"errorDescription\",\"companyId\",\"userId\" from pigtrax.\"DataIntegrityLog\""
				+ " where \"eventDate\" between ? and ? and \"companyId\" = ?";
		
		List<DataIntegrityLog> logList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setDate(1, start);
				ps.setDate(2, end);
				ps.setInt(3, companyId);
			}}, new EventMapper());
		
		return logList;
	} 
	
	private static final class EventMapper implements RowMapper<DataIntegrityLog> {
		public DataIntegrityLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			DataIntegrityLog log = new DataIntegrityLog();	
			log.setEventType(rs.getString("eventType"));
			log.setErrorType(rs.getString("errorType"));
			log.setEventDate(rs.getDate("eventDate"));
			log.setErrorDescription(rs.getString("errorDescription"));	
			log.setCompanyId(rs.getInt("companyId"));
			log.setUserId(rs.getString("userId"));
			return log;
		}
	}
}
