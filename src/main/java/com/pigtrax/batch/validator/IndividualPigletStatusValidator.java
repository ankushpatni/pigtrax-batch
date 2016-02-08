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
import com.pigtrax.batch.dao.interfaces.IndividualPigletDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.IndividualPigletStatusMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class IndividualPigletStatusValidator extends AbstractValidator {
	
	@Autowired
	IndividualPigletDao individualPigletDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		IndividualPigletStatusMapper individualPigletStatusMapper = null;
		for (Mapper mapper : list) {
			individualPigletStatusMapper = (IndividualPigletStatusMapper) mapper;
			if (individualPigletStatusMapper.isRecovrableErrors() == null || individualPigletStatusMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				if(!individualPigletStatusMapper.isEmpty())
				{
					validateCompanyId(individualPigletStatusMapper, errList);
					validatePremiseId(individualPigletStatusMapper, errList);
					validatePigId(individualPigletStatusMapper, errList);
					validateLitterId(individualPigletStatusMapper, errList);
					validateWtAtBirth(individualPigletStatusMapper, errList);
					validateWtAtWeaning(individualPigletStatusMapper, errList);
					validateTattooId(individualPigletStatusMapper, errList);
					validateWtAtFirstMonth(individualPigletStatusMapper, errList);
					validateWtAtSecondMonth(individualPigletStatusMapper, errList);
					validateWtAtThirdMonth(individualPigletStatusMapper, errList);
					validateWtAtFourthMonth(individualPigletStatusMapper, errList);
					validateWtAtFifthMonth(individualPigletStatusMapper, errList);
					validateWtAtSixthMonth(individualPigletStatusMapper, errList);
					if (errList.size() > 0) {
						errorMap.put(mapper, errList);
					}
				}
			}
		}
		return errorMap;
	}
	
	
	private void validatePigId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getPigId() == null) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}

	private void validateCompanyId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {
		if (individualPigletStatusMapper.getDeriveCompanyId() == null || individualPigletStatusMapper.getDeriveCompanyId() < 0) {
			individualPigletStatusMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}
	
	private void validatePremiseId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {
		if (individualPigletStatusMapper.getDerivePremiseId() == null || individualPigletStatusMapper.getDerivePremiseId() < 0) {
			individualPigletStatusMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_PREMISEID_CODE,
					Constants.ENTRY_EVENT_INVALID_PREMISEID_MSG, "farmName", false));
		}
	}	

	
	private void validateLitterId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getLitterId() != null && individualPigletStatusMapper.getLitterId().trim().length() > 0 && (individualPigletStatusMapper.getDeriveLitterId() == null)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_LITTERID_CODE, Constants.IND_PIGLET_ERR_LITTERID_MSG, "litterId", false));
		}
	}
	
	private void validateWtAtBirth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtBirth() != null && individualPigletStatusMapper.getWtAtBirth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtBirth() == null || individualPigletStatusMapper.getDeriveWtAtBirth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtBirth", false));
		}
	}
	
	private void validateWtAtWeaning(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtWeaning() != null && individualPigletStatusMapper.getWtAtWeaning().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtWeaning() == null || individualPigletStatusMapper.getDeriveWtAtWeaning() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_WEANING_CODE, Constants.IND_PIGLET_ERR_WT_WEANING_MSG, "wtAtWeaning", false));
		}
	}
	
	private void validateWtAtFirstMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtFirstMonth() != null && individualPigletStatusMapper.getWtAtFirstMonth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtFirstMonth() == null || individualPigletStatusMapper.getDeriveWtAtFirstMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtFirstMonth", false));
		}
	}
	
	
	private void validateWtAtSecondMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtSecondMonth() != null && individualPigletStatusMapper.getWtAtSecondMonth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtSecondMonth() == null || individualPigletStatusMapper.getDeriveWtAtSecondMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtSecondMonth", false));
		} 
	}
	
	private void validateWtAtThirdMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtThirdMonth() != null && individualPigletStatusMapper.getWtAtThirdMonth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtThirdMonth() == null || individualPigletStatusMapper.getDeriveWtAtThirdMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtThirdMonth", false));
		}
	}
	
	private void validateWtAtFourthMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtFourthMonth() != null && individualPigletStatusMapper.getWtAtFourthMonth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtFourthMonth() == null || individualPigletStatusMapper.getDeriveWtAtFourthMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtFourthMonth", false));
		}
	}
	
	private void validateWtAtFifthMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtFifthMonth() != null  && individualPigletStatusMapper.getWtAtFifthMonth().length() > 0&& (individualPigletStatusMapper.getDeriveWtAtFifthMonth() == null || individualPigletStatusMapper.getDeriveWtAtFifthMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtFifthMonth", false));
		}
	}
	
	private void validateWtAtSixthMonth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getWtAtSixthMonth() != null  && individualPigletStatusMapper.getWtAtSixthMonth().trim().length() > 0 && (individualPigletStatusMapper.getDeriveWtAtSixthMonth() == null || individualPigletStatusMapper.getDeriveWtAtSixthMonth() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtSixthMonth", false));
		}
	}
	
	
	private void validateTattooId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getTattooId() != null && individualPigletStatusMapper.getTattooId().trim().length() > 0)
		{
			boolean flag = individualPigletDao.checkIfExists(individualPigletStatusMapper.getTattooId(), individualPigletStatusMapper.getDeriveCompanyId());
			if(flag)
			{
				individualPigletStatusMapper.setRecovrableErrors(false); 
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_CODE, Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_MSG, "tattooId", false));
			}
		}
	}
}
