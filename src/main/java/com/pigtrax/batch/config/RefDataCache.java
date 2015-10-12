package com.pigtrax.batch.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pigtrax.batch.beans.RefDataTranslation;
import com.pigtrax.batch.dao.interfaces.RefDataDao;
import com.pigtrax.batch.util.Constants;

public class RefDataCache {

	private static final RefDataCache _INSTANCE = new RefDataCache();

	private Map<String, Map<Integer, String>> sexTypeMap = null;

	private Map<String, Map<Integer, String>> gfunctionTypeMap = null;

	private RefDataCache() {
		if (_INSTANCE != null) {
			throw new IllegalStateException("Inside RefDataCache(): RefDataCache " + "instance already created.");
		}
	}

	public synchronized void load(final RefDataDao refDataDao) {
		sexTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSexData()));
		gfunctionTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getGfunctionType()));
	}

	private Map<String, Map<Integer, String>> convertToMap(final List<RefDataTranslation> rolesList) {
		Map<String, Map<Integer, String>> tmpMap = new HashMap<String, Map<Integer, String>>();
		for (RefDataTranslation refDto : rolesList) {
			Map<Integer, String> innerMap = tmpMap.get(refDto.getFieldLanguage());
			if (innerMap == null) {
				innerMap = new HashMap<Integer, String>();
				tmpMap.put(refDto.getFieldLanguage(), innerMap);
			}
			innerMap.put(refDto.getFieldCode(), refDto.getFieldValue());
		}
		return tmpMap;
	}

	public Map<String, Map<Integer, String>> getSexTypeMap() {
		return sexTypeMap;
	}

	public Map<String, Map<Integer, String>> getGfunctionTypeMap() {
		return gfunctionTypeMap;
	}

	public static RefDataCache getInstance() {
		return _INSTANCE;
	}

	public Integer getRefDataId(final String value, final String refDataName) {
		Integer retVal = -1;
		switch (refDataName) {
		case Constants.REF_DATA_SEX:
			retVal = retRefdDataValue(sexTypeMap, value);
			break;
		case Constants.REF_DATA_GFUNCTION:
			retVal = retRefdDataValue(gfunctionTypeMap, value);
			break;
		default:
			retVal = -1;
		}
		return retVal;
	}

	private Integer retRefdDataValue(final Map<String, Map<Integer, String>> refDataMap, final String value) {
		Integer retVal = -1;
		if (refDataMap != null && !refDataMap.isEmpty() && value != null) {
			Iterator<String> langItr = refDataMap.keySet().iterator();
			Map<Integer, String> innerMap = null;
			Integer innerKey = -1;
			while (langItr.hasNext()) {
				innerMap = refDataMap.get(langItr.next());
				Iterator<Integer> innerItr = innerMap.keySet().iterator();
				while (innerItr.hasNext()) {
					innerKey = (Integer) innerItr.next();
					if (value.equalsIgnoreCase(innerMap.get(innerKey))) {
						return innerKey;
					}
				}
			}
		}
		return retVal;
	}
}
