package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

public interface RoomDao {
	
	public Integer getRoomPkId(final String roomId) throws SQLException;

	public Integer getRoomPkId(final String roomId, Integer  companyId, Integer premiseId) throws SQLException;
}
