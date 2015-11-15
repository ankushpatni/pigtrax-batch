package com.pigtrax.batch.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.batch.beans.MatingDetails;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.BreedingEventDao;
import com.pigtrax.batch.dao.interfaces.MatingDetailsDao;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.MatingDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Service
@Transactional
public class MatingDetailsHandler implements Handler {

	@Autowired
	private MatingDetailsDao matingDetailsDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;

	private static final Logger logger = Logger.getLogger(MatingDetailsHandler.class);

	@Override
	public Map<String, Object> execute(List<Mapper> list, Map<Mapper, List<ErrorBean>> errorMap, ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();
		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				MatingDetailsMapper matingDetailsMapper = (MatingDetailsMapper) mapper;
				if (matingDetailsMapper.isRecovrableErrors() == null || matingDetailsMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						MatingDetails matingDetails = populateMatingDetails(errorMap, matingDetailsMapper, processDTO);
						if (matingDetails != null) {
							matingDetailsDao.insertMatingDetails(matingDetails);
							/*if(matingDetailsMapper.isUpdateServiceStartDate())
								breedingEventDao.updateServiceStartDate(matingDetails.getMatingDate(), matingDetails.getBreedingEventId());*/
							totalRecordsProcessed+=1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception in MatingDetailsHandler.execute : " + e);
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

	private MatingDetails populateMatingDetails(final Map<Mapper, List<ErrorBean>> errorMap, final MatingDetailsMapper matingDetailsMapper,
			final ProcessDTO processDTO) {
		MatingDetails matingDetails = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			matingDetails = new MatingDetails();
			matingDetails.setBreedingEventId(matingDetailsMapper.getDeriveBreedingEventId()); 
			matingDetails.setEmployeeGroupId(matingDetailsMapper.getDeriveEmployeeGroupId());
			matingDetails.setSemenId(matingDetails.getSemenId());
			matingDetails.setMatingDate(matingDetailsMapper.getDeriveMatingDate());
			matingDetails.setMatingQuality(matingDetailsMapper.getDeriveMateQuality());
			matingDetails.setUserUpdated(processDTO.getUserName());
			
		} catch (Exception e) {
			logger.error("Exception in MatingDetailsHandler.populateMatingDetails" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(matingDetailsMapper, errList);
		}
		return matingDetails;
	}
}
