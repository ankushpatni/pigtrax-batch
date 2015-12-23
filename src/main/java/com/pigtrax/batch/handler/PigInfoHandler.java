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
import com.pigtrax.batch.beans.PigTraxEventMaster;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PigInfoDaoImpl;
import com.pigtrax.batch.dao.interfaces.PigTraxEventMasterDao;
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

	@Autowired
	private PigTraxEventMasterDao eventMasterDao;

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
							
							Integer pigInfoId = pigInfoDaoImpl.getPigInfoId(pigInfoMaper.getPigId(), pigInfoMaper.getDeriveCompanyId(), pigInfoMaper.getDerivePremiseId());
							if(pigInfoId == null)
							{
								int id = pigInfoDaoImpl.insertPigInformation(pigInfo);
								PigTraxEventMaster eventMaster = populateEventMaster(pigInfoMaper, id, processDTO);
								eventMasterDao.insertEventMaster(eventMaster);
								totalRecordsProcessed = totalRecordsProcessed + 1;
							}
							else
							{
								errList.add(ErrorBeanUtil.populateErrorBean(Constants.ENTRY_EVENT_DUPLICATE_PIGID_CODE, Constants.ENTRY_EVENT_DUPLICATE_PIGID_MSG , "pigId", false));
								isErrorOccured = true;
							}
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
			pigInfo.setPremiseId(pigInfoMaper.getDerivePremiseId());
			pigInfo.setAlternateTattoo(pigInfoMaper.getAlternateTattoo());
			pigInfo.setBirthDate(pigInfoMaper.getDeriveBirthDate());
			pigInfo.setCompanyId(pigInfoMaper.getDeriveCompanyId());			
			pigInfo.setDamId(pigInfoMaper.getDamId());
			pigInfo.setEntryDate(pigInfoMaper.getDeriveEntryDate());
			pigInfo.setGcompany(pigInfoMaper.getDeriveGCompany());
			pigInfo.setGfunctionTypeId(pigInfoMaper.getDeriveFfunctionTypeId());
			pigInfo.setGline(pigInfoMaper.getDeriveGline());
			pigInfo.setPigId(pigInfoMaper.getPigId());
			pigInfo.setOriginId(pigInfoMaper.getDeriveOriginId());
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

	private PigTraxEventMaster populateEventMaster(PigInfoMapper mapper, Integer generatedKey, ProcessDTO processDTO) {
		PigTraxEventMaster eventMaster = null;
		if (generatedKey != null && generatedKey > 0) {
			eventMaster = new PigTraxEventMaster();
			eventMaster.setEventTime(mapper.getDeriveEntryDate());
			eventMaster.setPigInfoId(generatedKey);
			eventMaster.setUserUpdated(processDTO.getUserName());
		}
		return eventMaster;
	}
}
