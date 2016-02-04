package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.GroupEventDaoImpl;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class RemovalEventExceptSalesDetailsValidator extends AbstractValidator {
	
	@Autowired
	PregnancyInfoDao pregnancyInfoDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	private GroupEventDaoImpl groupEventDaoImpl;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}
	
	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = null;
		
		for (Mapper mapper : list) {
			removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
			if (removalEventExceptSalesDetailsMapper.isRecovrableErrors() == null || removalEventExceptSalesDetailsMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				
				validateCompanyId( removalEventExceptSalesDetailsMapper, errList);
				validateNumberOfPigs(removalEventExceptSalesDetailsMapper, errList);
				validateRemovalDateTime(removalEventExceptSalesDetailsMapper, errList);
				validatePigInfoAndGroupEvent(removalEventExceptSalesDetailsMapper, errList);
				validateWeightInKg(removalEventExceptSalesDetailsMapper, errList);
				validateRemovalEventType(removalEventExceptSalesDetailsMapper, errList);
				validateRevenue(removalEventExceptSalesDetailsMapper, errList);
				validateMortalityReason(removalEventExceptSalesDetailsMapper, errList);
				//validateRoom(removalEventExceptSalesDetailsMapper, errList);
				validatePremises(removalEventExceptSalesDetailsMapper, errList);
				
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	/*private void validateRoom(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() != null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveCompanyId()) ||
				removalEventExceptSalesDetailsMapper.getDeriveCompanyId() <1) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_COMPANY_PRESENT_CODE, Constants.REM_COMPANY_PRESENT_MSG, "CompanyId", false));
		}
	}*/
	
	private void validatePremises(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveRemovalEventTypeId() != null && ! StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveRemovalEventTypeId()) &&
				removalEventExceptSalesDetailsMapper.getDeriveRemovalEventTypeId().intValue() == 9) {
			if(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId()) || 
					removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId().intValue() == 0)
			{
				removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_TRANSFER_DEST_PREMISES_NOT_PRESENT_CODE, Constants.REM_REMOVAL_TRANSFER_DEST_PREMISES_NOT_PRESENT_MSG, "destPremiseId", false));
			}
			
			if(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() != null && (removalEventExceptSalesDetailsMapper.getDeriveRoomId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveRoomId()) || 
					removalEventExceptSalesDetailsMapper.getDeriveRoomId().intValue() == 0))
			{
				removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_TRANSFER_DEST_ROOM_NOT_PRESENT_CODE, Constants.REM_REMOVAL_TRANSFER_DEST_ROOM_NOT_PRESENT_MSG, "roomId", false));
			}
			
			/*if(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() != null && !StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId()) )
			{
				GroupEvent groupEvent = groupEventDaoImpl.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId(),removalEventExceptSalesDetailsMapper.getDeriveCompanyId());
				if(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId() != null && !StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId()) && 
						groupEvent.getPremiseId().intValue() == removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId().intValue() )
				{
					removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_CODE, Constants.REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_MSG, "destPremiseId", false));
				}
				
			}*/
			
			if(removalEventExceptSalesDetailsMapper.getDerivePigInfoId() != null && !StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDerivePigInfoId()) )
			{
				PigInfo pigInfo = pigInfoDao.getPigDetails(removalEventExceptSalesDetailsMapper.getDerivePigInfoId());
				if(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId() != null && !StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId()) && 
						pigInfo.getPremiseId().intValue() == removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId().intValue() )
				{
					removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_CODE, Constants.REM_REMOVAL_TRANSFER_CURRENT_PREM_DEST_PREM_SAME_MSG, "destPremiseId", false));
				}
				
			}
		
			
		}
	}

	
	
	private void validateCompanyId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveCompanyId() == null || StringUtils.isEmpty(removalEventExceptSalesDetailsMapper.getDeriveCompanyId()) ||
				removalEventExceptSalesDetailsMapper.getDeriveCompanyId() <1) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_COMPANY_PRESENT_CODE, Constants.REM_COMPANY_PRESENT_MSG, "CompanyId", false));
		}
	}

	private void validateRemovalDateTime(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getRemovalDateTime() == null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_DATE_PRESENT_CODE, Constants.REM_REMOVAL_DATE_PRESENT_MSG, "RemovalDateTime", false));
		}
	}
	
	private void validateRevenue(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getRevenue() != null && removalEventExceptSalesDetailsMapper.getRevenue().trim().length() > 0
				 && removalEventExceptSalesDetailsMapper.getDeriveRevenue() == null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REMOVAL_REVENUE_CODE, Constants.REM_REMOVAL_REVENUE_MSG, "revenue", false));
		}
	}
	
	
	private void validateNumberOfPigs(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (!(removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs() != null && removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs() >= 1)) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MSG, "NumberOfPigs", false));
		}
		
		if(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() != null)
		{
			GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId(), removalEventExceptSalesDetailsMapper.getDeriveCompanyId());
			if(removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs()>groupEvent.getCurrentInventory())
			{
				removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_CODE, Constants.REM_NUMBER_OF_PIGS_PRESENT_MORE_MSG, "NumberOfPigs", false));
	
			}
		}
	}
	
	private void validateWeightInKg(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs() == null || removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs().intValue() == 0) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_CODE, Constants.REM_WEIGHT_IN_KG_PRESENT_PRESENT_MSG, "WeightInKg", false));
					}
	}	
	
	private void validateRemovalEventType(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getRemovalEventTypeId() != null && removalEventExceptSalesDetailsMapper.getRemovalEventTypeId().trim().length() > 0 && removalEventExceptSalesDetailsMapper.getDeriveRemovalEventTypeId() == null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_REM_EVENT_TYPE_PRESENT_CODE, Constants.REM_REM_EVENT_TYPE_PRESENT_MSG, "RemovalEventType", false));
		}
	}
	
	private void validateMortalityReason(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if (removalEventExceptSalesDetailsMapper.getMortalityReasonId() != null && removalEventExceptSalesDetailsMapper.getMortalityReasonId().trim().length() > 0 && removalEventExceptSalesDetailsMapper.getDeriveMortalityReasonId() == null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REMOVAL_INVALID_MORTALITY_CODE, Constants.REMOVAL_INVALID_MORTALITY_MSG, "Mortality Reason Type", false));
		}
	}
	
	private void validatePigInfoAndGroupEvent(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, List<ErrorBean> errList) {
		if((removalEventExceptSalesDetailsMapper.getGroupEventId() == null || removalEventExceptSalesDetailsMapper.getGroupEventId().trim().length() == 0) &&
				(removalEventExceptSalesDetailsMapper.getPigInfoId() == null || removalEventExceptSalesDetailsMapper.getPigInfoId().trim().length() == 0))
		{
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_REMOVAL_MISSING_GRP_PIGID_CODE, Constants.ERR_REMOVAL_MISSING_GRP_PIGID_MSG, "pigInfoId, groupEventId", false));
		}
		else if(removalEventExceptSalesDetailsMapper.getDerivePigInfoId() != null && removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() != null) {
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_CODE, Constants.REM_GROUP_PIG_ERR_BOTH_PRESENT_MSG, "RemovalEventType", false));
		}
		else if (removalEventExceptSalesDetailsMapper.getGroupEventId() != null && removalEventExceptSalesDetailsMapper.getGroupEventId().trim().length() > 0 && removalEventExceptSalesDetailsMapper.getDeriveGroupEventId() == null){
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REMOVAL_INVALID_GROUPID_CODE, Constants.REMOVAL_INVALID_GROUPID_MSG, "groupEventId", false));
		}
		else if (removalEventExceptSalesDetailsMapper.getPigInfoId() != null && removalEventExceptSalesDetailsMapper.getPigInfoId().trim().length() > 0 && removalEventExceptSalesDetailsMapper.getDerivePigInfoId() == null){
			removalEventExceptSalesDetailsMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REMOVAL_INVALID_PIGID_CODE, Constants.REMOVAL_INVALID_PIGID_MSG, "pigInfoId", false));
		}		
	}
	

}
