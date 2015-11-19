package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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

import com.pigtrax.batch.beans.BreedingEvent;
import com.pigtrax.batch.beans.MatingDetails;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.FarrowEventDao;

@Repository
@Transactional
public class BreedingEventDaoImpl implements BreedingEventDao {

	@Autowired
	FarrowEventDao farrowEventDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);

	@Override
	public Integer getBreedingEventPKId(Integer pigInfoId, Date serviceDate) throws SQLException {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("Select BE.\"id\" from pigtrax.\"BreedingEvent\" BE  where BE.\"id_PigInfo\" = ? and BE.\"serviceStartDate\"::date = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (pigInfoId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pigInfoId);
					ps.setDate(2, new java.sql.Date(serviceDate.getTime()));
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
		}
		return null;
	}

	@Override
	public Integer getBreedingEventId(Map<String, Object> creteriaMap) throws SQLException {
		Integer penPKId = (Integer) creteriaMap.get("penPKId");
		Date serviceDate = (Date) creteriaMap.get("serviceDate");
		logger.debug("getBreedingEventId ::creteriaMap is penPKId:" + penPKId);
		logger.debug("getBreedingEventId ::creteriaMap is serviceDate:" + serviceDate);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select \"id\" from pigtrax.\"BreedingEvent\" where \"id_PigInfo\" = ? and \"serviceStartDate\" = ? ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (penPKId != null) {
					ps.setInt(1, penPKId);
				} else {
					ps.setNull(1, java.sql.Types.INTEGER);
				}
				if (serviceDate != null) {
					ps.setDate(2, new java.sql.Date(serviceDate.getTime()));
					;
				} else {
					ps.setNull(1, java.sql.Types.DATE);
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
		logger.debug("getBreedingEventId::breedingEventId retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}
		return null;
	}
	
	@Override
	public Integer insertBreedingEventInfo(final BreedingEvent breedingEvent)
			throws SQLException{
		final String Qry = "insert into pigtrax.\"BreedingEvent\"(\"id_PigInfo\", \"id_BreedingServiceType\", "
				+ "\"serviceGroupId\", \"id_Pen\", \"sowCondition\",  \"weightInKgs\",\"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() { 
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setInt(1, breedingEvent.getPigInfoId());
	    				
	    				if(breedingEvent.getBreedingServiceTypeId() != null){
	    					ps.setInt(2, breedingEvent.getBreedingServiceTypeId());
	    				}
	    				else{
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    				}
	    				ps.setString(3, breedingEvent.getServiceGroupId());
	    				if(breedingEvent.getPenId() != null && breedingEvent.getPenId() != 0){
	    					ps.setInt(4, breedingEvent.getPenId());
	    				}
	    				else{
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    				}
	    				
	    				if(breedingEvent.getSowCondition() != null ){
	    					ps.setInt(5, breedingEvent.getSowCondition());
	    				}
	    				else{
	    					ps.setNull(5, java.sql.Types.INTEGER);
	    				}
	    				ps.setObject(6, breedingEvent.getWeight(), java.sql.Types.DOUBLE); 
	    				
	    				ps.setString(7, breedingEvent.getUserUpdated());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	
	@Override
	public boolean checkIfPreviousCycleCompleted(Integer pigInfoId) {
		
		Integer lastServiceId = getLatestServiceEventId(pigInfoId);  
		if(lastServiceId == null)
			return true;
		else
		{			
			boolean check = farrowEventDao.checkFarrowEventByBreedingEvent(lastServiceId);	 
			if(check)
				return true;
			else
				return false;
		}
	}
	
	
	public Integer getLatestServiceEventId(Integer pigInfoId) {
		String qry = "Select BE.\"id\" from pigtrax.\"BreedingEvent\" BE  where BE.\"id_PigInfo\" = ? order by BE.\"id\" desc";
		
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter(){
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, pigInfoId);
		}}, new ResultSetExtractor<Long>() {
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
	
	@Override
	public Date getServiceStartDate(final Integer breedingEventId){
		String qry = "Select  BE.\"serviceStartDate\" from pigtrax.\"BreedingEvent\" BE  where BE.\"id\" = ? ";
		Date serviceStartDate = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
			}}, new ResultSetExtractor<Date>() {
				public Date extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getDate(1);
					}
					return null;
				}
			});	

		return serviceStartDate;
	}

	@Override 
	public int updateServiceStartDate(final Date matingDate, final Integer breedingEventId) {
		String qry = "update pigtrax.\"BreedingEvent\" set \"serviceStartDate\" = ? where \"id\" = ?";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if(matingDate != null)
					ps.setObject(1, new java.sql.Date(matingDate.getTime()));
				else
					ps.setObject(1, null);
				ps.setInt(2, breedingEventId);
			}
		});
	}
	
	@Override
	public void resetServiceStartDate(Integer breedingEventId) {
		List<MatingDetails> matingDetailsList = getMatingDetails(breedingEventId);
		DateTime serviceDate = null;
		if(matingDetailsList != null)
		{
			for(MatingDetails matingDetails : matingDetailsList)
			{
				DateTime matingDate = new DateTime(matingDetails.getMatingDate());
				if(!(serviceDate != null && serviceDate.isBefore(matingDate)))
					serviceDate = matingDate;
				
			}
		}
		
		if(serviceDate != null)
			updateServiceStartDate(serviceDate.toDate(), breedingEventId);
		
	}
	
	
	private List<MatingDetails> getMatingDetails(final Integer breedingEventId) {
		String qry = "select \"id\", \"id_BreedingEvent\", \"semenId\", \"matingDate\", \"matingQuality\", "
				+ "\"id_EmployeeGroup\", \"lastUpdated\", \"userUpdated\" from pigtrax.\"MatingDetails\" where \"id_BreedingEvent\" = ?";
		
		List<MatingDetails> matingDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
			}}, new MatingEventWrapper());
		
		return matingDetailsList;
	}
	
	private static final class MatingEventWrapper implements RowMapper<MatingDetails> {
		public MatingDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			MatingDetails matingDetails = new MatingDetails();			
			matingDetails.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			matingDetails.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			matingDetails.setMatingDate(rs.getDate("matingDate"));
			matingDetails.setSemenId(rs.getString("semenId"));
			matingDetails.setMatingQuality(rs.getInt("matingQuality"));
			matingDetails.setLastUpdated(rs.getDate("lastUpdated"));
			matingDetails.setUserUpdated(rs.getString("userUpdated"));
			return matingDetails;
		}
	}
	
	@Override
	public List<BreedingEvent> getOpenServiceRecords(Integer pigInfoId) {
		String qry = "select BE.* from pigtrax.\"BreedingEvent\" BE where BE.\"serviceStartDate\" "
				+ "	is not NULL and BE.\"id\" not in (select PE.\"id_BreedingEvent\" from pigtrax.\"PregnancyEvent\" PE "
				+ " JOIN pigtrax.\"BreedingEvent\" BE on PE.\"id_BreedingEvent\" = BE.\"id\" JOIN pigtrax.\"PigInfo\" PI "
				+ " on BE.\"id_PigInfo\" = PI.\"id\" where PI.\"id\" = ?) and  BE.\"id_PigInfo\" = ? order by BE.\"id\" desc";
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
				ps.setInt(2, pigInfoId);
			}}, new BreedingEventMapper());
		
		return breedingEventList;
	}
	
	private static final class BreedingEventMapper implements RowMapper<BreedingEvent> {
		public BreedingEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			BreedingEvent breedingEvent = new BreedingEvent();			
			breedingEvent.setId(rs.getInt("id"));
			breedingEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			breedingEvent.setBreedingServiceTypeId(rs.getInt("id_BreedingServiceType"));
			breedingEvent.setServiceGroupId(rs.getString("serviceGroupId"));
			breedingEvent.setServiceStartDate(rs.getDate("serviceStartDate"));
			breedingEvent.setSowCondition(rs.getInt("sowCondition"));
			breedingEvent.setPenId(rs.getInt("id_Pen"));
			breedingEvent.setWeight(rs.getDouble("weightInKgs"));			
			return breedingEvent;
		}
	}
}
