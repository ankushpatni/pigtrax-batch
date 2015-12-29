package com.pigtrax.batch.dao.interfaces;

import com.pigtrax.batch.beans.IndividualPigletStatus;

public interface IndividualPigletDao {
	
    boolean checkIfExists(String tattooId, Integer premiseId);
    
    Integer insertIndividualPigletStatus(IndividualPigletStatus individualPigletStatus) throws Exception;
    
    boolean canAddPigletStatus(Integer farrowEventId);
}
