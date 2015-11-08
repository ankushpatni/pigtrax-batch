package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.PremisesDaoImpl;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
import com.pigtrax.batch.dao.interfaces.TransportDestinationDao;
import com.pigtrax.batch.dao.interfaces.TransportJourneyDao;
import com.pigtrax.batch.dao.interfaces.TransportTrailerDao;
import com.pigtrax.batch.dao.interfaces.TransportTruckDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.RemovalEventExceptSalesDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class RemovalEventExceptSalesDetailsDerivable implements Derivable{
	
	@Autowired
	private BarnDao barnDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private PigInfoDao pigInfoDao;

	@Autowired
	private PenDaoImpl penDao;

	@Autowired
	private EmployeeGroupDao employeeGroupDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	PremisesDaoImpl premisesDao;
		
	@Autowired
	TransportTrailerDao transportTrailerDao;
	
	@Autowired
	TransportTruckDao transportTruckDao;
	
	@Autowired
	TransportDestinationDao transportDestinationDao;
	
	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper = (RemovalEventExceptSalesDetailsMapper) mapper;
				
				setCompanyId(removalEventExceptSalesDetailsMapper);				
				setNumberOfPigs(removalEventExceptSalesDetailsMapper);
				setRemovalDateTime(removalEventExceptSalesDetailsMapper);
				setWeightInKgs(removalEventExceptSalesDetailsMapper);
				setRemovalEventType(removalEventExceptSalesDetailsMapper);
				setPigInfoId(removalEventExceptSalesDetailsMapper);
				setGroupEventId(removalEventExceptSalesDetailsMapper);
				setPremiseId(removalEventExceptSalesDetailsMapper);
				setDestPremiseId(removalEventExceptSalesDetailsMapper);
				setMortalityReason(removalEventExceptSalesDetailsMapper);
				setTransportDestination(removalEventExceptSalesDetailsMapper);
				setTransportTruck(removalEventExceptSalesDetailsMapper);
				setTransportTrailer(removalEventExceptSalesDetailsMapper);
				setTransportStartDate(removalEventExceptSalesDetailsMapper);
				setTransportEndDate(removalEventExceptSalesDetailsMapper);
			}
		}
	}
	
	private void setCompanyId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper) {
		try {
			removalEventExceptSalesDetailsMapper.setDeriveCompanyId(companyDao.getCompanyId(removalEventExceptSalesDetailsMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRemovalDateTime(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper) {
		try {
			removalEventExceptSalesDetailsMapper.setDeriveRemovalDateTime(DateUtil.getDateFromString(removalEventExceptSalesDetailsMapper.getRemovalDateTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setNumberOfPigs(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveNumberOfPigs(Integer.parseInt(removalEventExceptSalesDetailsMapper.getNumberOfPigs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWeightInKgs(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveWeightInKgs(Double.parseDouble(removalEventExceptSalesDetailsMapper.getWeightInKgs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRemovalEventType(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
				Integer phaseOfProductionTypeIdFromRefData = RefData.REMOVALEVENTTYPE.getId(removalEventExceptSalesDetailsMapper.getRemovalEventTypeId());
				if (phaseOfProductionTypeIdFromRefData > -1) {
					removalEventExceptSalesDetailsMapper.setDeriveRemovalEventTypeId(phaseOfProductionTypeIdFromRefData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void setPigInfoId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(removalEventExceptSalesDetailsMapper.getPigInfoId(), removalEventExceptSalesDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGroupEventId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(removalEventExceptSalesDetailsMapper.getGroupEventId(),removalEventExceptSalesDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setPremiseId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDerivePremiseId(premisesDao.getPremisesPK(removalEventExceptSalesDetailsMapper.getPremiseId(),removalEventExceptSalesDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDestPremiseId(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveDestPremiseId(premisesDao.getPremisesPK(removalEventExceptSalesDetailsMapper.getDestPremiseId(),removalEventExceptSalesDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void setMortalityReason(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			Integer phaseOfProductionTypeIdFromRefData = RefData.MORTALITYREASONTYPE.getId(removalEventExceptSalesDetailsMapper.getMortalityReasonId());
			if (phaseOfProductionTypeIdFromRefData > -1) {
				removalEventExceptSalesDetailsMapper.setDeriveMortalityReasonId(phaseOfProductionTypeIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportDestination(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveTransportDestinationId(transportDestinationDao.findByTransportDestinationName(removalEventExceptSalesDetailsMapper.getTransportDestinationId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportTruck(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveTransportTruckId(transportTruckDao.findByTransportTruckByTruckNumber(removalEventExceptSalesDetailsMapper.getTransportTruckId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportTrailer(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper){
		try {
			removalEventExceptSalesDetailsMapper.setDeriveTransportTrailerId(transportTrailerDao.findByTransportTrailerByTrailerNumberPlate(removalEventExceptSalesDetailsMapper.getTransportTrailerId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportStartDate(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper) {
		try {
			removalEventExceptSalesDetailsMapper.setDeriveJourneyStartTime(DateUtil.getDateFromString(removalEventExceptSalesDetailsMapper.getJourneyStartTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setTransportEndDate(final RemovalEventExceptSalesDetailsMapper removalEventExceptSalesDetailsMapper) {
		try {
			removalEventExceptSalesDetailsMapper.setDeriveJourneyEndTime(DateUtil.getDateFromString(removalEventExceptSalesDetailsMapper.getJourneyEndTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
