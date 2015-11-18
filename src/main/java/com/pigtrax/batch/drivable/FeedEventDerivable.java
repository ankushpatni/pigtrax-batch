package com.pigtrax.batch.drivable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.MasterRationDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class FeedEventDerivable implements Derivable {

	@Autowired
	private TransportJourneyDao transportJourneyDao;
	
	@Autowired
	MasterRationDao rationDao;

	@Override
	public void derive(List<Mapper> list, ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				FeedEventMapper feedEventMapper = (FeedEventMapper) mapper;
				setFeedquantitykgs(feedEventMapper);
				setFeedCost(feedEventMapper);
				setInitialFeedEntryDate(feedEventMapper);
				setTransportJourneyId(feedEventMapper);
				setRationId(feedEventMapper);
			}
		}
	}

	private void setRationId(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveRationId(rationDao.getPKRationId(feedEventMapper.getRationId()));
		} catch (Exception e) {
			feedEventMapper.setDeriveRationId(null);
		}
	}

	private void setFeedquantitykgs(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedQuantityKGs(Double.parseDouble(feedEventMapper.getFeedQuantityKGs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFeedCost(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedCost(Double.parseDouble(feedEventMapper.getFeedCost()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setInitialFeedEntryDate(final FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveIntialFeedEntryDate(DateUtil.getDateFromString(feedEventMapper.getFeedEntryDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setTransportJourneyId(final FeedEventMapper feedEventMapper) {
		try {
			Map<String, Object> criteriaMap = new HashMap<String, Object>();
			criteriaMap.put("jrnyStartDate", feedEventMapper.getDeriveIntialFeedEntryDate());
			feedEventMapper
					.setDeriveTransPortJourneyId(transportJourneyDao.getTranportJrnyIdByStartDat(criteriaMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
