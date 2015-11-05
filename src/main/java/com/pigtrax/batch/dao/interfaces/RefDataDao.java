package com.pigtrax.batch.dao.interfaces;

import java.util.List;

import com.pigtrax.batch.beans.RefDataTranslation;

public interface RefDataDao {

	List<RefDataTranslation> getSexData();

	List<RefDataTranslation> getGfunctionType();
	
	List<RefDataTranslation> getPregnancyEventType();
	
	List<RefDataTranslation> getPregnancyExamResultType();
	
	List<RefDataTranslation> getMortalityReasonType();
	
	List<RefDataTranslation> getPigletStatusEventType();

	List<RefDataTranslation> getPhaseOfProductionType();

	List<RefDataTranslation> getRemovalEventType();

}
