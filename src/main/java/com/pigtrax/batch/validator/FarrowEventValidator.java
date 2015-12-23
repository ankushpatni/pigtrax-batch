package com.pigtrax.batch.validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.PregnancyInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;
import com.pigtrax.batch.validator.interfaces.AbstractValidator;

@Component
public class FarrowEventValidator extends AbstractValidator {

	@Autowired
	private BreedingEventDao breedingEventDao;

	@Autowired
	private PregnancyInfoDao pregnancyInfoDao;

	@Override
	public Map<Mapper, List<ErrorBean>> validate(List<Mapper> list, ProcessDTO processDTO) {
		final Map<Mapper, List<ErrorBean>> errorMap = new HashMap<Mapper, List<ErrorBean>>();
		Config.Event eventConfig = ConfigCache.getInstance().getConfig().get(processDTO.getBatchType());
		super.runBaseValidation(list, eventConfig, processDTO, errorMap);
		return runCustomValidation(list, errorMap);
	}

	private Map<Mapper, List<ErrorBean>> runCustomValidation(final List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap) {
		FarrowEventMapper farrowEventMapper = null;
		for (Mapper mapper : list) {
			farrowEventMapper = (FarrowEventMapper) mapper;
			if (farrowEventMapper.isRecovrableErrors() == null || farrowEventMapper.isRecovrableErrors()) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				validateCompanyId(farrowEventMapper, errList);
				validatePremiseId(farrowEventMapper, errList);
				validatePigId(farrowEventMapper, errList);				
				validatePigPKId(farrowEventMapper, errList);
				validatePragnancyTest(farrowEventMapper, errList);
				validatePenId(farrowEventMapper, errList);
				validateFarrowDate(farrowEventMapper, errList);
				ValidateBornsMummies(farrowEventMapper, errList);
				validateLitterBirthWeight(farrowEventMapper, errList);
				validateLBirthType(farrowEventMapper, errList);
				validateLEmployeeGrp(farrowEventMapper, errList);
				validateTeats(farrowEventMapper, errList);
				validateSowCondition(farrowEventMapper, errList);
				if (errList.size() > 0) {
					errorMap.put(mapper, errList);
				}
			}
		}
		return errorMap;
	}

	private void validateTeats(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDeriveTeasts() == null) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_TEATS, Constants.FRW_EVNT_ERR_EMP_TEATS_MSG, "Teats", false));
		} else if (farrowEventMapper.getDeriveTeasts() < 1 || farrowEventMapper.getDeriveTeasts() > 30) {

			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_TEATS_VAL, Constants.FRW_EVNT_ERR_TEATS_VAL_MSG, "Teats", false));

		}

	}

	private void validatePremiseId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDerivePremiseId() == null || farrowEventMapper.getDerivePremiseId() < 0) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_INVALID_PREMISEID_CODE,
					Constants.ENTRY_EVENT_INVALID_PREMISEID_MSG, "farmName", false));
		}
	}
	
	private void validateSowCondition(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getSowCondition() != null && farrowEventMapper.getSowCondition().trim().length() > 0 && (farrowEventMapper.getDeriveSowCondition() == null || farrowEventMapper.getDeriveSowCondition()  < 0)) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_SOW_CNDTN, Constants.FRW_EVNT_ERR_SOW_CNDTN_MSG, "sowCondition", false));
		} else if (farrowEventMapper.getSowCondition() != null && farrowEventMapper.getSowCondition().trim().length() > 0 && (farrowEventMapper.getDeriveSowCondition() < 1 || farrowEventMapper.getDeriveSowCondition() > 5)) {

			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_SOW_CNDTN_VAL, Constants.FRW_EVNT_ERR_SOW_CNDTN_VAL_MSG, "sowCondition", false));

		}

	}

	private void validateLEmployeeGrp(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getEmployeeGrpId() != null && farrowEventMapper.getEmployeeGrpId().trim().length() > 0 && (farrowEventMapper.getDeriveEmployeeGrpId() == null || farrowEventMapper.getDeriveEmployeeGrpId() < 0)) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_EMP_GRP, Constants.FRW_EVNT_ERR_EMP_GRP_MSG, "employeeGroupId", true));
		}

	}

	private void validateLBirthType(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getTypeOfBirth() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getTypeOfBirth())) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_BIRTH_TYPE, Constants.FRW_EVNT_ERR_BIRTH_TYPE_MSG, "typeOfBirth", false));
		}
		else if(farrowEventMapper.getTypeOfBirth() != null && !("induced".equalsIgnoreCase(farrowEventMapper.getTypeOfBirth().trim()) || "assisted".equalsIgnoreCase(farrowEventMapper.getTypeOfBirth().trim())))
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_BIRTH_TYPE, Constants.FRW_EVNT_INVALID_BIRTH_TYPE_MSG, "typeOfBirth", false));
		}

	}

	private void validateLitterBirthWeight(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getWeightInKGs() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getDeriveWeightInKGs())) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil
					.populateErrorBean(Constants.FRW_EVNT_ERR_LITTER_WEIGHT, Constants.FRW_EVNT_ERR_LITTER_WEIGHT_MSG, "LitterWeightKgs", true));
		}
		else if(farrowEventMapper.getWeightInKGs() !=  null && farrowEventMapper.getWeightInKGs().trim().length() > 0 && farrowEventMapper.getDeriveWeightInKGs() == null)
		{
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil
					.populateErrorBean(Constants.FRW_EVNT_INVALID_LITTER_WEIGHT, Constants.FRW_EVNT_INVALID_LITTER_WEIGHT_MSG, "LitterWeightKgs", true));
		}

	}

	private void validatePigId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getPigId() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getPigId())) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PIG_ID, Constants.FRW_EVNT_ERR_PIG_ID_MSG, "pigId", false));
		}

	}

	private void validateCompanyId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getCompanyId() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getCompanyId())) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_CMPNY_ID, Constants.FRW_EVNT_ERR_CMPNY_ID_MSG, "companyId", false));
		}

	}

	private void validatePigPKId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDerivePigInfoId() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getDerivePigInfoId())) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PIG_ID, Constants.FRW_EVNT_ERR_PIG_ID_MSG, "pigId", false));
		}

	}

	
	private void validatePragnancyTest(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if ( farrowEventMapper.getPragnancyEventId() == null) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PRG_TEST, Constants.FRW_EVNT_ERR_PRG_TEST_MSG, "resultDate", false));
		}
	}

	private void validatePenId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getPenId() == null) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PEN_ID, Constants.FRW_EVNT_ERR_PEN_ID_MSG, "penID", true));
		} else if (farrowEventMapper.getPenId() == null) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PEN_ID_PK, Constants.FRW_EVNT_ERR_PEN_ID_PK_MSG, "penID", true));

		}

	}

	private void validateFarrowDate(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getFarrowDate() == null) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_FRW_DATE, Constants.FRW_EVNT_ERR_FRW_DATE_MSG, "FarrowDate", false));
		} else if (farrowEventMapper.getDeriveFarrowDate() == null) {

			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_FRW_DATE_1, Constants.FRW_EVNT_ERR_FRW_DATE_1_MSG, "FarrowDate", false));

		} 
	}

	private void ValidateBornsMummies(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if(farrowEventMapper.getLiveBorns() != null && farrowEventMapper.getLiveBorns().trim().length() > 0 && farrowEventMapper.getDeriveLiveBorns() == null)
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_FARROW_NUM, Constants.FRW_EVNT_INVALID_FARROW_NUM_MSG, "liveBorns", false)); 
		}
		else if(farrowEventMapper.getStillBorns() != null && farrowEventMapper.getStillBorns().trim().length() > 0 && farrowEventMapper.getDeriveStillBorns() == null)
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_FARROW_NUM, Constants.FRW_EVNT_INVALID_FARROW_NUM_MSG, "stillBorns", false)); 
		}
		else if(farrowEventMapper.getMaleBorns() != null && farrowEventMapper.getMaleBorns().trim().length() > 0 && farrowEventMapper.getDeriveMaleBorns() == null)
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_FARROW_NUM, Constants.FRW_EVNT_INVALID_FARROW_NUM_MSG, "maleBorns", false)); 
		}
		else if(farrowEventMapper.getFemaleBorns() != null && farrowEventMapper.getFemaleBorns().trim().length() > 0 && farrowEventMapper.getDeriveFemaleBorns() == null)
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_FARROW_NUM, Constants.FRW_EVNT_INVALID_FARROW_NUM_MSG, "femaleBorns", false)); 
		}
		else if(farrowEventMapper.getMummies() != null && farrowEventMapper.getMummies().trim().length() > 0 && farrowEventMapper.getDeriveMummies() == null)
		{
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_INVALID_FARROW_NUM, Constants.FRW_EVNT_INVALID_FARROW_NUM_MSG, "mummies", false)); 
		}

	}
}
