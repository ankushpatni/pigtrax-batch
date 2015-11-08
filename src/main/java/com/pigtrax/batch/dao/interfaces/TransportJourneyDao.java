package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import com.pigtrax.batch.beans.TransportJourney;

public interface TransportJourneyDao {
	public Integer getTranportJrnyIdByStartDat(Map <String,Object> criteriaMap);

	int addTransportJourney(TransportJourney transportJourney)
			throws SQLException;

}
