package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.FarrowEvent;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.FarrowEventDaoImpl;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class FarrowEventHandler implements Handler {

	@Autowired
	private FarrowEventDaoImpl farrowEventDao;

	private static final Logger logger = Logger.getLogger(FarrowEventHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				FarrowEventMapper farrowEventMapper = (FarrowEventMapper) mapper;
				if (farrowEventMapper.isRecovrableErrors() == null || farrowEventMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						FarrowEvent farrowEvent = populateFarrowEventfnfo(errorMap, farrowEventMapper, processDTO);
						if (farrowEvent != null) {
							farrowEventDao.insertFarrowEventformation(farrowEvent);
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

	private FarrowEvent populateFarrowEventfnfo(final Map<Mapper, List<ErrorBean>> errorMap, final FarrowEventMapper farrowEventfoMaper,
			final ProcessDTO processDTO) {
		FarrowEvent farrowEvent = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			farrowEvent = new FarrowEvent();
			farrowEvent.setPigInfoId(farrowEventfoMaper.getDerivePigInfoId());
			farrowEvent.setCompanyId(farrowEventfoMaper.getDeriveCompanyId());
			farrowEvent.setPragnancyEventId(farrowEventfoMaper.getPragnancyEventId());
			farrowEvent.setPenId(farrowEventfoMaper.getDerivePenId());
			farrowEvent.setFarrowDateTime(farrowEventfoMaper.getDeriveFarrowDate());
			farrowEvent.setLiveBorns(farrowEventfoMaper.getDeriveLiveBorns());
			farrowEvent.setStillBorns(farrowEventfoMaper.getDeriveStillBorns());
			farrowEvent.setMaleBorns(farrowEventfoMaper.getDeriveMaleBorns());
			farrowEvent.setFemaleBorns(farrowEventfoMaper.getDeriveFemaleBorns());
			farrowEvent.setMummies(farrowEventfoMaper.getDeriveMummies());
			farrowEvent.setLitterId(farrowEventfoMaper.getDeriveWeightInKGs());
			farrowEvent.setTeasts(farrowEventfoMaper.getDeriveTeasts());
			farrowEvent.setSowCondition(farrowEventfoMaper.getDeriveSowCondition());
			if ("Induced".equalsIgnoreCase(farrowEventfoMaper.getTypeOfBirth())) {
				farrowEvent.setInducedBirth(true);
			}
			if ("Assisted ".equalsIgnoreCase(farrowEventfoMaper.getTypeOfBirth())) {
				farrowEvent.setAssistedBirth(true);
			}
			farrowEvent.setEmployeeGrpId(farrowEventfoMaper.getDeriveEmployeeGrpId());
			farrowEvent.setUserUpdated(processDTO.getUserName());
		} catch (Exception e) {
			logger.error("Exception in FarrowEventHandler.populateFarrowEventfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(farrowEventfoMaper, errList);
		}
		return farrowEvent;
	}
}
