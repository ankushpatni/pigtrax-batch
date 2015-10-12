package com.pigtrax.batch.handler.interfaces;

import java.util.List;
import java.util.Map;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.interfaces.Mapper;

public interface Handler {

	public void execute(final List<Mapper> list, final Map<String, List<ErrorBean>> map, final ProcessDTO processDTO);

}
