package com.pigtrax.batch.loader;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.FileTypes;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.loader.interfaces.DataLoader;
import com.pigtrax.batch.mapper.interfaces.Mapper;

@Component
@SuppressWarnings({ "all" })
public class GenericDataLoader implements DataLoader<List<Mapper>> {

	public List<Mapper> loadData(final ProcessDTO processDTO) {
		for (FileTypes fileType : FileTypes.values()) {
			if (processDTO.getFileType().equalsIgnoreCase(fileType.toString())) {
				return fileType.getLoader().loadData(processDTO);
			}
		}
		return null;
	}
}
