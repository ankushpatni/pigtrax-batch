package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.GroupEventDetail;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.dao.GroupEventDetailsDaoImpl;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.GroupEventDetailMapper;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Component
public class GroupEventDetailHandler implements Handler{
	
	@Autowired
	private GroupEventDetailsDaoImpl groupEventDetailDaoImpl;
	
	@Autowired
	private GroupEventDaoImpl groupEventDaoImpl;
	
	@Autowired
	private PigTraxEventMasterDao eventMasterDao;

	private static final Logger logger = Logger.getLogger(GroupEventDetailHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				GroupEventDetailMapper groupEventDetailMapper = (GroupEventDetailMapper) mapper;
				if(!groupEventDetailMapper.isEmpty())
				{
					if (groupEventDetailMapper.isRecovrableErrors() == null || groupEventDetailMapper.isRecovrableErrors()) {
						boolean isErrorOccured = false;
						try {
							GroupEventDetail groupEventDetail = populateGroupEventDetails(errorMap, groupEventDetailMapper, processDTO);
							if (groupEventDetail != null) {
								groupEventDetailDaoImpl.addGroupEventDetails(groupEventDetail);
								GroupEvent groupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(groupEventDetail.getGroupId(),groupEventDetail.getCompanyId());
								groupEvent.setCurrentInventory(groupEvent.getCurrentInventory()+groupEventDetail.getNumberOfPigs());
								groupEventDaoImpl.updateGroupEventCurrentInventory(groupEvent);
								
								PigTraxEventMaster eventMaster = populateEventMaster(groupEventDetailMapper, groupEventDetailMapper.getDeriveGroupId(), processDTO);
								eventMasterDao.insertEventMaster(eventMaster);
								totalRecordsProcessed = totalRecordsProcessed + 1;
							}
						} 
						catch (Exception e) {
							e.printStackTrace();
							logger.error("Exception in GroupEventInfoHandler.execute : " + e);
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
	
	private GroupEventDetail populateGroupEventDetails(final Map<Mapper, List<ErrorBean>> errorMap, final GroupEventDetailMapper groupEventDetailMapper, final ProcessDTO processDTO) {
		GroupEventDetail groupEventDetail = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			groupEventDetail = new GroupEventDetail();
						
			groupEventDetail.setGroupId(groupEventDetailMapper.getDeriveGroupId());
			groupEventDetail.setPremiseId(groupEventDetailMapper.getDerivePremiseId());
			groupEventDetail.setSowSourceId(groupEventDetailMapper.getDeriveSowSourceId());
			groupEventDetail.setBarnId(groupEventDetailMapper.getDeriveBarnId());
			groupEventDetail.setDateOfEntry(groupEventDetailMapper.getDeriveDateOfEntry());
			groupEventDetail.setNumberOfPigs(groupEventDetailMapper.getDeriveNumberOfPigs());
			groupEventDetail.setWeightInKgs(groupEventDetailMapper.getDeriveWeightInKgs());
			groupEventDetail.setInventoryAdjustment(groupEventDetailMapper.getDeriveInventoryAdjustment());
			groupEventDetail.setRemarks(groupEventDetailMapper.getRemarks());
			groupEventDetail.setRoomId(groupEventDetailMapper.getDeriveRoomId());
			groupEventDetail.setEmployeeGroupId(groupEventDetailMapper.getDeriveEmployeeGroupId());
			groupEventDetail.setUserUpdated(processDTO.getUserName());
			groupEventDetail.setCompanyId(groupEventDetailMapper.getDerivecompanyId());
			
		} catch (Exception e) {
			logger.error("Exception in GroupEventDetailHandler.populateGroupEventDetails" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(groupEventDetailMapper, errList);
		}
		return groupEventDetail;
	}
	
	private PigTraxEventMaster populateEventMaster(GroupEventDetailMapper groupEventDetailMapper, Integer generatedKey, ProcessDTO processDTO) {
		PigTraxEventMaster eventMaster = null;
		if (generatedKey != null && generatedKey > 0) {
			eventMaster = new PigTraxEventMaster();
			eventMaster.setEventTime(groupEventDetailMapper.getDeriveDateOfEntry());
			eventMaster.setGroupEventId(groupEventDetailMapper.getDeriveGroupId());
			eventMaster.setUserUpdated(processDTO.getUserName());
		}
		return eventMaster;
	}


}
