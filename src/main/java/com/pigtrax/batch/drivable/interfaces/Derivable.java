package com.pigtrax.batch.drivable.interfaces;

import java.util.List;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.mapper.interfaces.Mapper;

public interface Derivable {
	public void derive(final List<Mapper> list, final ProcessDTO processDTO);
}
