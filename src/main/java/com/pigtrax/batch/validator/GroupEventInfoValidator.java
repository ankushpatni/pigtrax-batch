package com.pigtrax.batch.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.util.PrimitiveDataUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class GroupEventInfoValidator  extends AbstractValidator {
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}
	
	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		GroupEventInfoMapper groupEventInfoMapper = null;
		for (Mapper mapper : list) {
			groupEventInfoMapper = (GroupEventInfoMapper) mapper;
			if (groupEventInfoMapper.isRecovrableErrors() == null || groupEventInfoMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateEntryDate(groupEventInfoMapper, errList);				
				validateCompanyId(groupEventInfoMapper, errList);
				validateGroupId(groupEventInfoMapper, errList);	
				validatePhaseOfProduction(groupEventInfoMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}
	
	private void validateEntryDate(final GroupEventInfoMapper groupEventInfoMapper, List<ErrorBean> errList) {
		if (groupEventInfoMapper.getDeriveGroupStartDateTime() == null) {
			groupEventInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "groupStartDate", false));
		}
	}
	
	private void validateCompanyId(final GroupEventInfoMapper groupEventInfoMapper, List<ErrorBean> errList) {
		if (groupEventInfoMapper.getCompanyId() == null || StringUtils.isEmpty(groupEventInfoMapper.getCompanyId())) {
			groupEventInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "companyId", false));
		}
	}
	
	private void validateGroupId(final GroupEventInfoMapper groupEventInfoMapper, List<ErrorBean> errList) {
		if (groupEventInfoMapper.getGroupId() == null || PrimitiveDataUtil.validateAlphaNumeric(groupEventInfoMapper.getGroupId())) {
			groupEventInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "groupId", false));
		}
	}
	
	private void validatePhaseOfProduction(final GroupEventInfoMapper groupEventInfoMapper, List<ErrorBean> errList) {
		if (groupEventInfoMapper.getDerivePhaseOfProductionTypeId() == null || groupEventInfoMapper.getDerivePhaseOfProductionTypeId() < 0) {
			groupEventInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH, Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "PhaseOfProductionType", false));
		}
	}
}
