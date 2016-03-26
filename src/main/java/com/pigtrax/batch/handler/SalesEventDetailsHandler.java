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
import com.pigtrax.batch.beans.GroupEventDetail;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.SalesEventDetails;
import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.dao.GroupEventDetailsDaoImpl;
import com.pigtrax.batch.dao.SalesEventDetailsDaoImpl;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.SalesEventDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
@Component
public class SalesEventDetailsHandler implements Handler{
	
	@Autowired
	private SalesEventDetailsDaoImpl salesEventDetailsDaoImpl;
	
	@Autowired
	private GroupEventDaoImpl groupEventDaoImpl;
	
	@Autowired
	private PigTraxEventMasterDao eventMasterDao;
	

	@Autowired
	private GroupEventDetailsDaoImpl groupEventDetailDaoImpl;
	
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;

	private static final Logger logger = Logger.getLogger(SalesEventDetailsHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		int journeyId = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				SalesEventDetailsMapper salesEventDetailsMapper = (SalesEventDetailsMapper) mapper;
				if (salesEventDetailsMapper.isRecovrableErrors() == null || salesEventDetailsMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						SalesEventDetails salesEventDetails = populateSalesEventDetails(errorMap, salesEventDetailsMapper, processDTO);
						if (salesEventDetails != null) {
							if(salesEventDetailsMapper.getDeriveTransportTrailer() != null || salesEventDetailsMapper.getDeriveTransportTruck() != null)
							{
								TransportJourney journey = new TransportJourney();
								journey.setTransportTrailerId(salesEventDetailsMapper.getDeriveTransportTrailer());
								journey.setTransportTruckId(salesEventDetailsMapper.getDeriveTransportTruck());
								journey.setUserUpdated(processDTO.getUserName());
								journeyId = transportJourneyDao.addTransportJourney(journey);
								if(journeyId > 0)
									salesEventDetails.setTransPortJourneyId(journeyId);
							}
							int id =salesEventDetailsDaoImpl.addSalesEventDetails(salesEventDetails);
							if(salesEventDetails.getGroupEventId() != null)
							{
								GroupEvent groupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(salesEventDetails.getGroupEventId(),salesEventDetails.getCompanyId());
								
								//Add a negative transaction in the group event details
								GroupEventDetail groupEventDetails = new GroupEventDetail();
								groupEventDetails.setGroupId(groupEvent.getId());
								groupEventDetails.setDateOfEntry(salesEventDetails.getSalesDateTime());
								groupEventDetails.setNumberOfPigs(-1*salesEventDetails.getNumberOfPigs());
								groupEventDetails.setWeightInKgs(salesEventDetails.getWeightInKgs().doubleValue());
								groupEventDetails.setUserUpdated(salesEventDetails.getUserUpdated());
								groupEventDetails.setRemarks("Sales tracked through Pig Movement Mass Upload");
								groupEventDetailDaoImpl.addGroupEventDetails(groupEventDetails);
								
								groupEvent.setCurrentInventory(groupEvent.getCurrentInventory() - salesEventDetails.getNumberOfPigs());
								groupEventDaoImpl.updateGroupEventCurrentInventory(groupEvent);
							}
							if(salesEventDetails.getPigInfoId() != null)
							{
								pigInfoDao.updatePigInfoStatus(salesEventDetails.getPigInfoId(), false);
							}
							PigTraxEventMaster eventMaster = populateEventMaster(salesEventDetailsMapper, id, processDTO);
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
	
	private SalesEventDetails populateSalesEventDetails(final Map<Mapper, List<ErrorBean>> errorMap, SalesEventDetailsMapper salesEventDetailsMapper, final ProcessDTO processDTO) {
		SalesEventDetails salesEventDetails = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			salesEventDetails = new SalesEventDetails();
			salesEventDetails.setGroupEventId(salesEventDetailsMapper.getDeriveGroupEventId());
			salesEventDetails.setPigInfoId(salesEventDetailsMapper.getDerivePigInfoId());
			salesEventDetails.setCompanyId(salesEventDetailsMapper.getDeriveCompanyId());
			salesEventDetails.setNumberOfPigs(salesEventDetailsMapper.getDeriveNumberOfPigs());
			salesEventDetails.setSalesDateTime(salesEventDetailsMapper.getDeriveSalesDateTime());
			salesEventDetails.setWeightInKgs(new BigDecimal(salesEventDetailsMapper.getDeriveWeightInKgs().doubleValue()));
			salesEventDetails.setRemovalEventId(3);
			salesEventDetails.setRemarks(salesEventDetailsMapper.getRemarks());
			salesEventDetails.setRevenueUsd(salesEventDetailsMapper.getDeriveRevenueUsd());
			salesEventDetails.setInvoiceId(salesEventDetailsMapper.getInvoiceNumber());
			salesEventDetails.setSoldTo(salesEventDetailsMapper.getSoldTo());
			salesEventDetails.setTicketNumber(salesEventDetailsMapper.getTicketNumber());
			salesEventDetails.setUserUpdated(processDTO.getUserName());
			salesEventDetails.setSalesTypesAsString(salesEventDetailsMapper.getDeriveSalesTypes());
			salesEventDetails.setSalesReasonAsString(salesEventDetailsMapper.getDeriveSalesReason());
		} catch (Exception e) {
			logger.error("Exception in RemovalEventExceptSalesDetailsHandler.salesEventDetails" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(salesEventDetailsMapper, errList);
		}
		return salesEventDetails;
	}
	
	private PigTraxEventMaster populateEventMaster(SalesEventDetailsMapper salesEventDetailsMapper, Integer generatedKey, ProcessDTO processDTO) {
		PigTraxEventMaster eventMaster = null;
		if (generatedKey != null && generatedKey > 0) {
			eventMaster = new PigTraxEventMaster();
			eventMaster.setEventTime(salesEventDetailsMapper.getDeriveSalesDateTime());
			eventMaster.setSalesEventDetails(generatedKey);
			eventMaster.setUserUpdated(processDTO.getUserName());
			eventMaster.setPigInfoId(salesEventDetailsMapper.getDerivePigInfoId());
		}
		return eventMaster;
	}
	
}