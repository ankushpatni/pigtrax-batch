package com.pigtrax.batch.validator;

import java.sql.SQLException;
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
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class PigInfoValidator extends AbstractValidator {

	@Autowired
	PigInfoDao pigInfoDao;
	
	public Map<Mapper, List<ErrorBean>> validate(final List<Mapper> list, final ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list,
			Map<Mapper, List<ErrorBean>> errorMap) {
		PigInfoMapper pigInfoMapper = null;
		for (Mapper mapper : list) {
			pigInfoMapper = (PigInfoMapper) mapper;
			if (pigInfoMapper.isRecovrableErrors() == null || pigInfoMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateUniquePigId(pigInfoMapper, errList);
				validateEntryDate(pigInfoMapper, errList);
				validateBirthdate(pigInfoMapper, errList);
				validateParity(pigInfoMapper, errList);
				validateCompanyId(pigInfoMapper, errList);
				validateSexId(pigInfoMapper, errList);
				validateOrigin(pigInfoMapper, errList);
				validateGfunctionTypeId(pigInfoMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}

	private void validateEntryDate(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getDeriveEntryDate() == null) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH,
					Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "entryDate", false));
		} else {
			Date birthDate = pigInfoMapper.getDeriveBirthDate(); 
			if (birthDate != null) {
				long diff = pigInfoMapper.getDeriveEntryDate().getTime() - birthDate.getTime();
				if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 90) {
					pigInfoMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE,
							Constants.ERR_PIGONFO_ENTRY_EVENT_ENTRY_DATE_MSG, "entryDate", false));
				}
			}
		}
	}

	private void validateBirthdate(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getDeriveBirthDate() == null) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH,
					Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "birthDate", false));
		}
	}

	private void validateParity(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getParity() != null && (pigInfoMapper.getDeriveParity() == null || pigInfoMapper.getDeriveParity() < 0)) {
			pigInfoMapper.setDeriveParity(0);
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_DATA_TYPE_MIS_MATCH,
					Constants.ERR_DATA_TYPE_MIS_MATCH_MSG, "parity", false));
		}
	}

	private void validateCompanyId(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getDeriveCompanyId() == null || pigInfoMapper.getDeriveCompanyId() < 0) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_COMPANYID_CODE,
					Constants.ENTRY_EVENT_INVALID_COMPANYID_MSG, "companyId", false));
		}
	}
	
	
	private void validateUniquePigId(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) { 
		if (pigInfoMapper.getPigId() != null && pigInfoMapper.getDeriveCompanyId() != null && pigInfoMapper.getDeriveCompanyId() > 0) {
			Integer existingPifgId = null;
			try {
				 existingPifgId = pigInfoDao.getPigInfoId(pigInfoMapper.getPigId() , pigInfoMapper.getDeriveCompanyId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(existingPifgId != null && 0 < existingPifgId)
			{
				pigInfoMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_DUPLICATE_PIGID_CODE,
						Constants.ENTRY_EVENT_DUPLICATE_PIGID_MSG, "pigId", false));
			}
		}
	}
	
	private void validateOrigin(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) { 
		if (pigInfoMapper.getOrigin() != null && pigInfoMapper.getOrigin().trim().length() > 0 &&  (pigInfoMapper.getDeriveOriginId() == null || pigInfoMapper.getDeriveOriginId() < 0)) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_ORIGIN_CODE,
					Constants.ENTRY_EVENT_INVALID_ORIGIN_MSG, "origin", false));
		}
	}


	private void validateSexId(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getDeriveSexId() == null || pigInfoMapper.getDeriveSexId() < 0) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REF_DATA_NOT_FOUND_CODE,
					Constants.REF_DATA_NOT_FOUND_MSG, "sexTypeId", false));
		}
	}

	private void validateGfunctionTypeId(final PigInfoMapper pigInfoMapper, List<ErrorBean> errList) {
		if (pigInfoMapper.getGfunctionTypeId() != null && 0<pigInfoMapper.getGfunctionTypeId().trim().length() && (pigInfoMapper.getDeriveFfunctionTypeId() == null || pigInfoMapper.getDeriveFfunctionTypeId() < 0)) {
			pigInfoMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.REF_DATA_NOT_FOUND_CODE,
					Constants.REF_DATA_NOT_FOUND_MSG, "gfunctionTypeId", false));
		}
	}

}
