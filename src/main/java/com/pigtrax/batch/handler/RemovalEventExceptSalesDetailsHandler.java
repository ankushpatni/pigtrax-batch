package com.pigtrax.batch.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.dao.RemovalEventExceptSalesDetailsDaoImpl;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Component
public class RemovalEventExceptSalesDetailsHandler implements Handler{
	
	@Autowired
	private RemovalEventExceptSalesDetailsDaoImpl removalEventExceptSalesDetailsDaoImpl;
	
	@Autowired
	private GroupEventDaoImpl groupEventDaoImpl;
	
	@Autowired
	private PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;

	private static final Logger logger = Logger.getLogger(GroupEventDetailHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
				if (removalEventExceptSalesDetailsMapper.isRecovrableErrors() == null || removalEventExceptSalesDetailsMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						RemovalEventExceptSalesDetails removalEventExceptSalesDetails = populateRemovalEventExceptSalesDetails(errorMap, removalEventExceptSalesDetailsMapper, processDTO);
						if (removalEventExceptSalesDetails != null) {
							
							TransportJourney transportJourney = populateTransportJourney(removalEventExceptSalesDetailsMapper,processDTO);
							Integer transportJourneyId =  transportJourneyDao.addTransportJourney(transportJourney);
							removalEventExceptSalesDetails.setTransportJourneyId(transportJourneyId);
							Integer id = removalEventExceptSalesDetailsDaoImpl.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
							GroupEvent groupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetails.getGroupEventId(),removalEventExceptSalesDetails.getCompanyId());
							groupEvent.setCurrentInventory(groupEvent.getCurrentInventory() - removalEventExceptSalesDetails.getNumberOfPigs());
							groupEventDaoImpl.updateGroupEventCurrentInventory(groupEvent);
							
							PigTraxEventMaster eventMaster = populateEventMaster(removalEventExceptSalesDetailsMapper, id, processDTO);
							eventMasterDao.insertEventMaster(eventMaster);
							
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
	
	private RemovalEventExceptSalesDetails populateRemovalEventExceptSalesDetails(final Map<Mapper, List<ErrorBean>> errorMap, RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, final ProcessDTO processDTO) {
		RemovalEventExceptSalesDetails removalEventExceptSalesDetails = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			removalEventExceptSalesDetails = new RemovalEventExceptSalesDetails();
			removalEventExceptSalesDetails.setGroupEventId(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId());
			removalEventExceptSalesDetails.setPigInfoId(removalEventExceptSalesDetailsMapper.getDerivePigInfoId());
			removalEventExceptSalesDetails.setCompanyId(removalEventExceptSalesDetailsMapper.getDeriveCompanyId());
			removalEventExceptSalesDetails.setNumberOfPigs(removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs());
			removalEventExceptSalesDetails.setRemovalDateTime(removalEventExceptSalesDetailsMapper.getDeriveRemovalDateTime());
			removalEventExceptSalesDetails.setWeightInKgs(new BigDecimal(removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs().intValue()));
			removalEventExceptSalesDetails.setRemovalEventId(removalEventExceptSalesDetailsMapper.getDeriveRemovalTypeEventId());
			removalEventExceptSalesDetails.setPremiseId(removalEventExceptSalesDetailsMapper.getDerivePremiseId());
			removalEventExceptSalesDetails.setDestPremiseId(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId());
			removalEventExceptSalesDetails.setRemarks(removalEventExceptSalesDetailsMapper.getRemarks());
			removalEventExceptSalesDetails.setMortalityReasonId(removalEventExceptSalesDetailsMapper.getDeriveMortalityReasonId());
			removalEventExceptSalesDetails.setUserUpdated(processDTO.getUserName());
		} catch (Exception e) {
			logger.error("Exception in RemovalEventExceptSalesDetailsHandler.removalEventExceptSalesDetails" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(removalEventExceptSalesDetailsMapper, errList);
		}
		return removalEventExceptSalesDetails;
	}
	
	private PigTraxEventMaster populateEventMaster(RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, Integer generatedKey, ProcessDTO processDTO) {
		PigTraxEventMaster eventMaster = null;
		if (generatedKey != null && generatedKey > 0) {
			eventMaster = new PigTraxEventMaster();
			eventMaster.setEventTime(removalEventExceptSalesDetailsMapper.getDeriveRemovalDateTime());
			eventMaster.setRemovalEventExceptSalesDetails(generatedKey);
			eventMaster.setUserUpdated(processDTO.getUserName());
		}
		return eventMaster;
	}
	
	private TransportJourney populateTransportJourney(RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, final ProcessDTO processDTO)
	{
		TransportJourney transportJourney = new TransportJourney();
		transportJourney.setJourneyStartTime(removalEventExceptSalesDetailsMapper.getDeriveJourneyStartTime());
		transportJourney.setJourneyEndTime(removalEventExceptSalesDetailsMapper.getDeriveJourneyEndTime());
		transportJourney.setTrailerFunction(removalEventExceptSalesDetailsMapper.getTrailerFunction());
		transportJourney.setTransportDestinationId(removalEventExceptSalesDetailsMapper.getDeriveTransportDestinationId());
		transportJourney.setTransportTrailerId(removalEventExceptSalesDetailsMapper.getDeriveTransportTrailerId());
		transportJourney.setTransportTruckId(removalEventExceptSalesDetailsMapper.getDeriveTransportTruckId());
		transportJourney.setUserUpdated(processDTO.getUserName());
		return transportJourney;
	}
	
}
