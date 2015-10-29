package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.PigletStatusInfo;
import com.pigtrax.batch.config.PigletStatusEventType;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.PigletStatusInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class PigletStatusInfoHandler implements Handler {

	@Autowired
	private PigletStatusInfoDao pigletStatusInfoDao;
	
	@Autowired
	private PigTraxEventMasterDao eventMasterDao;

	private static final Logger logger = Logger.getLogger(PigletStatusInfoHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PigletStatusInfoMapper pigletStatusInfoMapper = (PigletStatusInfoMapper) mapper;
				if (pigletStatusInfoMapper.isRecovrableErrors() == null || pigletStatusInfoMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						
						pigletStatusInfoDao.deletePigletStatusEventsByFarrowId(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId());
						eventMasterDao.deletePigletStatusEvents(pigletStatusInfoMapper.getDeriveFarrowEventId());
						
						PigletStatusInfo pigletStatusInfo = populatePigletStatusWeanInfo(errorMap, pigletStatusInfoMapper, processDTO);
						if (pigletStatusInfo != null) {
							int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
							
							PigTraxEventMaster master = new PigTraxEventMaster();
							master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
							master.setUserUpdated(processDTO.getUserName());
							master.setEventTime(pigletStatusInfoMapper.getDeriveWeanDate());
							master.setPigletStatusId(generatedKey);
							master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
							
							eventMasterDao.insertEventMaster(master);
						
						}
						pigletStatusInfo = populateFosterOutInfo(errorMap, pigletStatusInfoMapper, processDTO);
						if (pigletStatusInfo != null) {
							int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
							
							PigTraxEventMaster master = new PigTraxEventMaster();
							master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
							master.setUserUpdated(processDTO.getUserName());
							master.setEventTime(pigletStatusInfoMapper.getDeriveTransferDate());
							master.setPigletStatusId(generatedKey);
							master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
							
							eventMasterDao.insertEventMaster(master);
						}
						
						pigletStatusInfo = populateFosterInInfo(errorMap, pigletStatusInfoMapper, processDTO);
						if (pigletStatusInfo != null) {
							int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
							
							PigTraxEventMaster master = new PigTraxEventMaster();
							master.setPigInfoId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
							master.setUserUpdated(processDTO.getUserName());
							master.setEventTime(pigletStatusInfoMapper.getDeriveTransferDate());
							master.setPigletStatusId(generatedKey);
							master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
							
							eventMasterDao.insertEventMaster(master);
						}
						
						pigletStatusInfo = populatePreMortalityInfo(errorMap, pigletStatusInfoMapper, processDTO);
						if (pigletStatusInfo != null) {
							int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
							
							PigTraxEventMaster master = new PigTraxEventMaster();
							master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
							master.setUserUpdated(processDTO.getUserName());
							master.setEventTime(pigletStatusInfoMapper.getDeriveMortalityEventDate());
							master.setPigletStatusId(generatedKey);
							master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
							
							eventMasterDao.insertEventMaster(master);
						
						}
						
						totalRecordsProcessed = totalRecordsProcessed + 1;
						
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
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private PigletStatusInfo populatePigletStatusWeanInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigletStatusInfoMapper pigletStatusInfoMapper, final ProcessDTO processDTO) {
		PigletStatusInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			if(pigletStatusInfoMapper.getDeriveWeanPigNum() != null && pigletStatusInfoMapper.getDeriveWeanPigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveWeanDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDeriveWeanPigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDeriveWeanPigWt());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());
				info.setGroupEventId(pigletStatusInfoMapper.getDeriveGroupEventId());			
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.Wean.getTypeCode());
			}
		} catch (Exception e) {
			logger.error("Exception in PigletStatusInfoHandler.populatePigletStatusWeanInfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigletStatusInfoMapper, errList);
		}
		return info;
	}
	
	
	private PigletStatusInfo populateFosterInInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigletStatusInfoMapper pigletStatusInfoMapper, final ProcessDTO processDTO) {
		PigletStatusInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			if(pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				info.setFosterFrom(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFosterTo(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
				info.setFosterFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveTransferDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDeriveTransferPigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDeriveTransferPigWt());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.FosterIn.getTypeCode());
			}
		} catch (Exception e) {
			logger.error("Exception in PigletStatusInfoHandler.populateFosterInInfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigletStatusInfoMapper, errList);
		}
		return info;
	}
	
	private PigletStatusInfo populateFosterOutInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigletStatusInfoMapper pigletStatusInfoMapper, final ProcessDTO processDTO) {
		PigletStatusInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			if(pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveTransferDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDeriveTransferPigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDeriveTransferPigWt());
				info.setFosterFrom(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFosterTo(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				info.setFosterFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.FosterOut.getTypeCode());
			}
		} catch (Exception e) {
			logger.error("Exception in PigletStatusInfoHandler.populateFosterOutInfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigletStatusInfoMapper, errList);
		}
		return info;
	}
	
	private PigletStatusInfo populatePreMortalityInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigletStatusInfoMapper pigletStatusInfoMapper, final ProcessDTO processDTO) {
		PigletStatusInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			if(pigletStatusInfoMapper.getDeriveMortalityPigNum() != null && pigletStatusInfoMapper.getDeriveMortalityPigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveMortalityEventDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDeriveMortalityPigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDeriveMortalityPigWt());
				info.setMortalityReasonTypeId(pigletStatusInfoMapper.getDeriveMortalityReasonId());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.FosterOut.getTypeCode());
			}
		} catch (Exception e) {
			logger.error("Exception in PigletStatusInfoHandler.populateFosterOutInfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigletStatusInfoMapper, errList);
		}
		return info;
	}
}
