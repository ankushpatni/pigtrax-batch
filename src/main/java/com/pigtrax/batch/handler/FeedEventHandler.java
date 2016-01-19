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
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.beans.TransportJourney;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.FeedEventDao;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class FeedEventHandler implements Handler {
	@Autowired
	private FeedEventDao feedEventDaoImpl;

	@Autowired
	private PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;

	private static final Logger logger = Logger.getLogger(FeedEventHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		int journeyId = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				FeedEventMapper feedEventMapper = (FeedEventMapper) mapper;
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
						boolean flag = feedEventDaoImpl.checkIfTicketNumberExists(feedEvent.getTicketNumber(), feedEvent.getPremiseId());
						if(flag)
						{
							errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FEED_DUPLICATE_TKTNUM, Constants.ERR_FEED_DUPLICATE_TKTNUM_MSG, "ticketNumber", false));
							isErrorOccured = true;
						}
						else
						{
							if (feedEvent != null) {								
								int id = feedEventDaoImpl.addFeedEvent(feedEvent);
								PigTraxEventMaster eventMaster = populateEventMaster(feedEventMapper, id, processDTO);
								eventMasterDao.insertEventMaster(eventMaster);
								totalRecordsProcessed = totalRecordsProcessed + 1;
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
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private FeedEvent populateFeedEventfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final FeedEventMapper feedEventMapper, final ProcessDTO processDTO) {
		FeedEvent feedEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			feedEvent = new FeedEvent();
			feedEvent.setFeedContentId(feedEventMapper.getFeedContentId());
			feedEvent.setFeedCost(feedEventMapper.getDeriveFeedCost());
			feedEvent.setFeedMadication(feedEventMapper.getFeedMadication());
			feedEvent.setFeedQuantityKGs(feedEventMapper.getDeriveFeedQuantityKGs());
			feedEvent.setIntialFeedEntryDate(feedEventMapper.getDeriveIntialFeedEntryDate());
			feedEvent.setRationId(feedEventMapper.getDeriveRationId());
			feedEvent.setTicketNumber(feedEventMapper.getTicketNumber());
			//feedEvent.setTransPortJourneyId(feedEventMapper.getDeriveTransPortJourneyId());
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

	private PigTraxEventMaster populateEventMaster(FeedEventMapper mapper, Integer generatedKey, ProcessDTO processDTO) {
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
