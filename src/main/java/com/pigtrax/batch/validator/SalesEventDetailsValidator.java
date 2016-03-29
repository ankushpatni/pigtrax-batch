package com.pigtrax.batch.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
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
	
	@Autowired
	PigInfoDao pigInfoDao;
	
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
				
				validateNumberOfPigs(salesEventDetailsMapper, errList);//				
				validatePigInfoAndGroupEvent(salesEventDetailsMapper, errList);//
				validateWeightInKg(salesEventDetailsMapper, errList);//
				validateRevenus(salesEventDetailsMapper,errList);
				validateTicketNumber(salesEventDetailsMapper,errList);
				validateRemovalDateTime(salesEventDetailsMapper, errList);//
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	private void validateTicketNumber(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getTicketNumber() == null || StringUtils.isEmpty(salesEventDetailsMapper.getTicketNumber())) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_TICKET_NUMBER_CODE, Constants.REM_REMOVAL_TICKET_NUMBER_MSG, "TicketNumber", false));
		}
	}
	
	private void validateRemovalDateTime(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveSalesDateTime() == null || salesEventDetailsMapper.getDeriveSalesDateTime() == null) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_PRESENT_CODE, Constants.REM_REMOVAL_DATE_PRESENT_MSG, "salesDateTime", false));
		}
		else
		{
			DateTime removalDate = new DateTime(salesEventDetailsMapper.getDeriveSalesDateTime());
			if(salesEventDetailsMapper.getDeriveGroupEventId() != null && salesEventDetailsMapper.getDeriveGroupEventId() > 0 && salesEventDetailsMapper.getDeriveCompanyId() != null)
			{
				GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(salesEventDetailsMapper.getDeriveGroupEventId(), salesEventDetailsMapper.getDeriveCompanyId());
				if(groupEvent != null)
				{
					DateTime startDate = new DateTime(groupEvent.getGroupStartDateTime());
					if(startDate.isAfter(removalDate))
					{
						salesEventDetailsMapper.setRecovrableErrors(false);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_BEFORE_GROUP_START_CODE, Constants.REM_REMOVAL_DATE_BEFORE_GROUP_START_MSG, "salesDateTime", false));
					}
				}
			}
			else if(salesEventDetailsMapper.getDerivePigInfoId() != null && salesEventDetailsMapper.getDerivePigInfoId() > 0)
			{
				PigInfo pigInfo = pigInfoDao.getPigDetails(salesEventDetailsMapper.getDerivePigInfoId());
				if(pigInfo != null)
				{
					DateTime entryDate = new DateTime(pigInfo.getEntryDate());
					if(entryDate.isAfter(removalDate))
					{
						salesEventDetailsMapper.setRecovrableErrors(false);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_BEFORE_PIG_START_CODE, Constants.REM_REMOVAL_DATE_BEFORE_PIG_START_MSG, "salesDateTime", false));
					}
				}
			}
		}
	}
	
	
	private void validateNumberOfPigs(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDerivePigInfoId() != null && (salesEventDetailsMapper.getDeriveNumberOfPigs() == null || salesEventDetailsMapper.getDeriveNumberOfPigs() <1)) {
			salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MSG, "NumberOfPigs", false));
		}
		if(salesEventDetailsMapper.getDeriveGroupEventId() != null)
		{
			GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(salesEventDetailsMapper.getDeriveGroupEventId(), salesEventDetailsMapper.getDeriveCompanyId());
			if(null != groupEvent && salesEventDetailsMapper.getDeriveNumberOfPigs()>groupEvent.getCurrentInventory())
			{
				salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_MSG, "NumberOfPigs", false));
	
			}
		}
	}
	
	private void validateWeightInKg(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (salesEventDetailsMapper.getDeriveWeightInKgs() == null || salesEventDetailsMapper.getDeriveWeightInKgs().intValue() == 0) {
			salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_CODE, Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_MSG, "WeightInKg", false));
					}
	}	
	
	
	
	private void validatePigInfoAndGroupEvent(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if((salesEventDetailsMapper.getGroupEventId() == null || salesEventDetailsMapper.getGroupEventId().trim().length() == 0) &&
				(salesEventDetailsMapper.getPigInfoId() == null || salesEventDetailsMapper.getPigInfoId().trim().length() == 0))
		{
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_REMOVAL_MISSING_GRP_PIGID_CODE, Constants.ERR_REMOVAL_MISSING_GRP_PIGID_MSG, "pigInfoId, groupEventId", false));
		}
		else if(salesEventDetailsMapper.getDerivePigInfoId() != null && salesEventDetailsMapper.getDeriveGroupEventId() != null) {
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE, Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG, "RemovalEventType", false));
		}
		else if (salesEventDetailsMapper.getGroupEventId() != null && salesEventDetailsMapper.getGroupEventId().trim().length() > 0 && salesEventDetailsMapper.getDeriveGroupEventId() == null){
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REMOVAL_INVALID_GROUPID_CODE, Constants.REMOVAL_INVALID_GROUPID_MSG, "groupEventId", false));
		}
		else if (salesEventDetailsMapper.getPigInfoId() != null && salesEventDetailsMapper.getPigInfoId().trim().length() > 0 && salesEventDetailsMapper.getDerivePigInfoId() == null){
			salesEventDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REMOVAL_INVALID_PIGID_CODE, Constants.REMOVAL_INVALID_PIGID_MSG, "pigInfoId", false));
		}		
	}
	
	private void validateRevenus(final SalesEventDetailsMapper salesEventDetailsMapper, List<ErrorBean> errList) {
		if (!StringUtils.isEmpty(salesEventDetailsMapper.getRevenueUsd())) {
			try
			{
				Double revenus = Double.parseDouble(salesEventDetailsMapper.getRevenueUsd());
				salesEventDetailsMapper.setDeriveRevenueUsd(new BigDecimal(revenus.doubleValue()));
			}
			catch(Exception e)
			{
				salesEventDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.SALE_EVENT_TYPE_REVENUE_CODE, Constants.SALE_EVENT_TYPE_REVENUE_MSG, "revenueUsd", false));
			}
			
		}
	}	

}
