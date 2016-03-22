package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.PigTraxEventMaster;

public interface PigTraxEventMasterDao {
	/**
	 * Insert the event master details details
	 * @param PigTraxEventMaster
	 * @return
	 * @throws SQLException
	 */
	int insertEventMaster(PigTraxEventMaster master) throws SQLException;
	
	/**
	 * Delete the piglet status event entries for a given farrow event id
	 * @param farrowEventId
	 * @return
	 * @throws SQLException
	 */	
	int deletePigletStatusEvents(final Integer farrowEventId) throws SQLException;
	
	/**
	 * update the breeding event master details of a given pigInfoId
	 * @param breedingEvent
	 * @return
	 * @throws SQLException
	 */
	int updateBreedingEventMasterDetails(final PigTraxEventMaster master) throws SQLException;
}
