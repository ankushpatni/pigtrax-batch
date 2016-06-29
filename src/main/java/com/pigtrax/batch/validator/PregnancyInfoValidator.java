package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.BreedingEventMapper;
import com.pigtrax.batch.mapper.MatingDetailsMapper;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.DateUtil;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class PregnancyInfoValidator extends AbstractValidator {	

	@Autowired
	PregnancyInfoDao pregnancyInfoDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new LinkedHashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		PregnancyInfoMapper pregnancyInfoMapper = null;
		for (Mapper mapper : list) {
			pregnancyInfoMapper = (PregnancyInfoMapper) mapper;
			if (pregnancyInfoMapper.isRecovrableErrors() == null || pregnancyInfoMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				if(!pregnancyInfoMapper.isEmpty())
				{
				
					validateCompanyId(pregnancyInfoMapper, errList);
					validatePremiseId(pregnancyInfoMapper, errList);
					validatePigId(pregnancyInfoMapper, errList);
					validatePigInfoId(pregnancyInfoMapper, errList);
					validatePigGender(pregnancyInfoMapper, errList);
					validatePregnacyEventTypeId(pregnancyInfoMapper, errList);
					validateBreedingEvent(pregnancyInfoMapper, errList);
					validateResultDate(pregnancyInfoMapper, errList);
					
					validatePregnacyExamResultTypeId(pregnancyInfoMapper, errList);
					validatePregnancyEventEntryExist(pregnancyInfoMapper, errList);				
					validateSowCondition(pregnancyInfoMapper, errList);
					
					if (errList.size() > 0) {
						errorMap.put(mapper, errList);
					}
				}
			}
		}
		return errorMap;
	}
	
	
	private void validatePigId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getPigId() == null) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	
	private void validatePremiseId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDerivePremiseId() == null || pregnancyInfoMapper.getDerivePremiseId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.INVALID_PREMISEID_CODE,
					Constants.INVALID_PREMISEID_MSG, "farmName", false));
		}
	}
	
	private void validatePigInfoId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDerivePigInfoId() == null || pregnancyInfoMapper.getDerivePigInfoId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.INVALID_PIGID_CODE, Constants.INVALID_PIGID_MSG, "pigId", false));
		}
	}
	
	private void validatePigGender(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDerivePigInfoId() != null)			
		{
			if(!pigInfoDao.isPigASow(pregnancyInfoMapper.getDerivePigInfoId()))
			{
				pregnancyInfoMapper.setRecovrableErrors(false); 
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIG_NOTA_SOW_CODE, Constants.PIG_NOTA_SOW_MSG, "pigId", false));
			}
		}
	}
	
	private void validateCompanyId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDeriveCompanyId() == null || pregnancyInfoMapper.getDeriveCompanyId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}

	
	private void validateBreedingEvent(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDeriveBreedingEventId() == null || pregnancyInfoMapper.getDeriveBreedingEventId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(true); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE, Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG, "pigId, resultDate", false));
		}
	}

	
	private void validateExamDate(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if(pregnancyInfoMapper.getExamDate() != null && pregnancyInfoMapper.getExamDate().trim().length() > 0 && pregnancyInfoMapper.getDeriveExamDate() == null ) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREGNANCY_EVENT_DATE_CODE, Constants.ERR_PREGNANCY_EVENT_DATE_MSG,"examDate", false));
		}
		
	}
	
	
	private void validateResultDate(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDeriveResultDate() == null) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREGNANCY_EVENTRESULT_DATE, Constants.ERR_PREGNANCY_EVENTRESULT_DATE_MSG, "resultDate", false));
		} 
	}
	
	
	private void validatePregnancyEventEntryExist(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		
		if(pregnancyInfoMapper.getDeriveBreedingEventId() != null && pregnancyInfoMapper.getDerivePregnancyEventTypeId() != null && pregnancyInfoMapper.getDerivePregnancyExamResultTypeId() != null)
		{
			boolean flag = pregnancyInfoDao.checkIfPregnancyEventExist(pregnancyInfoMapper.getDeriveBreedingEventId(),
					pregnancyInfoMapper.getDerivePregnancyEventTypeId(), pregnancyInfoMapper.getDerivePregnancyExamResultTypeId());
			
			if (flag) {
				pregnancyInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREGNANCY_EVENT_DUPLICATE_CODE, Constants.ERR_PREGNANCY_EVENT_DUPLICATE_MSG, "", false));
			} 
		}
	}
	
	private void validatePregnacyEventTypeId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDerivePregnancyEventTypeId() == null || pregnancyInfoMapper.getDerivePregnancyEventTypeId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.PREG_EVENT_INVALID_EVENT_TYPE_CODE, Constants.PREG_EVENT_INVALID_EVENT_TYPE_MSG, "pregnancyEventType", false));
		}
	}
	
	private void validatePregnacyExamResultTypeId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getPregnancyExamResultType() != null && pregnancyInfoMapper.getPregnancyExamResultType().trim().length() >0 &&  (pregnancyInfoMapper.getDerivePregnancyExamResultTypeId() == null || pregnancyInfoMapper.getDerivePregnancyExamResultTypeId() < 0)) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.PREG_EVENT_INVALID_RESULT_TYPE_CODE, Constants.PREG_EVENT_INVALID_RESULT_TYPE_MSG, "pregnancyExamResultType", false));
		}
	}
	
	private void validateSowCondition(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getSowCondition() != null && pregnancyInfoMapper.getSowCondition().trim().length() > 0 && (pregnancyInfoMapper.getDeriveSowCondition() == null || pregnancyInfoMapper.getDeriveSowCondition() < 0 || pregnancyInfoMapper.getDeriveSowCondition() > 5)) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "sowCondition", false));
		}
	}

}
