package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.batch.beans.PigInfo;

public interface PigInfoDao {
	public int insertPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;
}
