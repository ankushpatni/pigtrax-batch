package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.MatingDetails;
import com.pigtrax.batch.dao.interfaces.MatingDetailsDao;

@Repository
@Transactional
public class MatingDetailsDaoImpl implements MatingDetailsDao {
	
private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
    @Override
    public int insertMatingDetails(MatingDetails matingDetails) {
    	final String qry = "insert into pigtrax.\"MatingDetails\" (\"matingDate\", \"semenId\", \"matingQuality\", "
				+ "\"id_BreedingEvent\", \"id_EmployeeGroup\", \"lastUpdated\", \"userUpdated\", \"semenDate\") "
				+ " values (?,?,?,?,?,current_timestamp, ?,?)";
		
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(qry, new String[] {"id"});
	    	            
	    				ps.setDate(1, new java.sql.Date(matingDetails.getMatingDate().getTime()));
	    				ps.setObject(2, matingDetails.getSemenId());
	    				ps.setObject(3, matingDetails.getMatingQuality(), java.sql.Types.INTEGER);
	    				ps.setObject(4, matingDetails.getBreedingEventId(), java.sql.Types.INTEGER);
	    				ps.setObject(5, matingDetails.getEmployeeGroupId(), java.sql.Types.INTEGER);
	    				ps.setString(6, matingDetails.getUserUpdated());	    
	    				ps.setObject(7,  new java.sql.Date(matingDetails.getSemenDate().getTime()), java.sql.Types.DATE);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return 0;
    }
}
