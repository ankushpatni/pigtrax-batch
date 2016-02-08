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
import com.pigtrax.batch.beans.IndividualPigletStatus;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.FarrowEventDaoImpl;
import com.pigtrax.batch.dao.interfaces.IndividualPigletDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.FarrowEventMapper;
import com.pigtrax.batch.mapper.IndividualPigletStatusMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class IndividualPigletStatusHandler implements Handler {

	@Autowired
	private IndividualPigletDao individualPigletDao;

	private static final Logger logger = Logger.getLogger(IndividualPigletStatusHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				IndividualPigletStatusMapper individualPigletMapper = (IndividualPigletStatusMapper) mapper;
				if(!individualPigletMapper.isEmpty())
				{
					if (individualPigletMapper.isRecovrableErrors() == null || individualPigletMapper.isRecovrableErrors()) {
						boolean isErrorOccured = false;
						try {
							IndividualPigletStatus individualPigletStatus = populateIndividualPigletInfo(errorMap, individualPigletMapper, processDTO);
							if (individualPigletStatus != null) {
								
								boolean flag = false;
								if(individualPigletMapper.getTattooId() != null && individualPigletMapper.getTattooId().trim().length() > 0)
									flag = individualPigletDao.checkIfExists(individualPigletMapper.getTattooId(), individualPigletMapper.getDerivePremiseId());
								if(flag)
								{
									individualPigletMapper.setRecovrableErrors(false); 
									isErrorOccured = true;
									errList.add(ErrorBeanUtil.populateErrorBean(Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_CODE, Constants.IND_PIGLET_ERR_DUPLICATE_TATTOO_MSG, "tattooId", false));
								}
								else 
								{								
									individualPigletDao.insertIndividualPigletStatus(individualPigletStatus); 
									totalRecordsProcessed = totalRecordsProcessed + 1;
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
			}
			output.put("errors", errorMap);
			output.put("size", totalRecordsInInput);
			output.put("success", totalRecordsProcessed);
		}
		return output;
	}

	private IndividualPigletStatus populateIndividualPigletInfo(final Map<Mapper, List<ErrorBean>> errorMap, final IndividualPigletStatusMapper individualPigletMapper,
			final ProcessDTO processDTO) {
		IndividualPigletStatus individualPigletStatus = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			individualPigletStatus = new IndividualPigletStatus();
			individualPigletStatus.setPigId(individualPigletMapper.getPigId());
			individualPigletStatus.setTattooId(individualPigletMapper.getTattooId());
			individualPigletStatus.setLitterId(individualPigletMapper.getDeriveLitterId());
			individualPigletStatus.setWtAtBirth(individualPigletMapper.getDeriveWtAtBirth());
			individualPigletStatus.setWtAtWeaning(individualPigletMapper.getDeriveWtAtWeaning());
			individualPigletStatus.setUserUpdated(processDTO.getUserName());
			individualPigletStatus.setPremiseId(individualPigletMapper.getDerivePremiseId());
			individualPigletStatus.setWtAtFirstMonth(individualPigletMapper.getDeriveWtAtFirstMonth());
			individualPigletStatus.setWtAtSecondMonth(individualPigletMapper.getDeriveWtAtSecondMonth());
			individualPigletStatus.setWtAtThirdMonth(individualPigletMapper.getDeriveWtAtThirdMonth());
			individualPigletStatus.setWtAtFourthMonth(individualPigletMapper.getDeriveWtAtFourthMonth());
			individualPigletStatus.setWtAtFifthMonth(individualPigletMapper.getDeriveWtAtFifthMonth());
			individualPigletStatus.setWtAtSixthMonth(individualPigletMapper.getDeriveWtAtSixthMonth());
		} catch (Exception e) {
			logger.error("Exception in FarrowEventHandler.populateFarrowEventfnfo" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(individualPigletMapper, errList);
		}
		return individualPigletStatus;
	}
}
