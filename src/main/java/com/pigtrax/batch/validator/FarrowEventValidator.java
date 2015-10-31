package com.pigtrax.batch.validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.Config;
import com.pigtrax.batch.config.ConfigCache;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.mapper.FarrowEventMapper;
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
				validatePigId(farrowEventMapper, errList);
				validateCompanyId(farrowEventMapper, errList);
				validatePigPKId(farrowEventMapper, errList);
				validateServiceDate(farrowEventMapper, errList);
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
		} else if (farrowEventMapper.getDeriveTeasts() < 1 || farrowEventMapper.getDeriveTeasts() > 5) {

			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_TEATS_VAL, Constants.FRW_EVNT_ERR_TEATS_VAL_MSG, "Teats", false));

		}

	}

	private void validateSowCondition(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDeriveSowCondition() == null) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_SOW_CNDTN, Constants.FRW_EVNT_ERR_SOW_CNDTN_MSG, "Teats", false));
		} else if (farrowEventMapper.getDeriveTeasts() < 1 || farrowEventMapper.getDeriveTeasts() > 30) {

			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_SOW_CNDTN_VAL, Constants.FRW_EVNT_ERR_SOW_CNDTN_VAL_MSG, "Teats", false));

		}

	}

	private void validateLEmployeeGrp(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDeriveEmployeeGrpId() == null) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_EMP_GRP, Constants.FRW_EVNT_ERR_EMP_GRP_MSG, "LitterWeightKgs", true));
		}

	}

	private void validateLBirthType(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getTypeOfBirth() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getTypeOfBirth())) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_BIRTH_TYPE, Constants.FRW_EVNT_ERR_BIRTH_TYPE_MSG, "LitterWeightKgs", false));
		}

	}

	private void validateLitterBirthWeight(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getWeightInKGs() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getDeriveWeightInKGs())) {
			farrowEventMapper.setRecovrableErrors(true);
			errList.add(ErrorBeanUtil
					.populateErrorBean(Constants.FRW_EVNT_ERR_LITTER_WEIGHT, Constants.FRW_EVNT_ERR_LITTER_WEIGHT_MSG, "LitterWeightKgs", true));
		}

	}

	private void validatePigId(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getPigInfoId() == null || Constants.BLANK_STRING.equals(farrowEventMapper.getPigInfoId())) {
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

	private void validateServiceDate(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDeriveServiceDate() == null) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_SRV_DATE, Constants.FRW_EVNT_ERR_SRV_DATE_MSG, "serviceDate", false));
		}

	}

	private void validatePragnancyTest(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		if (farrowEventMapper.getDeriveServiceDate() != null && farrowEventMapper.getDerivePigInfoId() != null) {

			Map<String, Object> creteriaMap = new HashMap<String, Object>();
			try {
				creteriaMap.put("penPKId", farrowEventMapper.getDerivePigInfoId());
				creteriaMap.put("serviceDate", farrowEventMapper.getDeriveServiceDate());

				Integer breedingEventId = breedingEventDao.getBreedingEventId(creteriaMap);
				if (breedingEventDao.getBreedingEventId(creteriaMap) == null) {
					farrowEventMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_BRD_DATE, Constants.FRW_EVNT_ERR_BRD_DATE_MSG, "serviceDate", false));
				} else {
					creteriaMap.put("breedingEvenId", breedingEventId);
					Integer pragagncyId = pregnancyInfoDao.getPragnancyId(creteriaMap);
					farrowEventMapper.setPragnancyEventId(pragagncyId);
					if (pragagncyId == null) {
						farrowEventMapper.setRecovrableErrors(false);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_PRG_TEST, Constants.FRW_EVNT_ERR_PRG_TEST_MSG, "serviceDate", false));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				farrowEventMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), "serviceDate", false));
			}

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

		} else {
			if (farrowEventMapper.getDeriveServiceDate() != null) {
				long diff = farrowEventMapper.getDeriveFarrowDate().getTime() - farrowEventMapper.getDeriveServiceDate().getTime();

				if (diff <= 105 || diff >= 130) {

					farrowEventMapper.setRecovrableErrors(false);
					errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_FRW_DATE_2, Constants.FRW_EVNT_ERR_FRW_DATE_2_MSG, "FarrowDate", false));
				}
			} else {

				farrowEventMapper.setRecovrableErrors(false);
				errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_FRW_DATE_1, Constants.FRW_EVNT_ERR_FRW_DATE_1_MSG, "FarrowDate", false));

			}
		}

	}

	private void ValidateBornsMummies(final FarrowEventMapper farrowEventMapper, List<ErrorBean> errList) {
		int cnt = 0;
		if (farrowEventMapper.getDeriveLiveBorns() != null) {
			farrowEventMapper.setDeriveStillBorns(0);
			farrowEventMapper.setDeriveMaleBorns(0);
			farrowEventMapper.setDeriveFemaleBorns(0);
			farrowEventMapper.setDeriveMummies(0);
		} else {
			cnt++;
		}

		if (farrowEventMapper.getDeriveStillBorns() != null) {

			farrowEventMapper.setDeriveLiveBorns(0);
			farrowEventMapper.setDeriveMaleBorns(0);
			farrowEventMapper.setDeriveFemaleBorns(0);
			farrowEventMapper.setDeriveMummies(0);

		} else {
			cnt++;
		}

		if (farrowEventMapper.getDeriveMaleBorns() != null) {

			farrowEventMapper.setDeriveLiveBorns(0);
			farrowEventMapper.setDeriveStillBorns(0);
			farrowEventMapper.setDeriveFemaleBorns(0);
			farrowEventMapper.setDeriveMummies(0);

		} else {
			cnt++;
		}

		if (farrowEventMapper.getDeriveFemaleBorns() == null) {

			farrowEventMapper.setDeriveLiveBorns(0);
			farrowEventMapper.setDeriveStillBorns(0);
			farrowEventMapper.setDeriveMaleBorns(0);
			farrowEventMapper.setDeriveMummies(0);

		} else {
			cnt++;
		}

		if (farrowEventMapper.getDeriveMummies() == null) {

			farrowEventMapper.setDeriveLiveBorns(0);
			farrowEventMapper.setDeriveStillBorns(0);
			farrowEventMapper.setDeriveMaleBorns(0);
			farrowEventMapper.setDeriveFemaleBorns(0);

		} else {
			cnt++;
		}

		if (cnt == 5) {
			farrowEventMapper.setRecovrableErrors(false);
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.FRW_EVNT_ERR_BORNS_MUM_VAL, Constants.FRW_EVNT_ERR_BORNS_MUM_VAL_MSG, "liveBorns", false));

		}

	}
}
