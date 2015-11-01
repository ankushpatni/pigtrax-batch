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
		//Map<String, Object> inputMap = new HashMap<String, Object>();
		//inputMap.put(Constants.DATA, "C:\\Users\\agu171\\Desktop\\kshitiz\\piginfo.csv"); 
		
	//	Process process = 
	}

	private static void execute(final ApplicationContext context) {
		Process processEngine = context.getBean(ProcessEngine.class);
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(Constants.EVENT_TYPE, BatchType.PIGINFO);
		inputMap.put(Constants.DATA, "D:\\workspace\\report\\test.csv");
		inputMap.put(Constants.HEADER, "true");
		inputMap.put(Constants.SEPERATOR, ",");
		inputMap.put(Constants.FILE_TYPE, "csv");
		inputMap.put(Constants.USER_NAME, "Aadit Gupta");
		processEngine.execute(inputMap);
	}
	
	
}
