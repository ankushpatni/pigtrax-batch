package com.pigtrax.batch.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.dao.interfaces.RefDataDao;

@Component
@SuppressWarnings("all")
public class ConfigListner {

	@Autowired
	private XMLMetaDataLoader xmlMetaDataLoader;

	@Autowired
	private RefDataDao refDataDao;

	@PostConstruct
	public void initializeConfig() throws Exception {
		Config config = xmlMetaDataLoader.load();
		ConfigCache.getInstance().loadConfig(config);
		RefDataCache.getInstance().load(refDataDao);
	}
}
