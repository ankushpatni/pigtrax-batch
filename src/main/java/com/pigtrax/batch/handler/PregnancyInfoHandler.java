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
import com.pigtrax.batch.beans.PregnancyInfo;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class PregnancyInfoHandler implements Handler {

	@Autowired
	private PregnancyInfoDao pregnancyInfoDao;
	
	@Autowired
	private PigTraxEventMasterDao eventMasterDao;

	private static final Logger logger = Logger.getLogger(PigInfoHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PregnancyInfoMapper pregnancyInfoMapper = (PregnancyInfoMapper) mapper;
				if (pregnancyInfoMapper.isRecovrableErrors() == null || pregnancyInfoMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						PregnancyInfo pregnancyInfo = populatePregnancyInfo(errorMap, pregnancyInfoMapper, processDTO);
						if (pregnancyInfoMapper != null) {
							int generatedKey = pregnancyInfoDao.insertPregnancyInfo(pregnancyInfo);							
							PigTraxEventMaster eventMaster = populateEventMaster(pregnancyInfoMapper, generatedKey, processDTO);
							eventMasterDao.insertEventMaster(eventMaster);
							
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
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private PregnancyInfo populatePregnancyInfo(final Map<Mapper, List<ErrorBean>> errorMap, final PregnancyInfoMapper pregnancyInfoMapper, final ProcessDTO processDTO) {
		PregnancyInfo info = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			info = new PregnancyInfo();
			info.setBreedingEventId(pregnancyInfoMapper.getDeriveBreedingEventId());
			info.setPigInfoId(pregnancyInfoMapper.getDerivePigInfoId());
			info.setEmployeeGroupId(pregnancyInfoMapper.getDeriveEmployeeGroupId());
			if(pregnancyInfoMapper.getDeriveExamDate() != null)
				info.setExamDate(pregnancyInfoMapper.getDeriveExamDate());
			else
				info.setExamDate(pregnancyInfoMapper.getDeriveResultDate());
			info.setResultDate(pregnancyInfoMapper.getDeriveResultDate());
			info.setPregnancyEventTypeId(pregnancyInfoMapper.getDerivePregnancyEventTypeId());
			info.setPregnancyExamResultTypeId(pregnancyInfoMapper.getDerivePregnancyExamResultTypeId());
			info.setSowCondition(pregnancyInfoMapper.getDeriveSowCondition());
			info.setUserUpdated(processDTO.getUserName());
		} catch (Exception e) {
			logger.error("Exception in PregnancyInfoHandler.populatePregnancyInfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pregnancyInfoMapper, errList);
		}
		return info;
	}
	
	
	private PigTraxEventMaster populateEventMaster(PregnancyInfoMapper pregnancyInfoMapper, Integer generatedKey, ProcessDTO processDTO)
	{
		PigTraxEventMaster eventMaster = null;
		
		if(generatedKey != null && generatedKey > 0)
		{
			eventMaster = new PigTraxEventMaster();
			eventMaster.setPigInfoId(pregnancyInfoMapper.getDerivePigInfoId());
			eventMaster.setEventTime(pregnancyInfoMapper.getDeriveResultDate());
			eventMaster.setPregnancyEventId(generatedKey);
			eventMaster.setUserUpdated(processDTO.getUserName());
		}
		return eventMaster;
	}

}
