package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.MasterRationDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.dao.interfaces.TransportTrailerDao;
import com.pigtrax.batch.dao.interfaces.TransportTruckDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.PigInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class FeedEventDerivable implements Derivable {

	@Autowired
	private TransportJourneyDao transportJourneyDao;
	
	@Autowired
	MasterRationDao rationDao;
	
	@Autowired
	private PremisesDao premiseDao;
	
	@Autowired
	TransportTruckDao transportTruckDao;
	
	@Autowired
	TransportTrailerDao transportTrailerDao;

	@Autowired
	CompanyDao companyDao;
	
	@Override
	public void derive(List<Mapper> list, ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				FeedEventMapper feedEventMapper = (FeedEventMapper) mapper;
				setCompanyId(feedEventMapper, processDTO);
				setPremiseId(feedEventMapper, processDTO);
				setRationId(feedEventMapper);
				setFeedquantitykgs(feedEventMapper);
				setFeedCost(feedEventMapper);
				setInitialFeedEntryDate(feedEventMapper);
				setTransportTruck(feedEventMapper);
				setTransportTrailer(feedEventMapper);	
			}
		}
	}
	
	
	private void setCompanyId(final FeedEventMapper feedEventMapper, ProcessDTO processDTO) {
		try {
			feedEventMapper.setDeriveCompanyId(processDTO.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	private void setPremiseId(final FeedEventMapper feedEventMapper, ProcessDTO processDTO) {
		try {
			feedEventMapper.setDerivePremiseId(processDTO.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	private void setRationId(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveRationId(rationDao.getPKRationId(feedEventMapper.getRationId()));
		} catch (Exception e) {
			feedEventMapper.setDeriveRationId(null);
		}
	}

	private void setFeedquantitykgs(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedQuantityKGs(Double.parseDouble(feedEventMapper.getFeedQuantityKGs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFeedCost(FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedCost(Double.parseDouble(feedEventMapper.getFeedCost()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setInitialFeedEntryDate(final FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveIntialFeedEntryDate(DateUtil.getDateFromString(feedEventMapper.getFeedEntryDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setTransportTruck(final FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveTransportTruck(transportTruckDao.findByTransportTruckByTruckNumber(feedEventMapper.getTransportTruck()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportTrailer(final FeedEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveTransportTrailer(transportTrailerDao.findByTransportTrailerByTrailerNumberPlate(feedEventMapper.getTransportTrailer()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
