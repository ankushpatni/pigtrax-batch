package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.config.RefData;
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
public class PigInfoHandler implements Handler {

	@Autowired
	private PigInfoDaoImpl pigInfoDaoImpl;

	private static final Logger logger = Logger.getLogger(PigInfoHandler.class);

	public void execute(final List<Mapper> list, final Map<String, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		int count = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PigInfoMapper pigInfoMaper = (PigInfoMapper) mapper;
				if (pigInfoMaper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						PigInfo pigInfo = populatePigIfnfo(errorMap, pigInfoMaper, processDTO);
						if (pigInfo != null) {
							pigInfoDaoImpl.insertPigInformation(pigInfo);
							totalRecordsProcessed = totalRecordsProcessed + 1;
						}
					} catch (Exception e) {
						logger.error("Exception in PigInfoHandler.execute : " + e);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
						isErrorOccured = true;
					}
					if (errList != null && errList.size() > 0 && isErrorOccured) {
						errorMap.put(mapper.getId() == null ? Constants.NO_ID_FOUND + Constants.DB_ERR + count++ : mapper.getId(), errList);
					}
				}
			}

			sendReport(errorMap, totalRecordsProcessed, totalRecordsInInput);
		}
	}

	private PigInfo populatePigIfnfo(final Map<String, List<ErrorBean>> errorMap, final PigInfoMapper pigInfoMaper, final ProcessDTO processDTO) {
		PigInfo pigInfo = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		Integer count = 0;
		Integer sexIdFromRefData = RefData.SEX.getId(pigInfoMaper.getSexTypeId());
		Integer gFunctionIdFromRefData = RefData.GFUNCTION.getId(pigInfoMaper.getGfunctionTypeId());
		if (sexIdFromRefData > -1) {
			if (gFunctionIdFromRefData > -1) {
				try {
					pigInfo = new PigInfo();
					pigInfo.setAlternateTattoo(pigInfoMaper.getAlternateTattoo());
					pigInfo.setBarnId(Integer.valueOf(pigInfoMaper.getBarnId()));
					pigInfo.setBirthDate(DateUtil.getDateFromString(pigInfoMaper.getBirthDate()));
					pigInfo.setCompanyId(Integer.valueOf(pigInfoMaper.getCompanyId()));
					pigInfo.setCurrentFarrowEventDate(Calendar.getInstance().getTime());
					pigInfo.setDamId(pigInfoMaper.getDamId());
					pigInfo.setEntryDate(DateUtil.getDateFromString(pigInfoMaper.getEntryDate()));
					pigInfo.setGcompany(pigInfoMaper.getGcompany());
					pigInfo.setGfunctionTypeId(gFunctionIdFromRefData);
					pigInfo.setGline(pigInfoMaper.getGline());
					pigInfo.setId(Integer.valueOf(pigInfoMaper.getId()));
					pigInfo.setOrigin(pigInfoMaper.getOrigin());
					pigInfo.setParity(Integer.valueOf(pigInfoMaper.getParity()));
					pigInfo.setPenId(Integer.valueOf(pigInfoMaper.getPenId()));
					pigInfo.setRemarks(pigInfoMaper.getRemarks());
					pigInfo.setSexTypeId(sexIdFromRefData);
					pigInfo.setSireId(pigInfoMaper.getSireId());
					pigInfo.setTattoo(pigInfoMaper.getTattoo());
					pigInfo.setUserUpdated(processDTO.getUserName());
				} catch (Exception e) {
					logger.error("Exception in PigInfoHandler.populatePigIfnfo" + e.getMessage());
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
				}
			} else {
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REF_DATA_NOT_FOUND_CODE, Constants.REF_DATA_NOT_FOUND_MSG,
						pigInfoMaper.getGfunctionTypeId(), false));
			}
		} else {
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REF_DATA_NOT_FOUND_CODE, Constants.REF_DATA_NOT_FOUND_MSG, pigInfoMaper.getSexTypeId(), false));
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigInfoMaper.getId() == null ? Constants.NO_ID_FOUND + Constants.REF_DATA_ERR + count++ : pigInfoMaper.getId(), errList);
		}
		return pigInfo;
	}

	// TODO Pending
	private void sendReport(final Map<String, List<ErrorBean>> errorMap, final int totalProcessedRecords, final int totalRecordsInInput) {
	}
}
