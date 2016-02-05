package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.PigletStatusInfo;

public interface PigletStatusInfoDao {
   int insertPigletStatusInfo(PigletStatusInfo pigletStatusInfo); 
   
   void deletePigletStatusEventsByFarrowId(Integer pigInfoId, Integer farrowEventId,  Integer pigletStatusEventType) throws SQLException;
   
   Integer getPKPigletStatus(Integer pigInfoId, Integer farrowEventId,  Integer pigletStatusEventType);
   
   PigletStatusInfo getPigletStatusEventInformation(Integer pigletStatusEventId);
}
