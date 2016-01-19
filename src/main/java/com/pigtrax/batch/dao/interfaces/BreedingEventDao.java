package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pigtrax.batch.beans.BreedingEvent;

public interface BreedingEventDao {
	public Integer getBreedingEventPKId(final Integer pigInfoId, final Date serviceDate) throws SQLException;

	public Integer getBreedingEventId(final Map<String, Object> creteriaMap) throws SQLException;
	
	Integer insertBreedingEventInfo(final BreedingEvent breedingEvent) throws SQLException;
	
	Integer getLatestServiceEventId(Integer pigInfoId);
		
	boolean checkIfPreviousCycleCompleted(Integer pigInfoId);
	
	Date getServiceStartDate(final Integer breedingEventId);	
	 
	int updateServiceStartDate(Date matingDate, Integer breedingEventId);
	
	void resetServiceStartDate(Integer breedingEventId);
	
	List<BreedingEvent> getOpenServiceRecords(Integer pigInfoId);
	
	List<BreedingEvent> getPendingFarrowServiceRecords(final Integer pigInfoId);
}
