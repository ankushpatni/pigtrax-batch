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
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.PenDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.GroupEventDetailMapper;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class GroupEventDetailValidator extends AbstractValidator {
	
	@Autowired
	private BarnDao barnDao;

	@Autowired
	private PenDao penDao;

	@Autowired
	private CompanyDao companyDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}
	
	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		GroupEventDetailMapper groupEventDetailMapper = null;
		for (Mapper mapper : list) {
			groupEventDetailMapper = (GroupEventDetailMapper) mapper;
			if (groupEventDetailMapper.isRecovrableErrors() == null || groupEventDetailMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateBarnId(groupEventDetailMapper, errList);				
				validateDateOfEntry(groupEventDetailMapper, errList);
				validateNumberOfPigs(groupEventDetailMapper, errList);	
				validateWeightInKg(groupEventDetailMapper, errList);
				validateInventoryAdjustment(groupEventDetailMapper, errList);
				/*validateRoomId(groupEventDetailMapper, errList);
				validateEmployeeGroupId(groupEventDetailMapper, errList);
				validateGroupId(groupEventDetailMapper, errList);
				validateTransportDestination(groupEventDetailMapper, errList);*/	
								
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	private void validateDateOfEntry(final GroupEventDetailMapper groupEventDetailMapper, List<ErrorBean> errList) {
		if (groupEventDetailMapper.getDateOfEntry() == null) {
			groupEventDetailMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "DateOfEntry", false));
		}
		/*Date birthDate = groupEventDetailMapper.getDateOfEntry();
		if (birthDate != null) {
			long diff = pigInfoMapper.getDeriveEntryDate().getTime() - birthDate.getTime();
			if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 90) {
				pigInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE, Constants.ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE_MSG,
						"entryDate", false));
			}
		}*/
	}
	
	private void validateBarnId(final GroupEventDetailMapper groupEventDetailMapper, List<ErrorBean> errList) {
		if (groupEventDetailMapper.getBarnId() != null) {
			try {
				groupEventDetailMapper.setDeriveBarnId(barnDao.getBarnPKId(groupEventDetailMapper.getBarnId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "DateOfEntry", false));
		}
	}
	
	private void validateNumberOfPigs(final GroupEventDetailMapper groupEventDetailMapper, List<ErrorBean> errList) {
		if (groupEventDetailMapper.getNumberOfPigs() != null) {
			try {
				Integer.parseInt(groupEventDetailMapper.getNumberOfPigs());
			} catch (Exception e) {
				if(e instanceof NumberFormatException)
				{
					groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "DateOfEntry", false));
				}
				else
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "DateOfEntry", false));
		}
	}
	
	private void validateWeightInKg(final GroupEventDetailMapper groupEventDetailMapper, List<ErrorBean> errList) {
		if (groupEventDetailMapper.getNumberOfPigs() != null) {
			try {
				groupEventDetailMapper.setDeriveWeightInKgs(Double.parseDouble(groupEventDetailMapper.getNumberOfPigs()));
			} catch (Exception e) {
				if(e instanceof NumberFormatException)
				{
					groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "WeightInKg", false));
				}
				else
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "WeightInKg", false));
		}
	}
	
	private void validateInventoryAdjustment(final GroupEventDetailMapper groupEventDetailMapper, List<ErrorBean> errList) {
		if (groupEventDetailMapper.getInventoryAdjustment() != null) {
			try {
				groupEventDetailMapper.setDeriveInventoryAdjustment(Integer.parseInt(groupEventDetailMapper.getInventoryAdjustment()));
			} catch (Exception e) {
				if(e instanceof NumberFormatException)
				{
					groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "InventoryAdjustment", false));
				}
				else
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			groupEventDetailMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "InventoryAdjustment", false));
		}
	}
	
	
}
