package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.BreedingEvent;
import com.pigtrax.batch.beans.IndividualPigletStatus;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.BreedingEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class BreedingEventHandler implements Handler {

	@Autowired
	private BreedingEventDao breedingEventDao;

	private static final Logger logger = Logger.getLogger(BreedingEventHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				BreedingEventMapper breedingEventMapper = (BreedingEventMapper) mapper;
				if(!breedingEventMapper.isEmpty())
				{
					if (breedingEventMapper.isRecovrableErrors() == null || breedingEventMapper.isRecovrableErrors()) {
						boolean isErrorOccured = false;
						try {
							BreedingEvent breedingEvent = populateBreedingEvent(errorMap, breedingEventMapper, processDTO);
							if (breedingEvent != null) {
								if(breedingEventDao.checkIfPreviousCycleCompleted(breedingEvent.getPigInfoId()))
								{
									breedingEventDao.insertBreedingEventInfo(breedingEvent);
									totalRecordsProcessed+=1;
								}
								else
								{
									breedingEventMapper.setRecovrableErrors(false);
									isErrorOccured = true;
									errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_CODE, Constants.BREEDING_EVNT_ERR_INCOMPLETE_CYCLE_MSG, "pigId", false));
								}
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
			}
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private BreedingEvent populateBreedingEvent(final Map<Mapper, List<ErrorBean>> errorMap, final BreedingEventMapper breedingEventMapper,
			final ProcessDTO processDTO) {
		BreedingEvent breedingEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			breedingEvent = new BreedingEvent();
			breedingEvent.setPigInfoId(breedingEventMapper.getDerivePigInfoId());
			breedingEvent.setBreedingServiceTypeId(breedingEventMapper.getDeriveServiceTypeId());
			breedingEvent.setServiceGroupId(breedingEventMapper.getServiceGroupId());
			breedingEvent.setPenId(breedingEventMapper.getDerivePenId());
			breedingEvent.setSowCondition(breedingEventMapper.getDeriveSowCondition());
			breedingEvent.setWeight(breedingEventMapper.getDeriveWtInKgs());
			breedingEvent.setUserUpdated(processDTO.getUserName());
			breedingEvent.setPremiseId(breedingEventMapper.getDerivePremiseId());
		} catch (Exception e) {
			logger.error("Exception in FarrowEventHandler.populateFarrowEventfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(breedingEventMapper, errList);
		}
		return breedingEvent;
	}
}
