package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
import com.pigtrax.batch.dao.interfaces.TransportDestinationDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.SalesEventDetailsMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class SalesEventDetailsDrivable implements Derivable{
	
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
	TransportDestinationDao transportDestinationDao;
	
	//SalesEventDetailsMapper

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				SalesEventDetailsMapper salesEventDetailsMapper = (SalesEventDetailsMapper) mapper;
				
				salesEventDetailsMapper.setDeriveCompanyId(processDTO.getCompanyId());
				setPigInfoId(salesEventDetailsMapper, processDTO);
				setGroupEventId(salesEventDetailsMapper, processDTO);				
				setNumberOfPigs(salesEventDetailsMapper);
				setRemovalEventType(salesEventDetailsMapper);
				setWeightInKgs(salesEventDetailsMapper);
				setSalesTypes(salesEventDetailsMapper);
				setSalesDateTime(salesEventDetailsMapper);
				setSoldTo(salesEventDetailsMapper);
				setSalesReason(salesEventDetailsMapper);
				
			}
		}
	}
	
	private void setSoldTo(final SalesEventDetailsMapper salesEventDetailsMapper) {
		try {
			if(salesEventDetailsMapper.getSoldTo() != null && !StringUtils.isEmpty(salesEventDetailsMapper.getSoldTo()))
			salesEventDetailsMapper.setDeriveSoldTo(transportDestinationDao.findByTransportDestinationName(salesEventDetailsMapper.getSoldTo()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSalesReason(final SalesEventDetailsMapper salesEventDetailsMapper) {
		String salesReason = salesEventDetailsMapper.getSalesReason();
		StringBuffer buffer = new StringBuffer();
		try {
			if(salesReason != null && salesReason.trim().length() > 0)
			{
				String[]  types = StringUtils.split(salesReason, "|");
				if(types != null && types.length > 0)
				{
					int i= 0;
					for(String type : types)
					{
						i++;
						Integer salesTypeId = RefData.SALESREASON.getId((type !=null)?type.trim():"");
						if(salesTypeId != null)
							buffer.append(salesTypeId);
						if(i<types.length)
							buffer.append(",");
					}
				}
			}
			salesEventDetailsMapper.setDeriveSalesReason(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSalesTypes(final SalesEventDetailsMapper salesEventDetailsMapper) {
		String salesType = salesEventDetailsMapper.getSalesType();
		StringBuffer buffer = new StringBuffer();
		try {
			if(salesType != null && salesType.trim().length() > 0)
			{
				String[]  types = StringUtils.split(salesType, "|");
				if(types != null && types.length > 0)
				{
					int i= 0;
					for(String type : types)
					{
						i++;
						Integer salesTypeId = RefData.SALESTYPE.getId((type !=null)?type.trim():"");
						if(salesTypeId != null)
							buffer.append(salesTypeId);
						if(i<types.length)
							buffer.append(",");
					}
				}
			}
			salesEventDetailsMapper.setDeriveSalesTypes(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSalesDateTime(final SalesEventDetailsMapper salesEventDetailsMapper) {
		try {
			salesEventDetailsMapper.setDeriveSalesDateTime(DateUtil.getDateFromString(salesEventDetailsMapper.getSalesDateTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setNumberOfPigs(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
			salesEventDetailsMapper.setDeriveNumberOfPigs(Integer.parseInt(salesEventDetailsMapper.getNumberOfPigs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWeightInKgs(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
			salesEventDetailsMapper.setDeriveWeightInKgs(Double.parseDouble(salesEventDetailsMapper.getWeightInKgs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void setPigInfoId(final SalesEventDetailsMapper salesEventDetailsMapper, final ProcessDTO processDTO){
		try {
			if(salesEventDetailsMapper.getPigInfoId() != null && !StringUtils.isEmpty(salesEventDetailsMapper.getPigInfoId()))
			{
			salesEventDetailsMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(salesEventDetailsMapper.getPigInfoId(), processDTO.getCompanyId(), processDTO.getPremiseId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGroupEventId(final SalesEventDetailsMapper salesEventDetailsMapper, final ProcessDTO processDTO){
		try {
			if(salesEventDetailsMapper.getGroupEventId() != null && !StringUtils.isEmpty(salesEventDetailsMapper.getGroupEventId()))
			{
			salesEventDetailsMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(salesEventDetailsMapper.getGroupEventId(), processDTO.getCompanyId(), processDTO.getPremiseId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRemovalEventType(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
				Integer phaseOfProductionTypeIdFromRefData = RefData.REMOVALEVENTTYPE.getId(salesEventDetailsMapper.getRemovalType());
				if (phaseOfProductionTypeIdFromRefData > -1) {
					salesEventDetailsMapper.setDeriveRemovalType(phaseOfProductionTypeIdFromRefData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
