package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PigInfoDaoImpl;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class PigInfoHandler implements Handler {

	@Autowired
	private PigInfoDaoImpl pigInfoDaoImpl;

	private static final Logger logger = Logger.getLogger(PigInfoHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				PigInfoMapper pigInfoMaper = (PigInfoMapper) mapper;
				if (pigInfoMaper.isRecovrableErrors() == null || pigInfoMaper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						PigInfo pigInfo = populatePigIfnfo(errorMap, pigInfoMaper, processDTO);
						if (pigInfo != null) {
							pigInfoDaoImpl.insertPigInformation(pigInfo);
							totalRecordsProcessed = totalRecordsProcessed + 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception in PigInfoHandler.execute : " + e);
						errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
						isErrorOccured = true;
					}
					if (errList != null && errList.size() > 0 && isErrorOccured) {
						errorMap.put(mapper, errList);
					}
				}
			}
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private PigInfo populatePigIfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final PigInfoMapper pigInfoMaper, final ProcessDTO processDTO) {
		PigInfo pigInfo = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			pigInfo = new PigInfo();
			pigInfo.setAlternateTattoo(pigInfoMaper.getAlternateTattoo());
			pigInfo.setBarnId(pigInfoMaper.getDeriveBarnId());
			pigInfo.setBirthDate(pigInfoMaper.getDeriveBirthDate());
			pigInfo.setCompanyId(pigInfoMaper.getDeriveCompanyId());
			pigInfo.setCurrentFarrowEventDate(pigInfoMaper.getDeriveFarrowEventDate());
			pigInfo.setDamId(pigInfoMaper.getDamId());
			pigInfo.setEntryDate(pigInfoMaper.getDeriveEntryDate());
			pigInfo.setGcompany(pigInfoMaper.getDeriveGCompany());
			pigInfo.setGfunctionTypeId(pigInfoMaper.getDeriveFfunctionTypeId());
			pigInfo.setGline(pigInfoMaper.getDeriveGline());
			pigInfo.setPigId(pigInfoMaper.getPigId());
			pigInfo.setOrigin(pigInfoMaper.getOrigin());
			pigInfo.setParity(pigInfoMaper.getDeriveParity());
			pigInfo.setPenId(pigInfoMaper.getDerivePenId());
			pigInfo.setRemarks(pigInfoMaper.getRemarks());
			pigInfo.setSexTypeId((pigInfoMaper.getDeriveSexId()));
			pigInfo.setSireId(pigInfoMaper.getSireId());
			pigInfo.setTattoo(pigInfoMaper.getTattoo());
			pigInfo.setUserUpdated(processDTO.getUserName());
		} catch (Exception e) {
			logger.error("Exception in PigInfoHandler.populatePigIfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(pigInfoMaper, errList);
		}
		return pigInfo;
	}

}
