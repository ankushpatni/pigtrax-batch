package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PregnancyInfo;

public interface PregnancyInfoDao {
	public int insertPregnancyInfo(PregnancyInfo pregnancyInfo) throws SQLException, DuplicateKeyException;
	boolean checkIfPregnancyEventExist(final Integer breedingEventId, final Integer eventTypeId, final Integer resultTypeId);
}
