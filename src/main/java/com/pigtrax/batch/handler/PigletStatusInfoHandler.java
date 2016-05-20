package com.pigtrax.batch.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.GroupEventDetail;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.PigletStatusInfo;
import com.pigtrax.batch.config.PigletStatusEventType;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
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
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	PenDao penDao;
	
	@Autowired
	GroupEventDetailsDao groupEventDetailsDao;

	private static final Logger logger = Logger.getLogger(PigletStatusInfoHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PigletStatusInfoMapper pigletStatusInfoMapper = (PigletStatusInfoMapper) mapper;
				if(!pigletStatusInfoMapper.isEmpty())
				{
					if (pigletStatusInfoMapper.isRecovrableErrors() == null || pigletStatusInfoMapper.isRecovrableErrors()) {
						boolean isErrorOccured = false;
						try {
							PigletStatusInfo pigletStatusInfo = null;
							if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode())
							{
								Integer pkValue = pigletStatusInfoDao.getPKPigletStatus(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.Wean.getTypeCode());
								
								if(pkValue != null)
								{
									PigletStatusInfo statusEvent = pigletStatusInfoDao.getPigletStatusEventInformation(pkValue);
									if(statusEvent != null && statusEvent.getGroupEventId() != null)
									{
										groupEventDetailsDao.deleteGroupEventDetailsByPigletEvent(statusEvent.getId()); 
										GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(statusEvent.getGroupEventId(), processDTO.getCompanyId());
										if(groupEvent != null)
										{
											Integer newInvValue = groupEvent.getCurrentInventory() - statusEvent.getNumberOfPigs();
											groupEvent.setCurrentInventory(newInvValue);
											groupEventDao.updateGroupEventCurrentInventory(groupEvent);
										}
										
										
									}
								}
								
								
								pigletStatusInfoDao.deletePigletStatusEventsByFarrowId(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.Wean.getTypeCode());
								if(pkValue != null)
									eventMasterDao.deletePigletStatusEvents(pkValue);
								
								pigletStatusInfo = populatePigletStatusWeanInfo(errorMap, pigletStatusInfoMapper, processDTO);
								if (pigletStatusInfo != null) {
									int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
									
									
									//update Group Event details
									if(pigletStatusInfo.getGroupEventId() != null && pigletStatusInfo.getGroupEventId() > 0)
										updateGroupEventDetails(pigletStatusInfo, generatedKey, processDTO);
									
									PigTraxEventMaster master = new PigTraxEventMaster();
									master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
									master.setUserUpdated(processDTO.getUserName());
									master.setEventTime(pigletStatusInfoMapper.getDeriveEventDate());
									master.setPigletStatusId(generatedKey);
									master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
									
									eventMasterDao.insertEventMaster(master);
								
								}
							}
							if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode())
							{
								Integer pkValue = pigletStatusInfoDao.getPKPigletStatus(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.FosterOut.getTypeCode());
								pigletStatusInfoDao.deletePigletStatusEventsByFarrowId(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.FosterOut.getTypeCode());
								if(pkValue != null)
								eventMasterDao.deletePigletStatusEvents(pkValue);
								pigletStatusInfo = populateFosterOutInfo(errorMap, pigletStatusInfoMapper, processDTO);
								if (pigletStatusInfo != null) {
									int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
									
									PigTraxEventMaster master = new PigTraxEventMaster();
									master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
									master.setUserUpdated(processDTO.getUserName());
									master.setEventTime(pigletStatusInfoMapper.getDeriveEventDate());
									master.setPigletStatusId(generatedKey);
									master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
									
									eventMasterDao.insertEventMaster(master);
								}
								
								
								pkValue = pigletStatusInfoDao.getPKPigletStatus(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.FosterIn.getTypeCode());
								pigletStatusInfoDao.deletePigletStatusEventsByFarrowId(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.FosterIn.getTypeCode());
								if(pkValue != null)
								eventMasterDao.deletePigletStatusEvents(pkValue);
								
								pigletStatusInfo = populateFosterInInfo(errorMap, pigletStatusInfoMapper, processDTO);
								if (pigletStatusInfo != null) {
									int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
									
									PigTraxEventMaster master = new PigTraxEventMaster();
									master.setPigInfoId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
									master.setUserUpdated(processDTO.getUserName());
									master.setEventTime(pigletStatusInfoMapper.getDeriveEventDate());
									master.setPigletStatusId(generatedKey);
									master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
									
									eventMasterDao.insertEventMaster(master); 
								}
							}
							
							if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode())
							{
								Integer pkValue = pigletStatusInfoDao.getPKPigletStatus(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.Death.getTypeCode());
								/*if(pkValue != null)
								{
									eventMasterDao.deletePigletStatusEvents(pkValue);
									pigletStatusInfoDao.deletePigletStatusEventsByFarrowId(pigletStatusInfoMapper.getDerivePigInfoId(), pigletStatusInfoMapper.getDeriveFarrowEventId(), PigletStatusEventType.Death.getTypeCode());
								}
								*/
								pigletStatusInfo = populatePreMortalityInfo(errorMap, pigletStatusInfoMapper, processDTO);
								if (pigletStatusInfo != null) {
									int generatedKey = pigletStatusInfoDao.insertPigletStatusInfo(pigletStatusInfo);
									
									PigTraxEventMaster master = new PigTraxEventMaster();
									master.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
									master.setUserUpdated(processDTO.getUserName());
									master.setEventTime(pigletStatusInfoMapper.getDeriveEventDate());
									master.setPigletStatusId(generatedKey);
									master.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
									
									eventMasterDao.insertEventMaster(master);
								
								}
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
			}
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	
	private void updateGroupEventDetails(PigletStatusInfo pigletStatusInfo, Integer pigletStatusEventId, ProcessDTO processDTO) throws SQLException
	 {
		 	GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(pigletStatusInfo.getGroupEventId(), processDTO.getCompanyId());
			if(groupEvent != null)
			{
				Integer inventoryValue = groupEvent.getCurrentInventory();
				inventoryValue = inventoryValue+pigletStatusInfo.getNumberOfPigs();
				groupEvent.setCurrentInventory(inventoryValue);
				groupEventDao.updateGroupEventCurrentInventory(groupEvent);
				
				//add to group event details							
				GroupEventDetail groupEventDetails = new GroupEventDetail();
				groupEventDetails.setRoomId(null);
				groupEventDetails.setBarnId(null);
				
				groupEventDetails.setPigletStatusEventId(pigletStatusEventId);
				groupEventDetails.setPremiseId(pigletStatusInfo.getPremiseId());
				groupEventDetails.setDateOfEntry(pigletStatusInfo.getEventDateTime());
				groupEventDetails.setNumberOfPigs(pigletStatusInfo.getNumberOfPigs());
				groupEventDetails.setWeightInKgs(pigletStatusInfo.getWeightInKgs());
				groupEventDetails.setGroupId(pigletStatusInfo.getGroupEventId());
				groupEventDetails.setUserUpdated(pigletStatusInfo.getUserUpdated());
				groupEventDetails.setRemarks("From piglet wean");
				groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
				
				groupEventDao.updateGroupEventCurrentInventory(groupEvent);
			}
	 }
	
	private PigletStatusInfo populatePigletStatusWeanInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigletStatusInfoMapper pigletStatusInfoMapper, final ProcessDTO processDTO) {
		PigletStatusInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			if(pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveEventDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDerivePigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDerivePigWt());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());
				info.setGroupEventId(pigletStatusInfoMapper.getDeriveGroupEventId());			
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.Wean.getTypeCode());
				info.setPremiseId(pigletStatusInfoMapper.getDerivePremiseId());
				info.setPenId(pigletStatusInfoMapper.getDerivePenId());
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
			if(pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				info.setFosterFrom(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFosterTo(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
				info.setFosterFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveEventDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDerivePigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDerivePigWt());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.FosterIn.getTypeCode());
				info.setPremiseId(pigletStatusInfoMapper.getDerivePremiseId());
				info.setPenId(pigletStatusInfoMapper.getDerivePenId());
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
			if(pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveEventDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDerivePigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDerivePigWt());
				info.setFosterFrom(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFosterTo(pigletStatusInfoMapper.getDeriveTransferredPigInfoId());
				info.setFosterFarrowEventId(pigletStatusInfoMapper.getDeriveFosterFarrowEventId());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.FosterOut.getTypeCode());
				info.setPremiseId(pigletStatusInfoMapper.getDerivePremiseId());
				info.setPenId(pigletStatusInfoMapper.getDerivePenId());
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
			if(pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum() > 0)
			{
				info = new PigletStatusInfo();
				info.setPigInfoId(pigletStatusInfoMapper.getDerivePigInfoId());
				info.setFarrowEventId(pigletStatusInfoMapper.getDeriveFarrowEventId());
				info.setEventDateTime(pigletStatusInfoMapper.getDeriveEventDate());
				info.setNumberOfPigs(pigletStatusInfoMapper.getDerivePigNum());
				info.setWeightInKgs(pigletStatusInfoMapper.getDerivePigWt());
				info.setMortalityReasonTypeId(pigletStatusInfoMapper.getDeriveMortalityReasonId());
				info.setRemarks(pigletStatusInfoMapper.getRemarks());
				info.setSowCondition(pigletStatusInfoMapper.getDeriveSowCondition());
				info.setWeanGroupId(pigletStatusInfoMapper.getWeanGroupId());	
				info.setUserUpdated(processDTO.getUserName());
				info.setPigletStatusEventType(PigletStatusEventType.Death.getTypeCode());
				info.setPremiseId(pigletStatusInfoMapper.getDerivePremiseId());
				info.setPenId(pigletStatusInfoMapper.getDerivePenId());
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
