package com.pigtrax.batch.config;

import java.util.HashMap;
import java.util.Map;

//Eager loading caching
public class ConfigCache {

	private ConfigCache() {
		if (_INSTANCE != null) {
			throw new IllegalStateException("Inside ConfigCache(): ConfigCache " + "instance already created.");
		}
	}

	private static final ConfigCache _INSTANCE = new ConfigCache();

	private Map<BatchType, Config.Event> configMap = new HashMap<BatchType, Config.Event>();

	public Map<BatchType, Config.Event> getConfig() {
		return configMap;
	}

	public synchronized void loadConfig(final Config config) {
		if (config != null && config.getEvent() != null) {
			for (Config.Event event : config.getEvent()) {
				configMap.put(BatchType.valueOf(event.getId()), event);
			}
		}
	}

	public static ConfigCache getInstance() {
		return _INSTANCE;
	}

}
