package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.GroupEventPhaseChange;
import com.pigtrax.batch.dao.interfaces.GroupEventPhaseChangeDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.util.DateUtil;

@Repository
@Transactional
public class GroupEventPhaseChangeDaoImpl implements GroupEventPhaseChangeDao {

	private static final Logger logger = Logger.getLogger(FarrowEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	 
	@Autowired
	PremisesDao premisesDao;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	 @Override
	public Integer addGroupPhaseChange(final GroupEventPhaseChange groupEventPhaseChange)
			throws SQLException {
		
		 final String Qry = "insert into pigtrax.\"GroupEventPhaseChange\"(\"id_GroupEvent\", \"id_PhaseOfProductionType\", \"phaseStartDate\", "
					+ "\"phaseEndDate\", \"id_Premise\", \"userUpdated\",\"lastUpdated\" ) "
					+ "values(?,?,current_timestamp,NULL,?,?,current_timestamp )";
			
			KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(
		    	    new PreparedStatementCreator() {
		    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		    	            PreparedStatement ps =
		    	                con.prepareStatement(Qry, new String[] {"id"});
		    	            ps.setObject(1, groupEventPhaseChange.getGroupEventId(), java.sql.Types.INTEGER);
		    	            ps.setObject(2, groupEventPhaseChange.getPhaseOfProductionTypeId(), java.sql.Types.INTEGER);
		    	            ps.setObject(3, groupEventPhaseChange.getPremiseId(), java.sql.Types.INTEGER);
		    	            ps.setString(4, groupEventPhaseChange.getUserUpdated());
		    	            return ps;
		    	        }
		    	    },
		    	    holder);
			int keyVal = holder.getKey().intValue();
			logger.info("Key generated = "+keyVal);
			
			return keyVal;
	}
	 
	 /**
	  * End date a phase change record for a given group event
	  */
	 @Override
	public void endDateGroupEventPhase(final Integer groupEventId)
			throws SQLException {
		 final String Qry = "Update pigtrax.\"GroupEventPhaseChange\" set  \"phaseEndDate\" = current_timestamp where \"id_GroupEvent\" = ?";
			jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry);
	    	            ps.setObject(1, groupEventId, java.sql.Types.INTEGER);
	    	            return ps;
	    	        }	
	    	 });
	 }
	
	 
	 

	 
	 @Override
	public Integer getCurrentPhase(final Integer groupEventId) {
		 String qry = "select \"id_PhaseOfProductionType\" FROM pigtrax.\"GroupEventPhaseChange\" where \"id_GroupEvent\" = ? and \"phaseEndDate\" is NULL";
				
			Long phaseId = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupEventId);
				}}, new ResultSetExtractor<Long>() {
					public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						if (resultSet.next()) {
							return resultSet.getLong(1);
						}
						return null;
					}
				});
				logger.debug("current Phase is :" + phaseId);
				if (phaseId != null) {
					return Integer.decode(phaseId.toString());
				}
				return null;
	}
	 
}
