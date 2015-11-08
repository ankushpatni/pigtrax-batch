package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

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

import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;

@Repository
@Transactional
public class TransportJourneyDaoImpl implements TransportJourneyDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(TransportJourneyDaoImpl.class);

	@Override
	public Integer getTranportJrnyIdByStartDat(Map<String, Object> criteriaMap) {

		Date jrnyStartDate = (Date) criteriaMap.get("jrnyStartDate");
		logger.debug("jrnyStartDate is :" + jrnyStartDate);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select \"id\" from pigtrax.\"TransportJourney\" where \"journeyStartTime\" = ? ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (jrnyStartDate != null) {
					ps.setDate(1, new java.sql.Date(jrnyStartDate.getTime()));
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
		logger.debug("jrnyStartDate retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}

		return null;

	}
	
	@Override
	public int addTransportJourney(final TransportJourney transportJourney)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"TransportJourney\"(\"trailerFunction\", \"journeyStartTime\", \"journeyEndTime\","
			+" \"id_TransportDestination\", \"id_TransportTruck\", \"id_TransportTrailer\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,current_timestamp,?)";
			
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});	    	           
	    	            ps.setString(1, transportJourney.getTrailerFunction());
	    	            if(transportJourney.getJourneyStartTime() != null )
	    	            	ps.setDate(2, new java.sql.Date(transportJourney.getJourneyStartTime().getTime()));
	    	            else
	    	            	ps.setNull(2, java.sql.Types.DATE);
	    	            if(transportJourney.getJourneyEndTime() != null )
	    	            	ps.setDate(3, new java.sql.Date(transportJourney.getJourneyEndTime().getTime()));
	    	            else
	    	            	ps.setNull(3, java.sql.Types.DATE);
	    	            if(transportJourney.getTransportDestinationId() != null )
	    	            	ps.setInt(4, transportJourney.getTransportDestinationId());
	    	            else 
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            
	    	            if(transportJourney.getTransportTruckId() != null )
	    	            	ps.setInt(5, transportJourney.getTransportTruckId());
	    	            else 
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            if(transportJourney.getTransportTrailerId() != null )
	    	            	ps.setInt(6, transportJourney.getTransportTrailerId());
	    	            else 
	    	            	ps.setNull(6, java.sql.Types.INTEGER);
	    	          
	    	            ps.setString(7, transportJourney.getUserUpdated());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

}
