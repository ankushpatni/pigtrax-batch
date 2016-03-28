package com.pigtrax.batch.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.GroupEventDetail;
import com.pigtrax.batch.beans.GroupEventPhaseChange;
import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.batch.beans.SowMovement;
import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.dao.GroupEventDetailsDaoImpl;
import com.pigtrax.batch.dao.RemovalEventExceptSalesDetailsDaoImpl;
import com.pigtrax.batch.dao.interfaces.GroupEventPhaseChangeDao;
import com.pigtrax.batch.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.SowMovementDao;
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
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	GroupEventPhaseChangeDao groupEventPhaseChangeDao;
	
	@Autowired
	private GroupEventRoomDao groupEventRoomDao;
	
	@Autowired
	private GroupEventDetailsDaoImpl groupEventDetailDaoImpl;
	
	@Autowired
	SowMovementDao sowMovementDao;


	private static final Logger logger = Logger.getLogger(GroupEventDetailHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		int journeyId =0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
				if (removalEventExceptSalesDetailsMapper.isRecovrableErrors() == null || removalEventExceptSalesDetailsMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						RemovalEventExceptSalesDetails removalEventExceptSalesDetails = populateRemovalEventExceptSalesDetails(errorMap, removalEventExceptSalesDetailsMapper, processDTO);
						if (removalEventExceptSalesDetails != null) {
							
							if(removalEventExceptSalesDetails.getGroupEventId() != null)
							{
								GroupEvent groupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetails.getGroupEventId(),removalEventExceptSalesDetails.getCompanyId());
								removalEventExceptSalesDetails.setPremiseId(groupEvent.getPremiseId());
								//removalEventExceptSalesDetails.setNumberOfPigs(groupEvent.getCurrentInventory());
								if(groupEvent != null)
								{
									if(removalEventExceptSalesDetails.getRemovalEventId().intValue() == 9 && removalEventExceptSalesDetails.getToGroupEventId() == null)
									{
										groupEvent.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
										groupEventDaoImpl.updateGroupEvent(groupEvent);
										
										GroupEventPhaseChange currentPhase = groupEventPhaseChangeDao.getCurrentPhaseObject(groupEvent.getId());
										if(currentPhase != null)
										{
											currentPhase.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
											groupEventPhaseChangeDao.updatePhaseDetails(currentPhase);
											
											groupEventRoomDao.deleteGroupEventRooms(currentPhase.getId());
											groupEventRoomDao.addGroupEventRooms(currentPhase);
										}
										
									}
									else
									{
										//Add a negative transaction in the group event details
										GroupEventDetail groupEventDetails = new GroupEventDetail();
										groupEventDetails.setGroupId(groupEvent.getId());
										groupEventDetails.setDateOfEntry(removalEventExceptSalesDetails.getRemovalDateTime());
										groupEventDetails.setNumberOfPigs(-1*removalEventExceptSalesDetails.getNumberOfPigs());
										groupEventDetails.setWeightInKgs(removalEventExceptSalesDetails.getWeightInKgs().doubleValue());
										groupEventDetails.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
										groupEventDetails.setRemarks("Removed through Pig Movement Mass Upload");
										groupEventDetailDaoImpl.addGroupEventDetails(groupEventDetails);
										
										groupEvent.setCurrentInventory(groupEvent.getCurrentInventory() - removalEventExceptSalesDetails.getNumberOfPigs());
										groupEventDaoImpl.updateGroupEventCurrentInventory(groupEvent);
										
										if(removalEventExceptSalesDetails.getToGroupEventId() != null)
										{
											GroupEventDetail toGroupEventDetails = new GroupEventDetail();
											toGroupEventDetails.setGroupId(removalEventExceptSalesDetails.getToGroupEventId());
											toGroupEventDetails.setDateOfEntry(removalEventExceptSalesDetails.getRemovalDateTime());
											toGroupEventDetails.setNumberOfPigs(removalEventExceptSalesDetails.getNumberOfPigs());
											toGroupEventDetails.setWeightInKgs(removalEventExceptSalesDetails.getWeightInKgs().doubleValue());
											toGroupEventDetails.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
											toGroupEventDetails.setRemarks("Recived through Pig Movement Mass Upload");
											groupEventDetailDaoImpl.addGroupEventDetails(toGroupEventDetails);
											
											GroupEvent newGroupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetails.getToGroupEventId(), removalEventExceptSalesDetails.getCompanyId());
											newGroupEvent.setCurrentInventory(removalEventExceptSalesDetails.getNumberOfPigs()+newGroupEvent.getCurrentInventory());
											groupEventDaoImpl.updateGroupEventCurrentInventory(newGroupEvent);
											
										}
									}
								}
								
							}
							else if(removalEventExceptSalesDetails.getPigInfoId() != null)
							{
								//pigInfoDao.updatePigInfoStatus(removalEventExceptSalesDetails.getPigInfoId(), false);
								
								PigInfo pigInfo = pigInfoDao.getPigDetails(removalEventExceptSalesDetails.getPigInfoId());
								removalEventExceptSalesDetails.setPremiseId(pigInfo.getPremiseId());
								//removalEventExceptSalesDetails.setNumberOfPigs(1);
								if(null != pigInfo)
								{
									//No need to change the pig status on transfer
									//pigInfoDao.updatePigInfoStatus(removalEventExceptSalesDetails.getPigInfoId(), false);
									//update the pig premise and room details.
									
									if(removalEventExceptSalesDetails.getRemovalEventId() != 9)
									{
										pigInfo.setActive(false);
									}
									else
									{
										pigInfo.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
										pigInfo.setRoomId(removalEventExceptSalesDetails.getRoomId());
									}
									
									pigInfoDao.updatePigInformation(pigInfo);
									
									if(removalEventExceptSalesDetails.getRemovalEventId() == 9)
									{
									   SowMovement sowMovement = new SowMovement();
									   sowMovement.setPigInfoId(pigInfo.getId());
									   sowMovement.setPremiseId(pigInfo.getPremiseId());
									   sowMovement.setRoomId(pigInfo.getRoomId());
									   sowMovement.setUserUpdated(pigInfo.getUserUpdated());
									   sowMovement.setCompanyId(pigInfo.getCompanyId());
									   sowMovementDao.addSowMovement(sowMovement);
									}
									
								}
							}
							if(removalEventExceptSalesDetailsMapper.getDeriveTransportTrailer() != null || removalEventExceptSalesDetailsMapper.getDeriveTransportTruck() != null)
							{
								TransportJourney journey = new TransportJourney();
								journey.setTransportTrailerId(removalEventExceptSalesDetailsMapper.getDeriveTransportTrailer());
								journey.setTransportTruckId(removalEventExceptSalesDetailsMapper.getDeriveTransportTruck());
								journey.setUserUpdated(processDTO.getUserName());
								journeyId = transportJourneyDao.addTransportJourney(journey);
								if(journeyId > 0)
									removalEventExceptSalesDetails.setTransportJourneyId(journeyId);
							}
							Integer id = removalEventExceptSalesDetailsDaoImpl.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
							
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
			//removalEventExceptSalesDetails.setPremiseId(removalEventExceptSalesDetailsMapper.getDerivePremiseId());
			removalEventExceptSalesDetails.setDestPremiseId(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId());
			removalEventExceptSalesDetails.setRemarks(removalEventExceptSalesDetailsMapper.getRemarks());
			removalEventExceptSalesDetails.setMortalityReasonId(removalEventExceptSalesDetailsMapper.getDeriveMortalityReasonId());
			removalEventExceptSalesDetails.setRevenue(removalEventExceptSalesDetailsMapper.getDeriveRevenue());
			removalEventExceptSalesDetails.setRoomId(removalEventExceptSalesDetailsMapper.getDeriveRoomId());
			removalEventExceptSalesDetails.setUserUpdated(processDTO.getUserName());
			
			removalEventExceptSalesDetails.setToGroupEventId(removalEventExceptSalesDetailsMapper.getDeriveToGroupEventId());
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
			eventMaster.setPigInfoId(removalEventExceptSalesDetailsMapper.getDerivePigInfoId());
		}
		return eventMaster;
	}
	
}
