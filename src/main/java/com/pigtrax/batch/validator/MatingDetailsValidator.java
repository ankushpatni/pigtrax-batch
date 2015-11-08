package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.MatingDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class MatingDetailsValidator extends AbstractValidator {
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	PregnancyInfoDao pregnancyInfoDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		MatingDetailsMapper matingDetailsMapper = null;
		for (Mapper mapper : list) {
			matingDetailsMapper = (MatingDetailsMapper) mapper;
			if (matingDetailsMapper.isRecovrableErrors() == null || matingDetailsMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				
				validatePigId(matingDetailsMapper, errList);
				validatePigInfoId(matingDetailsMapper, errList);
				validatePigGender(matingDetailsMapper, errList);
				validateCompanyId(matingDetailsMapper, errList);				
				validateEmployeeGroupId(matingDetailsMapper, errList);
				validateMateQuality(matingDetailsMapper, errList);
				validateMatingRecord(matingDetailsMapper, errList);
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	
	private void validatePigId(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {	
		if(matingDetailsMapper.getPigId() == null) {
			matingDetailsMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	private void validatePigInfoId(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {	
		if(matingDetailsMapper.getDerivePigInfoId() == null || matingDetailsMapper.getDerivePigInfoId() < 0) {
			matingDetailsMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_NG_EVNT_INVALID_PIGID_CODE, Constants.BREED_NG_EVNT_INVALID_PIGID_MSG, "pigId", false));
		}
	}
	
	private void validatePigGender(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {	
		if(matingDetailsMapper.getDerivePigInfoId() != null)			
		{
			if(!pigInfoDao.isPigASow(matingDetailsMapper.getDerivePigInfoId()))
			{
				matingDetailsMapper.setRecovrableErrors(false); 
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_EVNT_PIG_NOTA_SOW_CODE, Constants.BREED_EVNT_PIG_NOTA_SOW_MSG, "pigId", false));
			}
		}
	}
	
	private void validateCompanyId(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {
		if (matingDetailsMapper.getDeriveCompanyId() == null || matingDetailsMapper.getDeriveCompanyId() < 0) {
			matingDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_NG_EVNT_INVALID_COMPANY_CODE, Constants.BREED_NG_EVNT_INVALID_COMPANY_MSG, "companyId", false));
		}
	}
	
	
	private void validateEmployeeGroupId(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {
		if (matingDetailsMapper.getEmployeeGroup() != null && !Constants.BLANK_STRING.equals(matingDetailsMapper.getEmployeeGroup().trim()) && matingDetailsMapper.getDeriveEmployeeGroupId() == null ) {
			matingDetailsMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_EMP_GRP, Constants.FRW_EVNT_ERR_EMP_GRP_MSG, "employeeGroup", true));
		}

	}
	
	private void validateMateQuality(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {
		if (matingDetailsMapper.getMateQuality() != null && !Constants.BLANK_STRING.equals(matingDetailsMapper.getMateQuality().trim())) {
			try{
				int mateQuality = Integer.parseInt(matingDetailsMapper.getMateQuality());
				if(mateQuality >= 1 && mateQuality <= 3)
					matingDetailsMapper.setDeriveMateQuality(mateQuality);
				else
				{
					matingDetailsMapper.setRecovrableErrors(true);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_INVALID_MATEQUALITY_CODE, Constants.MATING_ERR_INVALID_MATEQUALITY_MSG, "mateQuality", true));
				}
			}			
			catch(NumberFormatException nFEx)
			{
				matingDetailsMapper.setRecovrableErrors(true);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_INVALID_MATEQUALITY_CODE, Constants.MATING_ERR_INVALID_MATEQUALITY_MSG, "mateQuality", true));
			}
		}
	}
	
	private void validateMatingRecord(final MatingDetailsMapper matingDetailsMapper, List<ErrorBean> errList) {
		if (matingDetailsMapper.getDeriveMatingDate() != null) {
			
			DateTime recordMatingDate = new DateTime(matingDetailsMapper.getDeriveMatingDate());
			
			Integer lastBreedingEventId = breedingEventDao.getLatestServiceEventId(matingDetailsMapper.getDerivePigInfoId());
			if(lastBreedingEventId == null)
			{
				matingDetailsMapper.setRecovrableErrors(true);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_NO_SERVICE_RECORD_CODE, Constants.MATING_ERR_NO_SERVICE_RECORD_MSG, "pigId", true));
			} 
			else
			{
				Date serviceStartDate = breedingEventDao.getServiceStartDate(lastBreedingEventId);
				if(serviceStartDate != null)
				{
					DateTime serviceStartDateTime = new DateTime(serviceStartDate);
					boolean pregnancyEventFlag = pregnancyInfoDao.checkIfPregnancyEventExist(lastBreedingEventId, 1, 1);
					
					int durationDays = Days.daysBetween(serviceStartDateTime, recordMatingDate).getDays();
					
					
					 if(recordMatingDate.toLocalDate().equals(serviceStartDateTime.toLocalDate()))
					  {
						 matingDetailsMapper.setRecovrableErrors(true);
						 errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_DUPLICATE_MATINGDATE_CODE, Constants.MATING_ERR_DUPLICATE_MATINGDATE_MSG, "pigId", true));
					  }
					 else if(recordMatingDate.toLocalDate().isBefore(serviceStartDateTime.toLocalDate()))
					  {
						 if(pregnancyEventFlag)
						 {
							 matingDetailsMapper.setRecovrableErrors(true);
							 errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_EARLY_DATE_CODE, Constants.MATING_ERR_EARLY_DATE_MSG, "pigId", true));
						 }
						 else
							 matingDetailsMapper.setUpdateServiceStartDate(true);
					  }	
					 else if(durationDays > 130)
					 {
						 matingDetailsMapper.setRecovrableErrors(true);
						 errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_NEXT_SERVICE_CODE, Constants.MATING_ERR_NEXT_SERVICE_MSG, "pigId", true));
					 }
					 else if(durationDays > 18 && durationDays < 130 )
					 {
						 if(pregnancyEventFlag)
						 {
							 matingDetailsMapper.setRecovrableErrors(true);
							 errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_PREG_RECORD_CODE, Constants.MATING_ERR_PREG_RECORD_MSG, "pigId", true));
						 }
						 else
							 matingDetailsMapper.setUpdateServiceStartDate(true);
					 }
					 else if(durationDays > 5 && durationDays < 18)
					 {
						 matingDetailsMapper.setRecovrableErrors(true);
						 errList.add(ErrorBeanUtil.populateErrorBean(Constants.MATING_ERR_INVALID_WINDOW_CODE, Constants.MATING_ERR_INVALID_WINDOW_MSG, "pigId", true));
					 }
						 
				}	
				else
					matingDetailsMapper.setUpdateServiceStartDate(true);
			}
		}

	}
	
}
