package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigletStatusInfo;
import com.pigtrax.batch.config.PigletStatusEventType;
import com.pigtrax.batch.dao.interfaces.PigletStatusInfoDao;

@Repository
@Transactional
public class PigletStatusInfoDaoImpl implements PigletStatusInfoDao {
	
	private static final Logger logger = Logger.getLogger(PigletStatusInfoDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
		this.jdbcTemplate = jdbcTemplate;
	} 
	
	
    @Override
	public int insertPigletStatusInfo(PigletStatusInfo pigletStatusInfo) { 
    	
    	final String Qry = "insert into pigtrax.\"PigletStatus\"(\"id_PigInfo\", \"id_PigletStatusEventType\", "
				+ "\"eventDateTime\", \"numberOfPigs\", \"weightInKgs\", \"eventReason\", \"remarks\", \"sowCondition\", "
				+ "\"weanGroupId\", \"lastUpdated\", \"userUpdated\", \"fosterFrom\", \"fosterTo\", "
				+ "\"id_FarrowEvent\", \"id_fosterFarrowEvent\", \"id_GroupEvent\", \"id_MortalityReasonType\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?, ?,?,?)";
		 
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setObject(1, pigletStatusInfo.getPigInfoId(), java.sql.Types.INTEGER);
	    	            ps.setObject(2, pigletStatusInfo.getPigletStatusEventType(), java.sql.Types.INTEGER);
	    	            ps.setObject(3, new java.sql.Date(pigletStatusInfo.getEventDateTime().getTime()), java.sql.Types.DATE);
	    	            ps.setInt(4,  pigletStatusInfo.getNumberOfPigs());
	    	            ps.setObject(5,  pigletStatusInfo.getWeightInKgs(),java.sql.Types.DOUBLE);
	    	            ps.setString(6,  pigletStatusInfo.getEventReason());
	    	            ps.setString(7,  pigletStatusInfo.getRemarks());
	    	            ps.setObject(8,  pigletStatusInfo.getSowCondition(), java.sql.Types.INTEGER);
	    	            ps.setString(9, pigletStatusInfo.getWeanGroupId());
	    	            ps.setString(10, pigletStatusInfo.getUserUpdated());
	    	            ps.setObject(11, pigletStatusInfo.getFosterFrom(), java.sql.Types.INTEGER);
	    	            ps.setObject(12, pigletStatusInfo.getFosterTo(), java.sql.Types.INTEGER);
	    	            ps.setObject(13,  pigletStatusInfo.getFarrowEventId(), java.sql.Types.INTEGER);
	    	            ps.setObject(14,  pigletStatusInfo.getFosterFarrowEventId(), java.sql.Types.INTEGER);
	    	            ps.setObject(15,  pigletStatusInfo.getGroupEventId(), java.sql.Types.INTEGER);
	    	            ps.setObject(16,  pigletStatusInfo.getMortalityReasonTypeId(), java.sql.Types.INTEGER);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
    	
	}
    
    /***
	 * Delete the piglet status events for a given farrow event Id
	 */
	@Override
	public void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId)
			throws SQLException {
		//final String qry = "delete from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?";
		
		final String qry = "delete from pigtrax.\"PigletStatus\" where "
				+ "(\"id_FarrowEvent\" = ? and \"id_PigletStatusEventType\" <> ?) or "
				+ "(\"id_fosterFarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"id_PigInfo\" <> ?)";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
				ps.setInt(3, farrowEventId); 
				ps.setInt(4, PigletStatusEventType.FosterIn.getTypeCode());
				ps.setInt(5, pigInfoId); 
			}
		});
	}
}
