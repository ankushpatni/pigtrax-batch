package com.pigtrax.batch.validator.interfaces;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

public abstract class AbstractValidator implements Validator {

	public Map<Mapper, List<ErrorBean>> runBaseValidation(final List<Mapper> list, final Config.Event event, final ProcessDTO processDTO,
			final Map<Mapper, List<ErrorBean>> errorMap) {
		List<com.pigtrax.batch.config.Config.Event.DataInfo.Col> columnList = event.getDataInfo().getCol();
		if (list != null && list.size() > 0) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				for (com.pigtrax.batch.config.Config.Event.DataInfo.Col col : columnList) {
					try {
						if (!Boolean.parseBoolean(col.getIsEmpty())) {
							if (!isEmpty(col.getKey(), processDTO.getBatchType().getMapperClass(), mapper)) {
								errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_EMPTY_CODE, Constants.ERR_EMPTY_MESSAGE, col.getKey(), false));
								mapper.setRecovrableErrors(false);
							}
						}
					} catch (Exception exception) {
						exception.printStackTrace();
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE, col.getKey(), false));
						mapper.setRecovrableErrors(false);
					}
				}
				if (errList != null && errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}

	private boolean isEmpty(final String proerty, Class<?> clazz, Object object) throws Exception {
		Field field = clazz.getDeclaredField(proerty);
		field.setAccessible(true);
		if (field == null || field.get(object) == null || field.get(object).toString().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}
}
