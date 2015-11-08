package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class RemovalEventExceptSalesDetailsValidator extends AbstractValidator {
	
	@Autowired
	PregnancyInfoDao pregnancyInfoDao;
	
	@Autowired
	GroupEventDao groupEventDao;	
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}
	
	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = null;
		
		/*"numberOfPigs" smallint NOT NULL,
		"removalDateTime" timestamp NOT NULL,
		"id_PigInfo" integer,
		"id_GroupEvent" integer,
		"weightInKgs" numeric(20,2) NOT NULL,
		"id_RemovalEvent" integer NOT NULL,
		"id_Premise" integer,
		"id_DestPremise" integer,
		"id_TransportJourney" integer,
		"id_MortalityReason" integer,*/
		for (Mapper mapper : list) {
			removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
			if (removalEventExceptSalesDetailsMapper.isRecovrableErrors() == null || removalEventExceptSalesDetailsMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				
				validateCompanyId( removalEventExceptSalesDetailsMapper, errList);
				validateNumberOfPigs(removalEventExceptSalesDetailsMapper, errList);
				validateRemovalDateTime(removalEventExceptSalesDetailsMapper, errList);
				validatePigInfoAndGroupEvent(removalEventExceptSalesDetailsMapper, errList);
				validateWeightInKg(removalEventExceptSalesDetailsMapper, errList);
				validateRemovalEventType(removalEventExceptSalesDetailsMapper, errList);
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	private void validateCompanyId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveCompanyId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveCompanyId()) ||
				removalEventExceptSalesDetailsMapper.getDeriveCompanyId() <1) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_COMPANY_PRESENT_CODE, Constants.REM_COMPANY_PRESENT_MSG, "CompanyId", false));
		}
	}

	private void validateRemovalDateTime(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getRemovalDateTime() == null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_PRESENT_CODE, Constants.REM_REMOVAL_DATE_PRESENT_MSG, "RemovalDateTime", false));
		}
	}
	
	
	private void validateNumberOfPigs(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs() == null || removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs() <1) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MSG, "NumberOfPigs", false));
		}
		
		GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId(), removalEventExceptSalesDetailsMapper.getDeriveCompanyId());
		if(removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs()>groupEvent.getCurrentInventory())
		{
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_MSG, "NumberOfPigs", false));

		}
	}
	
	private void validateWeightInKg(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs() == null || removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs().intValue() == 0) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_CODE, Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_MSG, "WeightInKg", false));
					}
	}	
	
	private void validateRemovalEventType(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getRemovalEventTypeId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getRemovalEventTypeId())) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REM_EVENT_TYPE_PRESENT_CODE, Constants.REM_REM_EVENT_TYPE_PRESENT_MSG, "RemovalEventType", false));
		}
	}
	
	private void validatePigInfoAndGroupEvent(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if ((removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId()))
				&& (removalEventExceptSalesDetailsMapper.getDerivePigInfoId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDerivePigInfoId()))) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "RemovalEventType", false));
		}
		else if(!(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId()))
				&& !(removalEventExceptSalesDetailsMapper.getDerivePigInfoId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDerivePigInfoId()))) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE, Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG, "RemovalEventType", false));
		}
	}
	

}
