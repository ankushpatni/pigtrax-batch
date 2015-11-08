package com.pigtrax.batch.validator;

import java.math.BigDecimal;
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
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.SalesEventDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;
@Component
public class SalesEventDetailsValidator extends AbstractValidator{
	
	
	@Autowired
	GroupEventDao groupEventDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}
	
	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		SalesEventDetailsMapper salesEventDetailsMapper = null;
		
		for (Mapper mapper : list) {
			salesEventDetailsMapper = (SalesEventDetailsMapper) mapper;
			if (salesEventDetailsMapper.isRecovrableErrors() == null || salesEventDetailsMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				
				validateCompanyId( salesEventDetailsMapper, errList);
				validateNumberOfPigs(salesEventDetailsMapper, errList);
				validateRemovalDateTime(salesEventDetailsMapper, errList);
				validatePigInfoAndGroupEvent(salesEventDetailsMapper, errList);
				validateWeightInKg(salesEventDetailsMapper, errList);
				validateRemovalEventType(salesEventDetailsMapper, errList);
				validateRevenus(salesEventDetailsMapper,errList);
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	private void validateCompanyId(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveCompanyId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDeriveCompanyId()) ||
				salesEventDetailsMapper.getDeriveCompanyId() <1) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_COMPANY_PRESENT_CODE, Constants.REM_COMPANY_PRESENT_MSG, "CompanyId", false));
		}
	}

	private void validateRemovalDateTime(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveSalesDateTime() == null) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_PRESENT_CODE, Constants.REM_REMOVAL_DATE_PRESENT_MSG, "RemovalDateTime", false));
		}
	}
	
	
	private void validateNumberOfPigs(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveNumberOfPigs() == null || salesEventDetailsMapper.getDeriveNumberOfPigs() <1) {
			salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MSG, "NumberOfPigs", false));
		}
		
		GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(salesEventDetailsMapper.getDeriveGroupEventId(), salesEventDetailsMapper.getDeriveCompanyId());
		if(null != groupEvent && salesEventDetailsMapper.getDeriveNumberOfPigs()>groupEvent.getCurrentInventory())
		{
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_MSG, "NumberOfPigs", false));

		}
	}
	
	private void validateWeightInKg(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveWeightInKgs() == null || salesEventDetailsMapper.getDeriveWeightInKgs().intValue() == 0) {
			salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_CODE, Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_MSG, "WeightInKg", false));
					}
	}	
	
	private void validateRemovalEventType(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveRemovalEventTypeId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDeriveRemovalEventTypeId())) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REM_EVENT_TYPE_PRESENT_CODE, Constants.REM_REM_EVENT_TYPE_PRESENT_MSG, "RemovalEventType", false));
		}
	}
	
	private void validatePigInfoAndGroupEvent(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if ((salesEventDetailsMapper.getDeriveGroupEventId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDeriveGroupEventId()))
				&& (salesEventDetailsMapper.getDerivePigInfoId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDerivePigInfoId()))) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "RemovalEventType", false));
		}
		else if(!(salesEventDetailsMapper.getDeriveGroupEventId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDeriveGroupEventId()))
				&& !(salesEventDetailsMapper.getDerivePigInfoId() == null || StringUtils.isEmpty(salesEventDetailsMapper.getDerivePigInfoId()))) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE, Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG, "RemovalEventType", false));
		}
	}
	
	private void validateRevenus(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getRevenueUsd() != null || !StringUtils.isEmpty(salesEventDetailsMapper.getRevenueUsd())) {
			try
			{
				Double revenus = Double.parseDouble(salesEventDetailsMapper.getRevenueUsd());
				salesEventDetailsMapper.setDeriveRevenueUsd(new BigDecimal(revenus.doubleValue()));
			}
			catch(Exception e)
			{
				salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REM_EVENT_TYPE_PRESENT_CODE, Constants.REM_REM_EVENT_TYPE_PRESENT_MSG, "RemovalEventType", false));
			}
			
		}
	}	

}
