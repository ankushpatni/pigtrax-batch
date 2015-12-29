package com.pigtrax.batch.dao.interfaces;

import java.sql.SQLException;


public interface SiloDao {	
	
	Integer getSiloPKId(String silo, Integer premiseId) throws SQLException;

}
