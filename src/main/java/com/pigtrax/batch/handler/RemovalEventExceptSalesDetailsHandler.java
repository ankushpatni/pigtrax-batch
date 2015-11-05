package com.pigtrax.batch.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.RemovalEventExceptSalesDetailsDaoImpl;
import com.pigtrax.batch.exception.ErrorBean;
import com.pigtrax.batch.handler.interfaces.Handler;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.Constants;
import com.pigtrax.batch.util.ErrorBeanUtil;

@Component
public class RemovalEventExceptSalesDetailsHandler implements Handler{
	
	@Autowired
	private RemovalEventExceptSalesDetailsDaoImpl removalEventExceptSalesDetailsDaoImpl;

	private static final Logger logger = Logger.getLogger(GroupEventDetailHandler.class);

	public Map<String, Object> execute(final List<Mapper> list, final Map<Mapper, List<ErrorBean>> errorMap, final ProcessDTO processDTO) {
		Map<String, Object> output = new HashMap<String, Object>();

		int totalRecordsInInput = list != null ? list.size() : 0;
		int totalRecordsProcessed = 0;
		if (list != null) {
			for (Mapper mapper : list) {
				List<ErrorBean> errList = new ArrayList<ErrorBean>();
				RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
				if (removalEventExceptSalesDetailsMapper.isRecovrableErrors() == null || removalEventExceptSalesDetailsMapper.isRecovrableErrors()) {
					boolean isErrorOccured = false;
					try {
						RemovalEventExceptSalesDetails removalEventExceptSalesDetails = populateRemovalEventExceptSalesDetails(errorMap, removalEventExceptSalesDetailsMapper, processDTO);
						if (removalEventExceptSalesDetails != null) {
							removalEventExceptSalesDetailsDaoImpl.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
							totalRecordsProcessed = totalRecordsProcessed + 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("Exception in GroupEventInfoHandler.execute : " + e);
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
	
	private RemovalEventExceptSalesDetails populateRemovalEventExceptSalesDetails(final Map<Mapper, List<ErrorBean>> errorMap, RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper, final ProcessDTO processDTO) {
		RemovalEventExceptSalesDetails removalEventExceptSalesDetails = null;
		List<ErrorBean> errList = new ArrayList<ErrorBean>();
		try {
			removalEventExceptSalesDetails = new RemovalEventExceptSalesDetails();
			removalEventExceptSalesDetails.setGroupEventId(removalEventExceptSalesDetailsMapper.getDeriveGroupEventId());
			removalEventExceptSalesDetails.setPigInfoId(removalEventExceptSalesDetailsMapper.getDerivePigInfoId());
			removalEventExceptSalesDetails.setCompanyId(removalEventExceptSalesDetailsMapper.getDeriveCompanyId());
			removalEventExceptSalesDetails.setNumberOfPigs(removalEventExceptSalesDetailsMapper.getDeriveNumberOfPigs());
			removalEventExceptSalesDetails.setRemovalDateTime(removalEventExceptSalesDetailsMapper.getDeriveRemovalDateTime());
			removalEventExceptSalesDetails.setWeightInKgs(new BigDecimal(removalEventExceptSalesDetailsMapper.getDeriveWeightInKgs().intValue()));
			removalEventExceptSalesDetails.setRemovalEventId(removalEventExceptSalesDetailsMapper.getDeriveRemovalTypeEventId());
			removalEventExceptSalesDetails.setPremiseId(removalEventExceptSalesDetailsMapper.getDerivePremiseId());
			removalEventExceptSalesDetails.setDestPremiseId(removalEventExceptSalesDetailsMapper.getDeriveDestPremiseId());
			removalEventExceptSalesDetails.setRemarks(removalEventExceptSalesDetailsMapper.getRemarks());
			removalEventExceptSalesDetails.setMortalityReasonId(removalEventExceptSalesDetailsMapper.getDeriveMortalityReasonId());
		} catch (Exception e) {
			logger.error("Exception in RemovalEventExceptSalesDetailsHandler.removalEventExceptSalesDetails" + e.getMessage());
			errList.add(ErrorBeanUtil.populateErrorBean(Constants.ERR_SYS_CODE, Constants.ERR_SYS_MESSASGE + e.getMessage(), null, false));
			e.printStackTrace();
		}
		if (errList != null && errList.size() > 0) {
			errorMap.put(removalEventExceptSalesDetailsMapper, errList);
		}
		return removalEventExceptSalesDetails;
	}
	
}
