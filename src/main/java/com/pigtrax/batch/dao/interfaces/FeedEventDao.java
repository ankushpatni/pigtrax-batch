package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.FeedEvent;

public interface FeedEventDao {
	public Integer addFeedEvent(final FeedEvent feedEvent) throws SQLException;
	
	boolean checkIfTicketNumberExists(final String ticketNumber, final Integer premiseId);
	
	Integer getFeedEventPKId(String ticketNumber, Integer premiseId) throws SQLException;
}
