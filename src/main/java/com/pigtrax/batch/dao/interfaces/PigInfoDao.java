package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PigInfo;

public interface PigInfoDao {
	public int insertPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;

	public Integer getPigInfoId(String pigId, int companyId) throws SQLException;

	public Integer getPKfromPigId(final Map<String, String> searchCriteria) throws SQLException;
	
	public boolean isPigASow(Integer pigInfoId);
}
