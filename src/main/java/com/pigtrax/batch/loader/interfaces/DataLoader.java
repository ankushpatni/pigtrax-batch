package com.pigtrax.batch.loader.interfaces;

import com.pigtrax.batch.core.ProcessDTO;

public interface DataLoader<T> {

	public T loadData(final ProcessDTO processDTO);

}
