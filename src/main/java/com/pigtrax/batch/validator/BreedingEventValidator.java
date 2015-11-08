package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.BreedingEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class BreedingEventValidator extends AbstractValidator {
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		BreedingEventMapper breedingEventMapper = null;
		for (Mapper mapper : list) {
			breedingEventMapper = (BreedingEventMapper) mapper;
			if (breedingEventMapper.isRecovrableErrors() == null || breedingEventMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				
				validatePigId(breedingEventMapper, errList);
				validatePigInfoId(breedingEventMapper, errList);
				validatePigGender(breedingEventMapper, errList);
				validateCompanyId(breedingEventMapper, errList);
				validateSeriveTypeId(breedingEventMapper, errList);
				validatePenId(breedingEventMapper, errList);
				validateSowCondition(breedingEventMapper, errList);
				validateWeight(breedingEventMapper, errList);
				
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	
	private void validatePigId(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {	
		if(breedingEventMapper.getPigId() == null) {
			breedingEventMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	private void validatePigInfoId(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {	
		if(breedingEventMapper.getDerivePigInfoId() == null || breedingEventMapper.getDerivePigInfoId() < 0) {
			breedingEventMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_NG_EVNT_INVALID_PIGID_CODE, Constants.BREED_NG_EVNT_INVALID_PIGID_MSG, "pigId", false));
		}
	}
	
	private void validatePigGender(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {	
		if(breedingEventMapper.getDerivePigInfoId() != null)			
		{
			if(!pigInfoDao.isPigASow(breedingEventMapper.getDerivePigInfoId()))
			{
				breedingEventMapper.setRecovrableErrors(false); 
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_EVNT_PIG_NOTA_SOW_CODE, Constants.BREED_EVNT_PIG_NOTA_SOW_MSG, "pigId", false));
			}
		}
	}
	
	private void validateCompanyId(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {
		if (breedingEventMapper.getDeriveCompanyId() == null || breedingEventMapper.getDeriveCompanyId() < 0) {
			breedingEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_NG_EVNT_INVALID_COMPANY_CODE, Constants.BREED_NG_EVNT_INVALID_COMPANY_MSG, "companyId", false));
		}
	}
	
	private void validateSeriveTypeId(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {
		if (breedingEventMapper.getServiceType() != null && !breedingEventMapper.getServiceType().equals(Constants.BLANK_STRING) &&  (breedingEventMapper.getDeriveServiceTypeId()== null || breedingEventMapper.getDeriveServiceTypeId() < 0)) {
			breedingEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREEDING_EVNT_ERR_INVALID_SERVICETYPE_CODE, Constants.BREEDING_EVNT_ERR_INVALID_SERVICETYPE_MSG, "serviceTypeId", false));
		}
	}
	
	private void validatePenId(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {
		if (breedingEventMapper.getPen() != null && !breedingEventMapper.getPen().equals(Constants.BLANK_STRING) && (breedingEventMapper.getDerivePenId()== null || breedingEventMapper.getDerivePenId() < 0)) {
			breedingEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREEDING_EVNT_ERR_INVALID_PEN_CODE, Constants.BREEDING_EVNT_ERR_INVALID_PEN_MSG, "pen", false));
		}
	}
	
	private void validateSowCondition(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {
		if (breedingEventMapper.getSowCondition() != null && !Constants.BLANK_STRING.equals(breedingEventMapper.getSowCondition()) && 
				(breedingEventMapper.getDeriveSowCondition() == null || (breedingEventMapper.getDeriveSowCondition() != null&& breedingEventMapper.getDeriveSowCondition() < 0)  ||( breedingEventMapper.getDeriveSowCondition() != null && breedingEventMapper.getDeriveSowCondition() > 5))) {
			breedingEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_EVNT_INVALID_SOW_CONDITION_CODE, Constants.BREED_EVNT_INVALID_SOW_CONDITION_MSG, "sowCondition", false));
		}
		
	}
	
	private void validateWeight(final BreedingEventMapper breedingEventMapper, List<ErrorBean> errList) {
		if (breedingEventMapper.getWeightInKgs() != null && ! Constants.BLANK_STRING.equals(breedingEventMapper.getWeightInKgs())) {
			try{
				double weight = Double.parseDouble(breedingEventMapper.getWeightInKgs());
			}
			catch(NumberFormatException nFEx)
			{
				breedingEventMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "weightInKgs", false));
			}			
		}
	}
}
