package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.FeedEventDetail;
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.core.ProcessDTO;
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
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				FeedDetailEventMapper feedEventMapper = (FeedDetailEventMapper) mapper;
				if (feedEventMapper.isRecovrableErrors() == null || feedEventMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						FeedEventDetail feedEvent = populateFeedEventfnfo(errorMap, feedEventMapper, processDTO);
						
						if (feedEvent != null) {								
							int id = feedEventDetailDao.addFeedEventDetail(feedEvent);							
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

	private FeedEventDetail populateFeedEventfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final FeedDetailEventMapper feedEventMapper, final ProcessDTO processDTO) {
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

}
