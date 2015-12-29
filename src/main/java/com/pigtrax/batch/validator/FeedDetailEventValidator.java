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
import com.pigtrax.batch.mapper.FeedDetailEventMapper;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class FeedDetailEventValidator extends AbstractValidator {

	@Override
	public Map<Mapper, List<ErrorBean>> validate(List<Mapper> list, ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list,
			Map<Mapper, List<ErrorBean>> errorMap) {
		FeedDetailEventMapper feedEventMapper = null;
		for (Mapper mapper : list) {
			feedEventMapper = (FeedDetailEventMapper) mapper;
			if (feedEventMapper.isRecovrableErrors() == null || feedEventMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateCompanyId(feedEventMapper, errList);
				validatePremiseId(feedEventMapper, errList);
				validateFeedEventId(feedEventMapper, errList);
				validateGroupEventId(feedEventMapper, errList);
				validateFeedEventDate(feedEventMapper, errList);
				valdiateFeedEventType(feedEventMapper, errList);
				validateSilo(feedEventMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}

	private void validateCompanyId(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveCompanyId() == null || feedEventMapper.getDeriveCompanyId() < 0) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_COMPANYID_CODE,
					Constants.ENTRY_EVENT_INVALID_COMPANYID_MSG, "companyId", false));
		}
	}
	
	private void validatePremiseId(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDerivePremiseId() == null || feedEventMapper.getDerivePremiseId() < 0) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_PREMISEID_CODE,
					Constants.ENTRY_EVENT_INVALID_PREMISEID_MSG, "farmName", false));
		}
	}
	
	private void validateFeedEventId(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveFeedEventId() == null || feedEventMapper.getDeriveFeedEventId() < 0) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVENT_DETAIL_INVALID_ID_CODE,
					Constants.FEED_EVENT_DETAIL_INVALID_ID_MSG, "ticketNumber", false));
		}
	}
	
	private void validateGroupEventId(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveGroupEventId() == null || feedEventMapper.getDeriveGroupEventId() < 0) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVENT_DETAIL_INVALID_GRPID_CODE,
					Constants.FEED_EVENT_DETAIL_INVALID_GRPID_MSG, "groupEventId", false));
		}
	}
	
	
	private void validateFeedEventDate(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveFeedEventDate() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVENT_DETAIL_INVALID_DATE_CODE,
					Constants.FEED_EVENT_DETAIL_INVALID_DATE_MSG, "feedEventDate", false));
		}
	}
	
	private void valdiateFeedEventType(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveFeedEventType() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVENT_DETAIL_INVALID_EVT_TYPE_CODE,
					Constants.FEED_EVENT_DETAIL_INVALID_EVT_TYPE_MSG, "feedEventType", false));
		}
	}
	
	private void validateSilo(final FeedDetailEventMapper feedEventMapper, List<ErrorBean> errList) {
		if (feedEventMapper.getDeriveSilo() == null) {
			feedEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FEED_EVENT_DETAIL_INVALID_SILO_CODE,
					Constants.FEED_EVENT_DETAIL_INVALID_SILO_MSG, "silo", false));
		}
	}


}
