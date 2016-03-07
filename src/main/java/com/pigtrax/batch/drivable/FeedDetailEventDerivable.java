package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.FeedEventDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.MasterRationDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.dao.interfaces.SiloDao;
import com.pigtrax.batch.dao.interfaces.TransportTrailerDao;
import com.pigtrax.batch.dao.interfaces.TransportTruckDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.FeedDetailEventMapper;
import com.pigtrax.batch.mapper.FeedEventMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class FeedDetailEventDerivable implements Derivable {

	@Autowired
	private PremisesDao premiseDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	SiloDao siloDao;
	
	@Autowired
	FeedEventDao feedEventDao;
	
	@Autowired
	MasterRationDao rationDao;
	
	@Autowired
	TransportTruckDao transportTruckDao;
	
	@Autowired
	TransportTrailerDao transportTrailerDao;
	
	@Override
	public void derive(List<Mapper> list, ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				FeedDetailEventMapper feedEventMapper = (FeedDetailEventMapper) mapper;
				if(!feedEventMapper.isEmpty())
				{
					setCompanyId(feedEventMapper, processDTO);
					setPremiseId(feedEventMapper, processDTO);
					setRationId(feedEventMapper);
					setTransportTruck(feedEventMapper);
					setTransportTrailer(feedEventMapper);
					setGroupEventId(feedEventMapper);
					setFeedEventDate(feedEventMapper);
					setFeedEventType(feedEventMapper);
					setWeightInKgs(feedEventMapper);
					setSilo(feedEventMapper);
					setFeedEventId(feedEventMapper);
					setFeedCost(feedEventMapper);
				}
			}
		}
	}
	
	
	private void setRationId(FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveRationId(rationDao.getPKRationId(feedEventMapper.getRationId()));
		} catch (Exception e) {
			feedEventMapper.setDeriveRationId(null);
		}
	}
	
	
	private void setTransportTruck(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveTransportTruck(transportTruckDao.findByTransportTruckByTruckNumber(feedEventMapper.getTransportTruck()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportTrailer(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper
					.setDeriveTransportTrailer(transportTrailerDao.findByTransportTrailerByTrailerNumberPlate(feedEventMapper.getTransportTrailer()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setCompanyId(final FeedDetailEventMapper feedEventMapper,ProcessDTO processDTO) {
		try {
			feedEventMapper.setDeriveCompanyId(processDTO.getCompanyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	private void setPremiseId(final FeedDetailEventMapper feedEventMapper, ProcessDTO processDTO) {
		try {
			feedEventMapper.setDerivePremiseId(processDTO.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setGroupEventId(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(feedEventMapper.getGroupEventId(), feedEventMapper.getDeriveCompanyId(), feedEventMapper.getDerivePremiseId())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	private void setFeedEventDate(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedEventDate(DateUtil.getDateFromString(feedEventMapper.getFeedEventDate())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	private void setFeedEventType(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedEventType(RefData.FEEDEVENTTYPE.getId(feedEventMapper.getFeedEventType())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setWeightInKgs(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveWeightInKgs(Double.parseDouble(feedEventMapper.getWeightInKgs())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setSilo(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveSilo(siloDao.getSiloPKId(feedEventMapper.getSilo(), feedEventMapper.getDerivePremiseId())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setFeedEventId(final FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedEventId(feedEventDao.getFeedEventPKId(feedEventMapper.getTicketNumber(), feedEventMapper.getDerivePremiseId())); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setFeedCost(FeedDetailEventMapper feedEventMapper) {
		try {
			feedEventMapper.setDeriveFeedCost(Double.parseDouble(feedEventMapper.getFeedCost()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
