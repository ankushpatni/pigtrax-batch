package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.FeedEvent;
import com.pigtrax.batch.beans.FeedEventDetail;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.FeedEventDao;
import com.pigtrax.batch.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.FeedDetailEventMapper;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class FeedDetailEventHandler implements Handler {
	
	@Autowired
	private FeedEventDao feedEventDao;
	
	@Autowired
	private FeedEventDetailDao feedEventDetailDao;

	@Autowired
	private PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;

	
	private static final Logger logger = Logger.getLogger(FeedDetailEventHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		int journeyId = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				FeedDetailEventMapper feedEventMapper = (FeedDetailEventMapper) mapper;
				if(!feedEventMapper.isEmpty())
				{
					if (feedEventMapper.isRecovrableErrors() == null || feedEventMapper.isRecovrableErrors()) {
						boolean isErrorOccured = false;
						try {
							
							FeedEvent feedEvent = populateFeedEventfnfo(errorMap, feedEventMapper, processDTO);
							if(feedEventMapper.getDeriveTransportTrailer() != null || feedEventMapper.getDeriveTransportTruck() != null)
							{
								TransportJourney journey = new TransportJourney();
								journey.setTransportTrailerId(feedEventMapper.getDeriveTransportTrailer());
								journey.setTransportTruckId(feedEventMapper.getDeriveTransportTruck());
								journey.setUserUpdated(processDTO.getUserName());
								journeyId = transportJourneyDao.addTransportJourney(journey);
								if(journeyId > 0)
									feedEvent.setTransPortJourneyId(journeyId);
							}
							
							if(feedEvent != null)
							{
								if(feedEvent.getId() == null)
								{							
									int id = feedEventDao.addFeedEvent(feedEvent);
									feedEvent.setId(id);
									feedEventMapper.setDeriveFeedEventId(id);									
								}
								else
								{
									feedEventDao.updateFeedEvent(feedEvent);
									feedEventMapper.setDeriveFeedEventId(feedEvent.getId());
								}
								
								PigTraxEventMaster eventMaster = populateEventMaster(feedEventMapper, feedEvent.getId(), processDTO);
								eventMasterDao.insertEventMaster(eventMaster);
									
							}
							
							FeedEventDetail feedEventDetail = populateFeedEventDetailInfo(errorMap, feedEventMapper, processDTO);
							
							if (feedEventDetail != null) {								
								int id = feedEventDetailDao.addFeedEventDetail(feedEventDetail);							
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
			}
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	
	private FeedEvent populateFeedEventfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final FeedDetailEventMapper feedEventMapper, final ProcessDTO processDTO) {
		FeedEvent feedEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			feedEvent = new FeedEvent();
			if(feedEventMapper.isUpdate())
				feedEvent.setId(feedEventMapper.getDeriveFeedEventId());
			else
			{
				Integer feedId = feedEventDao.getFeedEventPKId(feedEventMapper.getTicketNumber(), feedEventMapper.getDerivePremiseId());
				if(feedId != null && feedId > 0)
					feedEvent.setId(feedId);
				else
					feedEvent.setId(null);
			}
			feedEvent.setFeedMadication(feedEventMapper.getFeedMadication());
			feedEvent.setRationId(feedEventMapper.getDeriveRationId());
			feedEvent.setTicketNumber(feedEventMapper.getTicketNumber());
			feedEvent.setUserUpdated(processDTO.getUserName());
			feedEvent.setPremiseId(feedEventMapper.getDerivePremiseId());
		} catch (Exception e) {
			logger.error("Exception in FeedEventHandler.populateFeedEventfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(feedEventMapper, errList);
		}
		return feedEvent;
	}
	
	
	private FeedEventDetail populateFeedEventDetailInfo(final Map<Mapper, List<ErrorBean>> errorMap, final FeedDetailEventMapper feedEventMapper, final ProcessDTO processDTO) {
		FeedEventDetail feedEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			feedEvent = new FeedEventDetail();
			feedEvent.setFeedEventDate(feedEventMapper.getDeriveFeedEventDate());
			feedEvent.setWeightInKgs(feedEventMapper.getDeriveWeightInKgs());
			feedEvent.setRemarks(feedEventMapper.getRemarks());
			feedEvent.setFeedEventId(feedEventMapper.getDeriveFeedEventId());
			feedEvent.setSiloId(feedEventMapper.getDeriveSilo());
			feedEvent.setGroupEventId(feedEventMapper.getDeriveGroupEventId());
			feedEvent.setFeedEventTypeId(feedEventMapper.getDeriveFeedEventType());
			feedEvent.setUserUpdated(processDTO.getUserName());
			feedEvent.setFeedMill(feedEventMapper.getFeedMill());
			feedEvent.setFeedCost(feedEventMapper.getDeriveFeedCost());
		} catch (Exception e) {
			logger.error("Exception in FeedDetailEventHandler.populateFeedEventfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(feedEventMapper, errList);
		}
		return feedEvent;
	}
	
	
	private PigTraxEventMaster populateEventMaster(FeedDetailEventMapper mapper, Integer generatedKey, ProcessDTO processDTO) {
		PigTraxEventMaster eventMaster = null;
		if (generatedKey != null && generatedKey > 0) {
			eventMaster = new PigTraxEventMaster();
			eventMaster.setEventTime(new Date());
			eventMaster.setFeedEventId(generatedKey);
			eventMaster.setUserUpdated(processDTO.getUserName());
		}
		return eventMaster;
	}

}
