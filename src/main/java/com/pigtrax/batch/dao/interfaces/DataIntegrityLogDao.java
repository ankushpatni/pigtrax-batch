package com.pigtrax.batch.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.pigtrax.batch.beans.DataIntegrityLog;

public interface DataIntegrityLogDao {
	void insert(DataIntegrityLog log);
	
	List<DataIntegrityLog> getLog(Date startDate, Date endDate);

}
