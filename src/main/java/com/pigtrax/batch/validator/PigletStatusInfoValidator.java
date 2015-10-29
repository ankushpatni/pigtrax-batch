package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.DateUtil;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class PigletStatusInfoValidator extends AbstractValidator {
	
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		PigletStatusInfoMapper pigletStatusInfoMapper = null;
		for (Mapper mapper : list) {
			pigletStatusInfoMapper = (PigletStatusInfoMapper) mapper;
			if (pigletStatusInfoMapper.isRecovrableErrors() == null || pigletStatusInfoMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validatePigId(pigletStatusInfoMapper, errList);
				validatePigInfoId(pigletStatusInfoMapper, errList);
				validateCompanyId(pigletStatusInfoMapper, errList);
				validateFarrowDate(pigletStatusInfoMapper, errList);
				validateFarrowEvent(pigletStatusInfoMapper, errList);
				validateSowCondition(pigletStatusInfoMapper, errList);
				validateFosterPig(pigletStatusInfoMapper, errList);
				validateFosterFarrowEvent(pigletStatusInfoMapper, errList);
				validateGroupEventId(pigletStatusInfoMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	
	private void validatePigId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getPigId() == null) {
			pigletStatusInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	
	private void validatePigInfoId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDerivePigInfoId() == null || pigletStatusInfoMapper.getDerivePigInfoId() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "pigId", false));
		}
	}
	
	private void validateCompanyId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveCompanyId() == null || pigletStatusInfoMapper.getDeriveCompanyId() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}

	private void validateFarrowDate(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getFarrowDate() == null)
		{
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "farrowDate", false));
		}
	}
	
	private void validateFarrowEvent(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDeriveFarrowEventId() == null || pigletStatusInfoMapper.getDeriveFarrowEventId() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_CODE, Constants.ERR_PREG_EVENT_SERVICE_NOT_FOUND_MSG, "farrowEvent", false));
		}
	}

	
	
	
	private void validateSowCondition(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveSowCondition() == null || pigletStatusInfoMapper.getDeriveSowCondition() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "sowCondition", false));
		}
	}
	
	private void validateFosterPig(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0 && (pigletStatusInfoMapper.getDeriveTransferredPigInfoId() == null || pigletStatusInfoMapper.getDeriveTransferredPigInfoId() < 0)) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FOSTER_PIG_NOT_FOUND_CODE, Constants.ERR_FOSTER_PIG_NOT_FOUND_MSG, "transferredToPig", false));
		}
	}
	
	private void validateFosterFarrowEvent(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveTransferPigNum() != null && pigletStatusInfoMapper.getDeriveTransferPigNum() > 0 && (pigletStatusInfoMapper.getDeriveFosterFarrowEventId() == null || pigletStatusInfoMapper.getDeriveFosterFarrowEventId() < 0)) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FOSTER_FARROW_NOT_FOUND_CODE, Constants.ERR_FOSTER_FARROW_NOT_FOUND_MSG, "deriveFosterFarrowEventId", false));
		}
	}
	
	private void validateGroupEventId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveWeanPigNum() != null && pigletStatusInfoMapper.getDeriveWeanPigNum() > 0 && pigletStatusInfoMapper.getWeanGroupEventId() != null && pigletStatusInfoMapper.getDeriveGroupEventId() == null ) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_WEAN_GROUPID_NOT_FOUND_CODE, Constants.ERR_WEAN_GROUPID_NOT_FOUND_MSG, "weanGroupEventId", false));
		}
	}
 
}
