package com.pigtrax.batch.dao.interfaces;


public interface PremisesDao {
	
	public Integer getPremisesPK( String premisesId, int generatedCompanyId );
	
	public Integer getSowSourcePK( String premisesId, int generatedCompanyId );
}
