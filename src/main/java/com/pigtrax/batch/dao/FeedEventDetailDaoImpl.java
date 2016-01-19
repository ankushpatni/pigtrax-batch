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

import com.pigtrax.batch.beans.FeedEventDetail;
import com.pigtrax.batch.dao.interfaces.FeedEventDetailDao;

@Repository
public class FeedEventDetailDaoImpl implements FeedEventDetailDao {

private static final Logger logger = Logger.getLogger(FeedEventDetailDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Integer addFeedEventDetail(final FeedEventDetail feedEventDetail)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"FeedEventDetails\"(\"feedEventDate\", \"weightInKgs\", \"remarks\", \"id_FeedEvent\", "
		   		+ "\"id_Silo\", \"id_GroupEvent\", \"id_FeedEventType\",\"lastUpdated\", \"userUpdated\",\"feedMill\", \"feedCost\") "
				+ "values(?,?,?,?,?,?,?,current_timestamp,?,?,?)";	
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				if( null != feedEventDetail.getFeedEventDate())
				{
				ps.setDate(1, new java.sql.Date(feedEventDetail.getFeedEventDate().getTime()));
				}
				else
				{
					ps.setNull(1, java.sql.Types.DATE);
				}
				
				if( null != feedEventDetail.getWeightInKgs())
				{
					ps.setDouble(2, feedEventDetail.getWeightInKgs());
				}
				else
				{
					ps.setNull(2, java.sql.Types.DOUBLE);
				}
				
				ps.setString(3, feedEventDetail.getRemarks() );
				ps.setInt(4, feedEventDetail.getFeedEventId());
				ps.setInt(5, feedEventDetail.getSiloId());
				if( null != feedEventDetail.getGroupEventId() && feedEventDetail.getGroupEventId()>0)
				{
					ps.setInt(6, feedEventDetail.getGroupEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}
				ps.setInt(7, feedEventDetail.getFeedEventTypeId());
				ps.setString(8, feedEventDetail.getUserUpdated());
				ps.setString(9,  feedEventDetail.getFeedMill());
				ps.setObject(10, feedEventDetail.getFeedCost(), java.sql.Types.DOUBLE);
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	

}
