package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
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
	public void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId, final Integer pigletStatusEventType)
			throws SQLException {
		//final String qry = "delete from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?";
		
		StringBuffer buffer = new StringBuffer();
	
		if(pigletStatusEventType == 3 || pigletStatusEventType == 4 || pigletStatusEventType == 2)
		{
			buffer.append("delete from pigtrax.\"PigletStatus\" where "
					+ "\"id_FarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"id_PigInfo\" = ?");
			
		}
		else if(pigletStatusEventType == 1)
		{
			buffer.append("delete from pigtrax.\"PigletStatus\" where "
					+ "\"id_fosterFarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"fosterFrom\" = ?");
		}
		
		
		final String qry = buffer.toString();
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, pigletStatusEventType);
				ps.setInt(3, pigInfoId); 
			}
		});
	}
	
	@Override
	public Integer getPKPigletStatus(Integer pigInfoId, Integer farrowEventId,
			Integer pigletStatusEventType) {
		if(pigletStatusEventType == 3 || pigletStatusEventType == 4 || pigletStatusEventType == 2)
		{
			StringBuffer qryBuffer = new StringBuffer();
			qryBuffer.append("select \"id\" from pigtrax.\"PigletStatus\" where "
					+ "\"id_FarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"id_PigInfo\" = ?");
			final String qry = qryBuffer.toString();
			Long retValList1 = null;
			
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, farrowEventId);
					ps.setInt(2, pigletStatusEventType);
					ps.setInt(3, pigInfoId); 
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		
			return null;
		}
		else if(pigletStatusEventType == 1)
		{
			StringBuffer qryBuffer = new StringBuffer();
			qryBuffer.append("select \"id\" from pigtrax.\"PigletStatus\" where "
					+ "\"id_fosterFarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"fosterFrom\" = ?");
			final String qry = qryBuffer.toString();
			Long retValList1 = null;
			
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, farrowEventId);
					ps.setInt(2, pigletStatusEventType);
					ps.setInt(3, pigInfoId); 
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		
			return null;
		}
		return null;
	}
}
