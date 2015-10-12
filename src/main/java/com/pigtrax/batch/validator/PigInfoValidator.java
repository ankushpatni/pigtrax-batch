package com.pigtrax.batch.validator;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class PigInfoValidator extends AbstractValidator {

	public Map<String, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		Map<String, List<ErrorBean>> errorMap = super.runBaseValidation(list, eventConfig, processDTO);
		return runCustomValidation(list, errorMap);
	}

	private Map<String, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<String, List<ErrorBean>> errorMap) {
		return errorMap;
	}

}
