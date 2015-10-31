package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

public class GroupEventInfoHandler implements Handler{
	
	@Autowired
	private GroupEventDaoImpl groupEventDaoImpl;

	private static final Logger logger = Logger.getLogger(PigInfoHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				GroupEventInfoMapper groupEventInfoMapper = (GroupEventInfoMapper) mapper;
				if (groupEventInfoMapper.isRecovrableErrors() == null || groupEventInfoMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						GroupEvent groupEvent = populateGroupEvent(errorMap, groupEventInfoMapper, processDTO);
						if (groupEvent != null) {
							groupEventDaoImpl.addGroupEvent(groupEvent);
							totalRecordsProcessed = totalRecordsProcessed + 1;
						}
					} catch (Exception e) {
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
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private GroupEvent populateGroupEvent(final Map<Mapper, List<ErrorBean>> errorMap, final GroupEventInfoMapper groupEventInfoMapper, final ProcessDTO processDTO) {
		GroupEvent groupEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			groupEvent = new GroupEvent();
			groupEvent.setGroupId(groupEventInfoMapper.getGroupId());
			groupEvent.setCompanyId(groupEventInfoMapper.getDeriveCompanyId());
			groupEvent.setActive(true);
			groupEvent.setGroupStartDateTime(groupEventInfoMapper.getDeriveGroupStartDateTime());
			groupEvent.setUserUpdated(processDTO.getUserName());
			groupEvent.setPhaseOfProductionTypeId(groupEventInfoMapper.getDerivePhaseOfProductionTypeId());
			groupEvent.setGroupCloseDateTime(groupEventInfoMapper.getDeriveGroupcloseDateTime());
			groupEvent.setRemarks(groupEventInfoMapper.getRemarks());
			groupEvent.setCurrentInventory(groupEventInfoMapper.getDeriveCurrentInventory());
			groupEvent.setPreviousGroupId(groupEventInfoMapper.getPreviousGroupId());
		
		} catch (Exception e) {
			logger.error("Exception in PigInfoHandler.populatePigIfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(groupEventInfoMapper, errList);
		}
		return groupEvent;
	}

}
