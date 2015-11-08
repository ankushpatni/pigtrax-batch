package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
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
	
	//SalesEventDetailsMapper

	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				SalesEventDetailsMapper salesEventDetailsMapper = (SalesEventDetailsMapper) mapper;
				
				setCompanyId(salesEventDetailsMapper);				
				setNumberOfPigs(salesEventDetailsMapper);
				setSalesDateTime(salesEventDetailsMapper);
				setWeightInKgs(salesEventDetailsMapper);
				setRemovalEventType(salesEventDetailsMapper);
				setPigInfoId(salesEventDetailsMapper);
				setGroupEventId(salesEventDetailsMapper);
				
			}
		}
	}
	
	private void setCompanyId(final SalesEventDetailsMapper salesEventDetailsMapper) {
		try {
			salesEventDetailsMapper.setDeriveCompanyId(companyDao.getCompanyId(salesEventDetailsMapper.getCompanyId()));
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
	
	private void setRemovalEventType(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
				Integer phaseOfProductionTypeIdFromRefData = RefData.REMOVALEVENTTYPE.getId(salesEventDetailsMapper.getRemovalEventId());
				if (phaseOfProductionTypeIdFromRefData > -1) {
					salesEventDetailsMapper.setDeriveRemovalEventTypeId(phaseOfProductionTypeIdFromRefData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void setPigInfoId(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
			salesEventDetailsMapper.setDerivePigInfoId(pigInfoDao.getPigInfoId(salesEventDetailsMapper.getPigInfoId(), salesEventDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGroupEventId(final SalesEventDetailsMapper salesEventDetailsMapper){
		try {
			salesEventDetailsMapper.setDeriveGroupEventId(groupEventDao.getGroupEventId(salesEventDetailsMapper.getGroupEventId(),salesEventDetailsMapper.getDeriveCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
