package com.pigtrax.batch.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
public class XMLMetaDataLoader implements MetaDataLoader {

	private String metaDataFileClassPath = "metaDataConfig.xml";

	private static final Logger logger = Logger.getLogger(XMLMetaDataLoader.class);

	public Config load() throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Config config = (Config) jaxbUnmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream(metaDataFileClassPath));
			return config;
		} catch (Exception e) {
			logger.error("excption in XMLMetaDataLoader.load" + e);
			e.printStackTrace();
			throw new Exception("Exception while loading metadata" + e);
		}
	}
}
