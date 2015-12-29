package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.FarrowEvent;

public interface FarrowEventDao {
	public Integer getFarrowEventId(Integer pigInfoId, Date farrowEventDate);

	public Integer getFarrowEventId(Integer pigInfoId);

	public int insertFarrowEventformation(final FarrowEvent farrowEvent) throws SQLException, DuplicateKeyException;
	
	boolean checkFarrowEventByBreedingEvent(Integer breedingEventId);
	
	boolean checkIfFarrowExists(final Integer pregnancyEventId);
	
	List<FarrowEvent> getFarrowEvents(Integer pigInfoId);
	
	void updateLitterId(final Integer farrowEventId, final Integer companyId);
}
