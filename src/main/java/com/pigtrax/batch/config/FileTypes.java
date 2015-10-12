package com.pigtrax.batch.config;

import java.util.List;

import com.pigtrax.batch.loader.CSVLoader;
import com.pigtrax.batch.loader.interfaces.DataLoader;
import com.pigtrax.batch.mapper.interfaces.Mapper;

public enum FileTypes {
	CSV {
		@Override
		public DataLoader<List<Mapper>> getLoader() {
			return new CSVLoader();
		}
	};

	public abstract DataLoader<List<Mapper>> getLoader();
}
