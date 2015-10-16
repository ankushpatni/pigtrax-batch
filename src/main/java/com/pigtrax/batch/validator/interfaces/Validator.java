package com.pigtrax.batch.validator.interfaces;

import java.util.List;
import java.util.Map;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.interfaces.Mapper;

public interface Validator {
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO);
}
