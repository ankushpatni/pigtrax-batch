package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.batch.beans.SowMovement;


public interface SowMovementDao {

	int addSowMovement(SowMovement sowMovement) throws SQLException;
	
}
