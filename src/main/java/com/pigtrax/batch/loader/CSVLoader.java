package com.pigtrax.batch.loader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.loader.interfaces.DataLoader;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;

@SuppressWarnings("all")
public class CSVLoader implements DataLoader<List<Mapper>> {

	private static final Logger logger = Logger.getLogger(CSVLoader.class);

	public List<Mapper> loadData(final ProcessDTO processDTO) {
		try {
			CsvToBean csv = new CsvToBean();
			char seperator = processDTO.getSeperator() != null && !processDTO.getSeperator().trim().equalsIgnoreCase("") ? processDTO.getSeperator()
					.toCharArray()[0] : Constants.DEFAULT_SEPRATOR;
			CSVReader reader = new CSVReader(new FileReader(processDTO.getDataSrc()), seperator);
			ColumnPositionMappingStrategy colMapping = getColumns(processDTO, reader);
			List<Mapper> list = csv.parse(colMapping, reader);
			if (processDTO.hasHeaders()) {
				list.remove(0);
			}
			return list;
		} catch (Exception e) {
			logger.error("Exception in CSVLoader" + e);
			e.printStackTrace();
		}
		return null;
	}

	private ColumnPositionMappingStrategy getColumns(final ProcessDTO processDTO, final CSVReader reader) throws IOException {
		ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
		strategy.setType(processDTO.getBatchType().getMapperClass());
		String[] columns = null;
		boolean isHeader = false;
		if (isHeader) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				columns = nextLine;
				break;
			}
		} else {
			Config.Event event = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
			List<Config.Event.DataInfo.Col> cols = event.getDataInfo().getCol();
			Collections.sort(cols, new Comparator<Config.Event.DataInfo.Col>() {
				public int compare(Config.Event.DataInfo.Col s1, Config.Event.DataInfo.Col s2) {
					return s1.getOrder() > s2.getOrder() ? 1 : -1;
				}
			});
			List<String> list = new ArrayList<String>();
			for (Config.Event.DataInfo.Col col : cols) {
				list.add(col.getKey());
			}
			columns = list.stream().toArray(String[]::new);
		}
		strategy.setColumnMapping(columns);
		return strategy;
	}

}