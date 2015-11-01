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
				+ " \"initialFeedQuantityKgs\",\"feedCost\", \"feedMedication\", \"id_TransportJourney\",  \"lastUpdated\",\"userUpdated\") "
				+ "values(?,?,?,?,?,?,?,?,current_timestamp,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				ps.setString(1, feedEvent.getTicketNumber());
				ps.setString(2, feedEvent.getFeedContentId());
				ps.setDate(3, new java.sql.Date(feedEvent.getIntialFeedEntryDate().getTime()));
				ps.setInt(4, feedEvent.getRationId());
				ps.setInt(5, feedEvent.getFeedQuantityKGs());
				ps.setInt(6, feedEvent.getFeedCost());
				ps.setString(7, feedEvent.getFeedMadication());
				if (null != feedEvent.getTransPortJourneyId() && feedEvent.getTransPortJourneyId() > 0)
					ps.setInt(8, feedEvent.getTransPortJourneyId());
				else
					ps.setNull(8, java.sql.Types.INTEGER);
				ps.setString(9, feedEvent.getUserUpdated());
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

}
