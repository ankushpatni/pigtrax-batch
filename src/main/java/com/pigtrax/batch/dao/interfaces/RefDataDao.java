package com.pigtrax.batch.dao.interfaces;

import java.util.List;

import com.pigtrax.batch.beans.RefDataTranslation;

public interface RefDataDao {

	List<RefDataTranslation> getSexData();

	List<RefDataTranslation> getGfunctionType();

}
