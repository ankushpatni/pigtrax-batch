package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.pigtrax.batch.beans.GroupEventDetail;
import com.pigtrax.batch.dao.interfaces.GroupEventDetailsDao;

@Repository
@Transactional
public class GroupEventDetailsDaoImpl implements GroupEventDetailsDao{

private static final Logger logger = Logger.getLogger(GroupEventDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public int addGroupEventDetails(final GroupEventDetail groupEventDetails) throws SQLException {
		final String Qry = "insert into pigtrax.\"GroupEventDetails\"(\"id_GroupEvent\", \"id_Barn\", \"dateOfEntry\", \"id_Room\", \"id_EmployeeGroup\", \"numberOfPigs\","
					+"\"weightInKgs\", \"indeventoryAdjustment\", \"remarks\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setInt(1, groupEventDetails.getGroupId());
	    	            
	    	            if(groupEventDetails.getBarnId() != null && groupEventDetails.getBarnId() != 0)
	    	            	ps.setInt(2, groupEventDetails.getBarnId());
	    	            else
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    	            
	    	            ps.setDate(3, new java.sql.Date(groupEventDetails.getDateOfEntry().getTime()));
	    	            
	    	            if(groupEventDetails.getRoomId() != null && groupEventDetails.getRoomId() != 0)
	    	            	ps.setInt(4, groupEventDetails.getRoomId());
	    	            else
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            
	    	            if(groupEventDetails.getEmployeeGroupId() != null && groupEventDetails.getEmployeeGroupId() != 0)
	    	            	ps.setInt(5, groupEventDetails.getEmployeeGroupId());
	    	            else
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            
	    	            ps.setInt(6, groupEventDetails.getNumberOfPigs());
	    	            ps.setDouble(7, groupEventDetails.getWeightInKgs());
	    	            
	    	            if(groupEventDetails.getInventoryAdjustment() != null && groupEventDetails.getInventoryAdjustment() != 0)
	    	            	ps.setInt(8, groupEventDetails.getInventoryAdjustment());
	    	            else
	    	            	ps.setNull(8, java.sql.Types.INTEGER);
	    	            
	    	            ps.setString(9, groupEventDetails.getRemarks());
	    	            ps.setString(10, groupEventDetails.getUserUpdated());
	    	           
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

}
