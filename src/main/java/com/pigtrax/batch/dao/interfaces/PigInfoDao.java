package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PigInfo;

public interface PigInfoDao {
	public int insertPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;

	public Integer getPigInfoId(String pigId, int companyId) throws SQLException;
	
	public Integer getPigInfoId(String pigId, int companyId, int premiseId) throws SQLException;

	public Integer getPKfromPigId(final Map<String, String> searchCriteria) throws SQLException;
	
	public boolean isPigASow(Integer pigInfoId);
	
	public PigInfo getPigDetails(Integer pigInfoId);
	
	int updatePigInfoStatus(Integer id, Boolean pigStatus);
	
	Integer getActivePigInfoId(String pigId, int companyId) throws SQLException;
	
	Integer getPigInfoIdForTattoo(String tattoo, int companyId, int premiseId) throws SQLException;

	int updatePigInformation(PigInfo pigInfo) throws SQLException,
			DuplicateKeyException;
	
	int increaseParity(Integer pigInfoId, Integer gestationLength);
}
