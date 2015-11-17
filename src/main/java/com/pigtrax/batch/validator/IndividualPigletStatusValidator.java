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
				validatePigId(individualPigletStatusMapper, errList);
				validatePigInfoId(individualPigletStatusMapper, errList);
				validateCompanyId(individualPigletStatusMapper, errList);
				validateFarrowDate(individualPigletStatusMapper, errList);
				validateFarrowEvent(individualPigletStatusMapper, errList);
				validateWtAtBirth(individualPigletStatusMapper, errList);
				validateWtAtWeaning(individualPigletStatusMapper, errList);
				validateTattooId(individualPigletStatusMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
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
	
	private void validatePigInfoId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getPigId() != null && individualPigletStatusMapper.getPigId().trim().length() > 0 && (individualPigletStatusMapper.getDerivePigInfoId() == null || individualPigletStatusMapper.getDerivePigInfoId() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_NG_EVNT_INVALID_PIGID_CODE, Constants.BREED_NG_EVNT_INVALID_PIGID_MSG, "pigId", false));
		}
	}
	
	private void validateCompanyId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {
		if (individualPigletStatusMapper.getDeriveCompanyId() == null || individualPigletStatusMapper.getDeriveCompanyId() < 0) {
			individualPigletStatusMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}

	private void validateFarrowDate(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(!(individualPigletStatusMapper.getFarrowDate() != null &&  0<individualPigletStatusMapper.getFarrowDate().trim().length()))
		{
			individualPigletStatusMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_FARROW_DATE_CODE, Constants.IND_PIGLET_ERR_FARROW_DATE_MSG, "farrowDate", false));
		}
	}
	
	private void validateFarrowEvent(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getFarrowDate() != null &&  0<individualPigletStatusMapper.getFarrowDate().trim().length() && (individualPigletStatusMapper.getDeriveFarrowEventId() == null || individualPigletStatusMapper.getDeriveFarrowEventId() < 0)) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_FARROW_EVENT_CODE, Constants.IND_PIGLET_ERR_FARROW_EVENT_MSG, "farrowEvent", false));
		}
	}

	private void validateWtAtBirth(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getDeriveWtAtBirth() == null || individualPigletStatusMapper.getDeriveWtAtBirth() < 0) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_BIRTH_CODE, Constants.IND_PIGLET_ERR_WT_BIRTH_MSG, "wtAtBirth", false));
		}
	}
	
	private void validateWtAtWeaning(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		if(individualPigletStatusMapper.getDeriveWtAtWeaning() == null || individualPigletStatusMapper.getDeriveWtAtWeaning() < 0) {
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_WT_WEANING_CODE, Constants.IND_PIGLET_ERR_WT_WEANING_MSG, "wtAtWeaning", false));
		}
	}
	
	private void validateTattooId(final IndividualPigletStatusMapper individualPigletStatusMapper, List<ErrorBean> errList) {	
		boolean flag = individualPigletDao.checkIfExists(individualPigletStatusMapper.getTattooId(), individualPigletStatusMapper.getDeriveCompanyId());
		if(flag)
		{
			individualPigletStatusMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_CODE, Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_MSG, "tattooId", false));
		}
	}
}
