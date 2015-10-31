package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public interface BreedingEventDao {
	public Integer getBreedingEventPKId(final Integer pigInfoId, final Date serviceDate) throws SQLException;

	public Integer getBreedingEventId(final Map<String, Object> creteriaMap) throws SQLException;
}
