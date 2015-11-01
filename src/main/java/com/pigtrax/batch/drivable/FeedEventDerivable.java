package com.pigtrax.batch.drivable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.TransportJourneyDaoImpl;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class FeedEventDerivable implements Derivable {

	@Autowired
	private TransportJourneyDaoImpl transportJourneyDaoImpl;

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
			feedEventMapper.setDeriveRationId(Integer.parseInt(feedEventMapper.getRationId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFeedquantitykgs(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedQuantityKGs(Integer.parseInt(feedEventMapper.getFeedQuantityKGs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFeedCost(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedCost(Integer.parseInt(feedEventMapper.getFeedCost()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setInitialFeedEntryDate(final FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveIntialFeedEntryDate(DateUtil.getDateFromString(feedEventMapper.getIntialFeedEntryDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setTransportJourneyId(final FeedEventMapper feedEventMapper) {
		try {
			Map<String, Object> criteriaMap = new HashMap<String, Object>();
			criteriaMap.put("jrnyStartDate", feedEventMapper.getDeriveIntialFeedEntryDate());
			feedEventMapper
					.setDeriveTransPortJourneyId(transportJourneyDaoImpl.getTranportJrnyIdByStartDat(criteriaMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
