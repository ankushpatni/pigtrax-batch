package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.batch.beans.GroupEvent;

public interface GroupEventDao {
	
	public Integer getGroupEventId(String groupId, int companyId) throws  SQLException;

	int addGroupEvent(GroupEvent groupEvent) throws SQLException;

	GroupEvent getGroupEventByGeneratedGroupId(int groupId, int companyId);
	
	int updateGroupEvent(final GroupEvent groupEvent) throws SQLException;
}
