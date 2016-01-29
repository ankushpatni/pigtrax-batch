package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.batch.beans.GroupEventPhaseChange;

public interface GroupEventPhaseChangeDao {
	
   Integer addGroupPhaseChange(GroupEventPhaseChange groupEventPhaseChange) throws SQLException;
   
   void endDateGroupEventPhase(Integer groupEventId) throws SQLException;
   
   Integer getCurrentPhase(Integer groupEventId);

GroupEventPhaseChange getCurrentPhaseObject(Integer groupEventId)
		throws SQLException;

void updatePhaseDetails(GroupEventPhaseChange currentPhase);
}
