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

import com.pigtrax.batch.beans.FeedEvent;
import com.pigtrax.batch.dao.interfaces.FeedEventDao;

@Repository
@Transactional
public class FeedEventDaoImpl implements FeedEventDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(FeedEventDaoImpl.class);

	@Override
	public Integer addFeedEvent(FeedEvent feedEvent) throws SQLException {
		final String Qry = "insert into pigtrax.\"FeedEvent\"(\"ticketNumber\", \"feedContentId\", \"initialFeedEntryDateTime\", \"batchId\","
				+ " \"initialFeedQuantityKgs\",\"feedCost\", \"feedMedication\", \"id_TransportJourney\",  \"lastUpdated\",\"userUpdated\", \"id_Premise\") "
				+ "values(?,?,?,?,?,?,?,?,current_timestamp,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				ps.setString(1, feedEvent.getTicketNumber());
				ps.setString(2, feedEvent.getFeedContentId());
				if(feedEvent.getIntialFeedEntryDate() != null)
					ps.setDate(3, new java.sql.Date(feedEvent.getIntialFeedEntryDate().getTime()));
				else
					ps.setNull(3, java.sql.Types.DATE);
				ps.setInt(4, feedEvent.getRationId());
				ps.setObject(5, feedEvent.getFeedQuantityKGs(), java.sql.Types.DOUBLE);
				ps.setObject(6, feedEvent.getFeedCost(), java.sql.Types.DOUBLE);
				ps.setString(7, feedEvent.getFeedMadication());
				if (null != feedEvent.getTransPortJourneyId() && feedEvent.getTransPortJourneyId() > 0)
					ps.setInt(8, feedEvent.getTransPortJourneyId());
				else
					ps.setNull(8, java.sql.Types.INTEGER);
				ps.setString(9, feedEvent.getUserUpdated());
				ps.setObject(10, feedEvent.getPremiseId(), java.sql.Types.INTEGER);
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}
	
	
	@Override
	public boolean checkIfTicketNumberExists(final String ticketNumber, final Integer premiseId) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select count(*) from pigtrax.\"FeedEvent\" where lower(\"ticketNumber\") = ?");
		final String qry = qryBuffer.toString();
		if (ticketNumber != null) {
			Long id = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if (ticketNumber != null) {
						ps.setString(1, ticketNumber.trim().toLowerCase());
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
		   
			if (id != null && id > 0) {
				return true;
			}
			else
				return false;
		}
		return false;
	}

	
	@Override
	public Integer getFeedEventPKId(final String ticketNumber, final Integer premiseId)
			throws SQLException {
		logger.debug("silo is :" + ticketNumber);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select \"id\" from pigtrax.\"FeedEvent\" where lower(\"ticketNumber\") = ? and \"id_Company\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (ticketNumber != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, ticketNumber.toLowerCase());
					ps.setObject(2, premiseId, java.sql.Types.INTEGER);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("ticketNumber retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;
	}
	
	
	@Override
	public Integer updateFeedEvent(final FeedEvent feedEvent) throws SQLException {
		String query = "update pigtrax.\"FeedEvent\" SET \"ticketNumber\"=?, \"initialFeedEntryDateTime\"=?, \"batchId\"=?,"
				+" \"feedMedication\" = ?, \"id_TransportJourney\"=?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=? ,\"id_Premise\"=? where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, feedEvent.getTicketNumber());		
					ps.setNull(2, java.sql.Types.DATE);		
					ps.setInt(3, feedEvent.getRationId());
					ps.setString(4, feedEvent.getFeedMadication());
					if(feedEvent.getTransPortJourneyId() != null  && feedEvent.getTransPortJourneyId()>0)
						ps.setInt(5, feedEvent.getTransPortJourneyId());
					else
						ps.setNull(5, java.sql.Types.INTEGER);
					ps.setString(6, feedEvent.getUserUpdated());
					if(feedEvent.getPremiseId() != null  && feedEvent.getPremiseId()>0)
						ps.setInt(7, feedEvent.getPremiseId());
					else
						ps.setNull(7, java.sql.Types.INTEGER);
					ps.setInt(8, feedEvent.getId());
				}
			});	
	}
	
}
