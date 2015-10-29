package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PigInfo;

public interface GroupEventDao {
	
	public Integer getGroupEventId(String groupId, int companyId) throws  SQLException;
}
