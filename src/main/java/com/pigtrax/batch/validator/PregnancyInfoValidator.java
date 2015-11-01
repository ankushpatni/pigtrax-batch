package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
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
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
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
				validatePigId(pregnancyInfoMapper, errList);
				validatePigInfoId(pregnancyInfoMapper, errList);
				validateCompanyId(pregnancyInfoMapper, errList);
				validateServiceDate(pregnancyInfoMapper, errList);
				validateBreedingEvent(pregnancyInfoMapper, errList);
				validateExamDate(pregnancyInfoMapper, errList);
				validateResultDate(pregnancyInfoMapper, errList);
				validatePregnancyEventEntryExist(pregnancyInfoMapper, errList);
				validatePregnacyEventTypeId(pregnancyInfoMapper, errList);
				validatePregnacyExamResultTypeId(pregnancyInfoMapper, errList);
				validateSowCondition(pregnancyInfoMapper, errList);
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
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
	
	private void validatePigInfoId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDerivePigInfoId() == null || pregnancyInfoMapper.getDerivePigInfoId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	
	private void validateCompanyId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDeriveCompanyId() == null || pregnancyInfoMapper.getDeriveCompanyId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}

	private void validateServiceDate(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getServiceDate() == null)
		{
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "serviceDate", false));
		}
	}
	
	private void validateBreedingEvent(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDeriveBreedingEventId() == null || pregnancyInfoMapper.getDeriveBreedingEventId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE, Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG, "serviceDate", false));
		}
	}

	
	private void validateExamDate(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if(pregnancyInfoMapper.getDeriveExamDate() != null ) {
			Date serviceDate = DateUtil.getDateFromString(pregnancyInfoMapper.getServiceDate());
			Integer eventTypeId = pregnancyInfoMapper.getDerivePregnancyEventTypeId();
			
			if (serviceDate != null) {
				long diff = pregnancyInfoMapper.getDeriveExamDate().getTime() - serviceDate.getTime();
				if (eventTypeId == 1 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 18 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >60) {
					pregnancyInfoMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREGNANCY_EVENT_DATE_CODE, Constants.ERR_PREGNANCY_EVENT_DATE_MSG,
							"examDate", false));
				}
			}
		}
	}
	
	
	private void validateResultDate(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDeriveResultDate() == null) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "resultDate", false));
		} else {
			Date serviceDate = DateUtil.getDateFromString(pregnancyInfoMapper.getServiceDate());
			Integer eventTypeId = pregnancyInfoMapper.getDerivePregnancyEventTypeId();
			
			if (serviceDate != null) {
				long diff = pregnancyInfoMapper.getDeriveResultDate().getTime() - serviceDate.getTime();
				if (eventTypeId == 1 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 18 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 60) {
					pregnancyInfoMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREGNANCY_EVENT_DATE_CODE, Constants.ERR_PREGNANCY_EVENT_DATE_MSG,
							"resultDate", false));
				}
				else if (eventTypeId == 2 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 18 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >110) {
					pregnancyInfoMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_ABORTION_DATE_CODE, Constants.ERR_ABORTION_DATE_MSG,
							"resultDate", false));
				}
				else if (eventTypeId == 3 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 105 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 125) {
					pregnancyInfoMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_NOT_IN_PIG_DATE_CODE, Constants.ERR_NOT_IN_PIG_DATE_MSG,
							"resultDate", false));
				}
			}
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
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pregnancyEventType", false));
		}
	}
	
	private void validatePregnacyExamResultTypeId(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {	
		if(pregnancyInfoMapper.getDerivePregnancyExamResultTypeId() == null || pregnancyInfoMapper.getDerivePregnancyExamResultTypeId() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pregnancyExamResultType", false));
		}
	}
	
	private void validateSowCondition(final PregnancyInfoMapper pregnancyInfoMapper, List<ErrorBean> errList) {
		if (pregnancyInfoMapper.getDeriveSowCondition() == null || pregnancyInfoMapper.getDeriveSowCondition() < 0) {
			pregnancyInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "sowCondition", false));
		}
	}

}
