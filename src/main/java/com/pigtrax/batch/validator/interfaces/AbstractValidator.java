package com.pigtrax.batch.validator.interfaces;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

public abstract class AbstractValidator implements Validator {

	public Map<String, List<ErrorBean>> runBaseValidation(final List<Mapper> list, final Config.Event event, final ProcessDTO processDTO) {
		List<com.pigtrax.batch.config.Config.Event.DataInfo.Col> columnList = event.getDataInfo().getCol();
		Map<String, List<ErrorBean>> errorMap = new HashMap<String, List<ErrorBean>>();
		int count = 0;
		if (list != null && list.size() > 0) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				for (com.pigtrax.batch.config.Config.Event.DataInfo.Col col : columnList) {
					try {
						if (col.getIsEmpty() != null && Boolean.parseBoolean(col.getIsEmpty())) {
							if (!isEmpty(col.getKey(), processDTO.getBatchType().getMapperClass(), mapper)) {
								errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_EMPTY_CODE, Constants.ERR_EMPTY_MESSAGE, col.getKey(), false));
								mapper.setRecovrableErrors(false);
							}
						}
					} catch (Exception exception) {
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE, col.getKey(), false));
					}
				}
				if (errList != null && errList.size() > 0) {
					errorMap.put(mapper.getId() == null ? Constants.NO_ID_FOUND + count++ : mapper.getId(), errList);
				}
			}
		}
		return errorMap;
	}

	private boolean isEmpty(final String proerty, Class<?> clazz, Object object) throws Exception {
		Field field = clazz.getDeclaredField(proerty);
		field.setAccessible(true);
		if (field == null || field.get(object) == null || !field.get(object).toString().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}
}
