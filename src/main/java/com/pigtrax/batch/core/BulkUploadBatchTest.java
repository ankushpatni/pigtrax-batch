package com.pigtrax.batch.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pigtrax.batch.config.BatchType;
import com.pigtrax.batch.util.Constants;

public class BulkUploadBatchTest {

	public static void main(String[] args) {
		execute(new ClassPathXmlApplicationContext("springConfig.xml"));
	}

	private static void execute(final ApplicationContext context) {
		Process processEngine = context.getBean(ProcessEngine.class);
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(Constants.EVENT_TYPE, BatchType.REMOVALEVENTEXCEPTSALESEVENT);
		inputMap.put(Constants.DATA, "C:\\ankush\\Elance\\Khitij\\xls\\RemovalExcept.csv");
		inputMap.put(Constants.HEADER, "true");
		inputMap.put(Constants.SEPERATOR, ",");
		inputMap.put(Constants.FILE_TYPE, "csv");
		inputMap.put(Constants.USER_NAME, "pigtraxsuperadmin");
		processEngine.execute(inputMap);
	}
}
