package com.pigtrax.batch.core;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.BatchType;
import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.loader.interfaces.DataLoader;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.DateUtil;
import com.pigtrax.batch.validator.interfaces.Validator;

@Component
public class ProcessEngine implements Process {

	private static final Logger logger = Logger.getLogger(ProcessEngine.class);

	@Autowired
	private DataLoader<List<Mapper>> dataLoader;

	@Autowired
	private ApplicationContext applicationContext;

	public void execute(final Map<String, Object> inputMap) {
		try {
			long startTime = DateUtil.getCurrentDate().getTime();
			ProcessDTO processDTO = getProcessDTO(inputMap);
			logger.info("Start execution for for event:" + processDTO.getBatchType());
			execute(processDTO);
			long endTime = DateUtil.getCurrentDate().getTime();
			logger.info("End execution for for event:" + processDTO.getBatchType());
			logger.info("Total time taken for execution : " + (endTime - startTime) + " (ms)");
		} catch (Exception e) {
			logger.error("excption in ProcessEngine.execute" + e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private void execute(final ProcessDTO processDTO) {
		Config.Event config = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		if (config == null) {
			throw new RuntimeException(processDTO.getBatchType() + " :  event type is not found in the system");
		}
		List<Mapper> mapper = dataLoader.loadData(processDTO);
		if (mapper == null) {
			throw new RuntimeException("Data mapping is failed");
		}
		getDerivable(processDTO.getBatchType()).derive(mapper, processDTO);
		Map<Mapper, List<ErrorBean>> errorBeans = getValidator(processDTO.getBatchType()).validate(mapper, processDTO);
		Handler handler = getHandler(processDTO.getBatchType());
		handler.execute(mapper, errorBeans, processDTO);
	}

	private ProcessDTO getProcessDTO(final Map<String, Object> inputMap) {
		System.out.println(inputMap);
		ProcessDTO processDTO = new ProcessDTO();
		try {
			processDTO.setBatchType(BatchType.valueOf(inputMap.get(Constants.EVENT_TYPE).toString()));
			processDTO.setDataSrc(inputMap.get(Constants.DATA).toString());
			processDTO.setHasHeaders(Boolean.parseBoolean(inputMap.get(Constants.HEADER).toString()));
			processDTO.setSeperator(inputMap.get(Constants.SEPERATOR).toString());
			processDTO.setFileType(inputMap.get(Constants.FILE_TYPE).toString());
			processDTO.setUserName(inputMap.get(Constants.USER_NAME).toString());
		} catch (Exception e) {
			logger.error("excption in ProcessEngine.getProcessDTO" + e);
			e.printStackTrace();
			throw new RuntimeException("input map is invalid, Please check all params.");
		}
		return processDTO;
	}

	private Validator getValidator(final BatchType batchType) {
		return (Validator) applicationContext.getBean(batchType.getValidatorClass());
	}

	private Handler getHandler(final BatchType batchType) {
		return (Handler) applicationContext.getBean(batchType.getHandlerClass());
	}

	private Derivable getDerivable(final BatchType batchType) {
		return (Derivable) applicationContext.getBean(batchType.getDriveClass());
	}
}
