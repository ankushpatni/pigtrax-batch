package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.FeedEventDetail;

public interface FeedEventDetailDao {
	
	Integer addFeedEventDetail(final FeedEventDetail feedEventDetail) throws SQLException;
}
