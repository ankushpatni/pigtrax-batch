package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class FeedEventValidator extends AbstractValidator {

	@Override
	public Map<Mapper, List<ErrorBean>> validate(List<Mapper> list, ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list,
			Map<Mapper, List<ErrorBean>> errorMap) {
		FeedEventMapper feedEventMapper = null;
		for (Mapper mapper : list) {
			feedEventMapper = (FeedEventMapper) mapper;
			if (feedEventMapper.isRecovrableErrors() == null || feedEventMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateRationId(feedEventMapper, errList);
				validateTicketNumber(feedEventMapper, errList);
				validateInitialFeedEntryDate(feedEventMapper, errList);
				validateTransportJourneyId(feedEventMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}

	private void validateRationId(FeedEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveRationId() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVNT_ERR_RATIONID,
					Constants.FEED_EVNT_ERR_RATIONID_MSG, "rationId", false));
		}

	}
	
	private void validateTicketNumber(FeedEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getTicketNumber() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVNT_ERR_TICKETID,
					Constants.FEED_EVNT_ERR_TICKETID_MSG, "TicketNumber", false));
		}

	}
	
	private void validateInitialFeedEntryDate(FeedEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveIntialFeedEntryDate() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVNT_ERR_INI_ENTRY_DATE,
					Constants.FEED_EVNT_ERR_INI_ENTRY_DATE_MSG, "initialFeedEntryDate", false));
		}

	}
	
	private void validateTransportJourneyId(FeedEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveTransPortJourneyId() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVNT_ERR_TRNS_JRNY_ID,
					Constants.FEED_EVNT_ERR_TRNS_JRNY_ID_MSG, "transportJourneyDate", false));
		}

	}

}
