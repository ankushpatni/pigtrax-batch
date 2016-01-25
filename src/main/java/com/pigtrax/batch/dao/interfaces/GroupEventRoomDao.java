package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.batch.beans.GroupEventPhaseChange;
import com.pigtrax.batch.beans.RoomPK;


public interface GroupEventRoomDao {
     void addGroupEventRooms(GroupEventPhaseChange event);
     
     void deleteGroupEventRooms(Integer groupEventId);
     
     List<RoomPK> getGroupEventRooms(final Integer groupEventId)	throws SQLException;
}
