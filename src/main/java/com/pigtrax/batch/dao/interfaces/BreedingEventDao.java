package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;

public interface BreedingEventDao {
	Integer getBreedingEventPKId(final Integer pigInfoId, final Date serviceDate) throws SQLException;
}
