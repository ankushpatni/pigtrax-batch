package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.config.PigletStatusEventType;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.PigletStatusInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class PigletStatusInfoValidator extends AbstractValidator {
	
	@Autowired
	PigInfoDao pigInfoDao;
	
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
				if(!pigletStatusInfoMapper.isEmpty())
				{
					validatePigId(pigletStatusInfoMapper, errList);
					validatePigInfoId(pigletStatusInfoMapper, errList);
					validatePigGender(pigletStatusInfoMapper, errList);
					validateCompanyId(pigletStatusInfoMapper, errList);
					validateWeanDate(pigletStatusInfoMapper, errList);	
					validateTransferDate(pigletStatusInfoMapper, errList);
					validateMortalityDate(pigletStatusInfoMapper, errList);
					//validateFarrowDate(pigletStatusInfoMapper, errList);
					validateFarrowEvent(pigletStatusInfoMapper, errList);
					validateSowCondition(pigletStatusInfoMapper, errList);
					validateFosterPig(pigletStatusInfoMapper, errList);
					validateFosterFarrowEvent(pigletStatusInfoMapper, errList);
					validateGroupEventId(pigletStatusInfoMapper, errList);
					validateMortalityReason(pigletStatusInfoMapper, errList);
					if (errList.size() > 0) {
						errorMap.put(mapper, errList);
					}
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
	
	private void validatePigGender(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDerivePigInfoId() != null)			
		{
			if(!pigInfoDao.isPigASow(pigletStatusInfoMapper.getDerivePigInfoId()))
			{
				pigletStatusInfoMapper.setRecovrableErrors(false); 
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.BREED_EVNT_PIG_NOTA_SOW_CODE, Constants.BREED_EVNT_PIG_NOTA_SOW_MSG, "pigId", false));
			}
		}
	}
	
	private void validateCompanyId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveCompanyId() == null || pigletStatusInfoMapper.getDeriveCompanyId() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}

	private void validateFarrowDate(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getFarrowDate() == null || pigletStatusInfoMapper.getDeriveFarrowDate() == null )
		{
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIGLETSTATUS_INVALID_FARROWDATE_CODE, Constants.PIGLETSTATUS_INVALID_FARROWDATE_MSG, "farrowDate", false));
		}
	}
	
	private void validateWeanDate(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode() && 
				pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum()  > 0)
		{
			if(pigletStatusInfoMapper.getEventDate() == null || pigletStatusInfoMapper.getDeriveEventDate() == null)
			{
				pigletStatusInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIGLETSTATUS_INVALID_WEAN_DATE_CODE, Constants.PIGLETSTATUS_INVALID_WEAN_DATE_MSG, "eventDate", false));
			}			
		}
	}
	
	
	private void validateMortalityDate(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if( pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode() && 
				pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum()  > 0)
		{
			if(pigletStatusInfoMapper.getEventDate() == null || pigletStatusInfoMapper.getDeriveEventDate() == null)
			{
				pigletStatusInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIGLETSTATUS_INVALID_MORTALITY_DATE_CODE, Constants.PIGLETSTATUS_INVALID_MORTALITY_DATE_MSG, "eventDate", false));
			}
			
		}
	}
	
	
	private void validateTransferDate(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode() && 
				pigletStatusInfoMapper.getDerivePigNum() != null && pigletStatusInfoMapper.getDerivePigNum()>0)
		{
			if(pigletStatusInfoMapper.getEventDate() == null || pigletStatusInfoMapper.getDeriveEventDate() == null)
			{
				pigletStatusInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIGLETSTATUS_INVALID_TRANSFERDATE_CODE, Constants.PIGLETSTATUS_INVALID_TRANSFERDATE_MSG, "transferredDate", false));
			}
			
		}
	}
	
	
	private void validateFarrowEvent(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {	
		if(pigletStatusInfoMapper.getDeriveFarrowEventId() == null || pigletStatusInfoMapper.getDeriveFarrowEventId() < 0) {
			pigletStatusInfoMapper.setRecovrableErrors(false); 
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FRW_EVENT_SERVICE_NOT_FOUND_CODE, Constants.ERR_FRW_EVENT_SERVICE_NOT_FOUND_MSG, "farrowEvent", false));
		}
	}
	
	private void validateSowCondition(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDeriveSowCondition() != null && ( pigletStatusInfoMapper.getDeriveSowCondition() < 0 || pigletStatusInfoMapper.getDeriveSowCondition() > 5) ) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.FRW_EVNT_ERR_SOW_CNDTN_VAL_MSG, "sowCondition", false));
		}
	}
	
	private void validateFosterPig(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode() && 
				pigletStatusInfoMapper.getTransferredToPig() != null && pigletStatusInfoMapper.getTransferredToPig().trim().length() > 0 && 
				(pigletStatusInfoMapper.getDeriveTransferredPigInfoId() == null || pigletStatusInfoMapper.getDeriveTransferredPigInfoId() < 0)) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FOSTER_PIG_NOT_FOUND_CODE, Constants.ERR_FOSTER_PIG_NOT_FOUND_MSG, "transferredToPig", false));
		}
	}
	
	private void validateFosterFarrowEvent(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode() && 
				(pigletStatusInfoMapper.getDeriveFosterFarrowEventId() == null || pigletStatusInfoMapper.getDeriveFosterFarrowEventId() < 0)) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_FOSTER_FARROW_NOT_FOUND_CODE, Constants.ERR_FOSTER_FARROW_NOT_FOUND_MSG, "deriveFosterFarrowEventId", false));
		}
	}
	
	private void validateGroupEventId(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode() &&  
				pigletStatusInfoMapper.getWeanGroupEventId() != null
				&& pigletStatusInfoMapper.getWeanGroupEventId().trim().length()>0 && pigletStatusInfoMapper.getDeriveGroupEventId() == null ) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_WEAN_GROUPID_NOT_FOUND_CODE, Constants.ERR_WEAN_GROUPID_NOT_FOUND_MSG, "weanGroupEventId", false));
		}
	}
	private void validateMortalityReason(final PigletStatusInfoMapper pigletStatusInfoMapper, List<ErrorBean> errList) {
		if (pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() != null &&  pigletStatusInfoMapper.getDerivePigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode() && 
				pigletStatusInfoMapper.getMortalityReason() != null && pigletStatusInfoMapper.getMortalityReason().trim().length() > 0  && 
				(pigletStatusInfoMapper.getDeriveMortalityReasonId() == null ||  pigletStatusInfoMapper.getDeriveMortalityReasonId()<0)) {
			pigletStatusInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.PIGLETSTATUS_INVALID_MORTALITY_CODE, Constants.PIGLETSTATUS_INVALID_MORTALITY_MSG, "mortalityReason", false));
		}
	}
 
}
