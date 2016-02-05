package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.GroupEventDetail;

public interface GroupEventDetailsDao {
	
	int addGroupEventDetails(final GroupEventDetail groupEventDetails) throws SQLException;
	
	void deleteGroupEventDetailsByPigletEvent(final Integer pigletStatusEventId) throws SQLException;
	
}
