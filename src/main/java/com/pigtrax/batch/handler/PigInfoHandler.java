package com.pigtrax.batch.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PigInfoDaoImpl;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.DateUtil;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class PigInfoHandler implements Handler {

	@Autowired
	private PigInfoDaoImpl pigInfoDaoImpl;

	private static final Logger logger = Logger.getLogger(PigInfoHandler.class);

	public void execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PigInfoMapper pigInfoMaper = (PigInfoMapper) mapper;
				if (pigInfoMaper.isRecovrableErrors() == null || pigInfoMaper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						PigInfo pigInfo = populatePigIfnfo(errorMap, pigInfoMaper, processDTO);
						if (pigInfo != null) {
							pigInfoDaoImpl.insertPigInformation(pigInfo);
							totalRecordsProcessed = totalRecordsProcessed + 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception in PigInfoHandler.execute : " + e);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
						isErrorOccured = true;
					}
					if (errList != null && errList.size() > 0 && isErrorOccured) {
						errorMap.put(mapper, errList);
					}
				}
			}
			sendReport(errorMap, totalRecordsProcessed, totalRecordsInInput, processDTO);
		}
	}

	private PigInfo populatePigIfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigInfoMapper pigInfoMaper, final ProcessDTO processDTO) {
		PigInfo pigInfo = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			pigInfo = new PigInfo();
			pigInfo.setAlternateTattoo(pigInfoMaper.getAlternateTattoo());
			pigInfo.setBarnId(pigInfoMaper.getDeriveBarnId());
			pigInfo.setBirthDate(pigInfoMaper.getDeriveBirthDate());
			pigInfo.setCompanyId(pigInfoMaper.getDeriveCompanyId());
			pigInfo.setCurrentFarrowEventDate(pigInfoMaper.getDeriveFarrowEventDate());
			pigInfo.setDamId(pigInfoMaper.getDamId());
			pigInfo.setEntryDate(pigInfoMaper.getDeriveEntryDate());
			pigInfo.setGcompany(pigInfoMaper.getGcompany());
			pigInfo.setGfunctionTypeId(pigInfoMaper.getDeriveFfunctionTypeId());
			pigInfo.setGline(pigInfoMaper.getGline());
			pigInfo.setPigId(pigInfoMaper.getPigId());
			pigInfo.setOrigin(pigInfoMaper.getOrigin());
			pigInfo.setParity(pigInfoMaper.getDeriveParity());
			pigInfo.setPenId(pigInfoMaper.getDerivePenId());
			pigInfo.setRemarks(pigInfoMaper.getRemarks());
			pigInfo.setSexTypeId((pigInfoMaper.getDeriveSexId()));
			pigInfo.setSireId(pigInfoMaper.getSireId());
			pigInfo.setTattoo(pigInfoMaper.getTattoo());
			pigInfo.setUserUpdated(processDTO.getUserName());
		} catch (Exception e) {
			logger.error("Exception in PigInfoHandler.populatePigIfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigInfoMaper, errList);
		}
		return pigInfo;
	}

	// Need to refactor this method make it more generic/modular and use string
	// buffer.
	private void sendReport(final Map<Mapper, List<ErrorBean>> errorMap, final int totalProcessedRecords, final int totalRecordsInInput,
			final ProcessDTO processDTO) {

		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			String fileName = "report.txt";
			File reportFile = new File(getFileName(processDTO.getDataSrc()));
			outputStream = new FileOutputStream(reportFile);
			outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			bufferedWriter = new BufferedWriter(outputStreamWriter);

			bufferedWriter.write(":::::::::::::::Start - PigTrax Batch Report - Piginfo Entry Event:::::::::::::::::::::::");
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
			while (itr.hasNext()) {
				mapper = (itr.next());
				bufferedWriter.write("Pig Id is: " + mapper.getId());
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
			bufferedWriter.write(":::::::::::::::END - PigTrax Batch Report - Piginfo Entry Event:::::::::::::::::::::::");
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
		return originalFileName.toLowerCase().replaceAll(".csv", "_report.csv");
	}

}
