package com.pigtrax.batch.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;
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
import com.pigtrax.batch.exception.ErrorBeanCollection;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.loader.interfaces.DataLoader;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.notification.NotificationManager;
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
	
	@Autowired
	NotificationManager notificationManager;

	public void execute(final Map<String, Object> inputMap) {		
		try {
			long startTime = DateUtil.getCurrentDate().getTime();
			ProcessDTO processDTO = getProcessDTO(inputMap);
			processDTO.setStartTime(new Date(startTime));
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
		long startTime = DateUtil.getCurrentDate().getTime();
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
		Map<String, Object> output = getHandler(processDTO.getBatchType()).execute(mapper, errorBeans, processDTO);		
		sendReport(output, processDTO);
	}

	private ProcessDTO getProcessDTO(final Map<String, Object> inputMap) {
		ProcessDTO processDTO = new ProcessDTO();
		try {
			processDTO.setBatchType(BatchType.valueOf(inputMap.get(Constants.EVENT_TYPE).toString()));
			processDTO.setDataSrc(inputMap.get(Constants.DATA).toString());
			processDTO.setHasHeaders(Boolean.parseBoolean(inputMap.get(Constants.HEADER).toString()));
			processDTO.setSeperator(inputMap.get(Constants.SEPERATOR).toString());
			processDTO.setFileType(inputMap.get(Constants.FILE_TYPE).toString());
			processDTO.setUserName(inputMap.get(Constants.USER_NAME).toString());
			processDTO.setCompanyId(Integer.parseInt(inputMap.get(Constants.COMPANY_ID).toString()));
			processDTO.setPremiseId(Integer.parseInt(inputMap.get(Constants.PREMISE_ID).toString()));
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

	// Refactor will do it later..
	@SuppressWarnings("unchecked")
	private void sendReport(final Map<String, Object> output, final ProcessDTO processDTO) {		
		final Map<Mapper, List<ErrorBean>> errorMap = (Map<Mapper, List<ErrorBean>>) output.get("errors");
		final int totalProcessedRecords = (int) output.get("success");
		final int totalRecordsInInput = (int) output.get("size");
		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			
			if(errorMap != null)
			{
				ErrorBeanCollection collection  = new ErrorBeanCollection();
				collection.setCompanyId(processDTO.getCompanyId());
				collection.setUserId(processDTO.getUserName());
				collection.setEventType(processDTO.getBatchType().toString());
				collection.setErrorBeanList(errorMap);
				notificationManager.put(collection);
			}
			
			File reportFile = new File(getFileName(processDTO.getDataSrc()));
			outputStream = new FileOutputStream(reportFile);
			outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			bufferedWriter = new BufferedWriter(outputStreamWriter);

			bufferedWriter.write(":::::::::::::::Start - " + processDTO.getBatchType() + " Batch Report :::::::::::::::::::::::");
			bufferedWriter.newLine();
			bufferedWriter.write("Total Input Records ::" + totalRecordsInInput);
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write("Total Successfully Processed Records ::" + totalProcessedRecords);
			bufferedWriter.newLine();
			bufferedWriter.newLine();

			Iterator<Mapper> itr = errorMap.keySet().iterator();
			List<ErrorBean> lst = null;
			Mapper mapper = null;
			int i = 0;
			while (itr.hasNext()) {
				i++;
				mapper = (itr.next());
				bufferedWriter.write("----Pig Id is: " + mapper.getId());
				bufferedWriter.newLine();
				bufferedWriter.newLine();
				bufferedWriter.write("Non Recoverable Error are : ");
				lst = errorMap.get(mapper);
				int nonRecoverCount = 0;
				for (ErrorBean errBean : lst) {
					if (!errBean.isRecoverable()) {
						bufferedWriter.newLine();
						bufferedWriter.write("Error Code : " + errBean.getCode() + "  Error for Property : " + errBean.getProperty() + "  Error Message is : "
								+ errBean.getMessage());
						bufferedWriter.newLine();
						nonRecoverCount++;
					}
				}
				bufferedWriter.write("Total Non Recoverable Error are : " + nonRecoverCount);
				bufferedWriter.newLine();
				bufferedWriter.newLine();
				bufferedWriter.write("Recoverable Error are:");
				bufferedWriter.newLine();
				lst = errorMap.get(mapper);
				int recoverCount = 0;
				for (ErrorBean errBean : lst) {
					if (errBean.isRecoverable()) {
						bufferedWriter.newLine();
						bufferedWriter.write("Error Code : " + errBean.getCode() + "  Error for Property : " + errBean.getProperty() + "  Error Message is : "
								+ errBean.getMessage());
						bufferedWriter.newLine();
						recoverCount++;
					}
				}
				bufferedWriter.newLine();
				bufferedWriter.write("Total Recoverable Error are:" + recoverCount);
				bufferedWriter.newLine();

			}
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			bufferedWriter.write("Upload Process Started at : "+processDTO.getStartTime());
			bufferedWriter.newLine();
			long endTime = DateUtil.getCurrentDate().getTime();
			bufferedWriter.write("Upload Process completed at : "+new Date(endTime));
			bufferedWriter.newLine();
			long duration = endTime - processDTO.getStartTime().getTime();
			bufferedWriter.write("Total time taken for execution : " + (duration) + " (ms)"+((duration>10000)?(" ["+(duration/1000)+" secs]"):""));
			
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			bufferedWriter.write(":::::::::::::::End - " + processDTO.getBatchType() + " Batch Report :::::::::::::::::::::::");
			bufferedWriter.close();
		} catch (Exception ex) {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
				}
			}
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			ex.printStackTrace();
		}		
	}

	private String getFileName(final String originalFileName) {
		return originalFileName.toLowerCase().replaceAll(".csv", "_report.txt");
	}
}
