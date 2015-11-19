package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PregnancyInfo;

public interface PregnancyInfoDao {
	public int insertPregnancyInfo(PregnancyInfo pregnancyInfo) throws SQLException, DuplicateKeyException;

	boolean checkIfPregnancyEventExist(final Integer breedingEventId, final Integer eventTypeId, final Integer resultTypeId);

	public Integer getPragnancyId(final Map<String, Object> creteriaMap) throws SQLException;
	
	List<PregnancyInfo> getOpenPregnancyRecords(Integer pigInfoId);
}
