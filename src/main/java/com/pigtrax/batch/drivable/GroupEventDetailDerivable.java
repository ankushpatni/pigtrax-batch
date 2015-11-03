package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.PenDaoImpl;
import com.pigtrax.batch.dao.interfaces.BarnDao;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.GroupEventDetailMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;

@Component
public class GroupEventDetailDerivable implements Derivable {
	
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
	
	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				GroupEventDetailMapper groupEventDetailMapper = (GroupEventDetailMapper) mapper;
				
				setCompanyId(groupEventDetailMapper);
				setGroupId(groupEventDetailMapper);
				setBarnId(groupEventDetailMapper);
				setDateOfEntry(groupEventDetailMapper);
				setNumberOfPigs(groupEventDetailMapper);
				setWeightInKgs(groupEventDetailMapper);
				setRoomId(groupEventDetailMapper);
				setEmployeeGroupId(groupEventDetailMapper);				
				setIndeventoryAdjustment(groupEventDetailMapper);
				
			}
		}
	}
	
	private void setBarnId(final GroupEventDetailMapper groupEventDetailMapper) {
		if (groupEventDetailMapper.getBarnId() != null && !StringUtils.isEmpty(groupEventDetailMapper.getBarnId()) &&
				groupEventDetailMapper.getBarnId().equalsIgnoreCase("null")) {
			try {
				groupEventDetailMapper.setDeriveBarnId(barnDao.getBarnPKId(groupEventDetailMapper.getBarnId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setDateOfEntry(final GroupEventDetailMapper groupEventDetailMapper) {
		try {
			groupEventDetailMapper.setDeriveDateOfEntry(DateUtil.getDateFromString(groupEventDetailMapper.getDateOfEntry()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setNumberOfPigs(final GroupEventDetailMapper groupEventDetailMapper){
		try {
			groupEventDetailMapper.setDeriveNumberOfPigs(Integer.parseInt(groupEventDetailMapper.getNumberOfPigs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWeightInKgs(final GroupEventDetailMapper groupEventDetailMapper){
		try {
			groupEventDetailMapper.setDeriveWeightInKgs(Double.parseDouble(groupEventDetailMapper.getWeightInKgs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRoomId(final GroupEventDetailMapper groupEventDetailMapper){
		try {
			if (groupEventDetailMapper.getRoomId() != null && !StringUtils.isEmpty(groupEventDetailMapper.getRoomId()) &&
					groupEventDetailMapper.getRoomId().equalsIgnoreCase("null")) {
			groupEventDetailMapper.setDeriveRoomId(roomDao.getRoomPkId(groupEventDetailMapper.getRoomId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setEmployeeGroupId(final GroupEventDetailMapper groupEventDetailMapper) {
		try {
			if (groupEventDetailMapper.getEmployeeGroupId() != null && !StringUtils.isEmpty(groupEventDetailMapper.getEmployeeGroupId()) &&
					groupEventDetailMapper.getEmployeeGroupId().equalsIgnoreCase("null")) {
			groupEventDetailMapper.setDeriveEmployeeGroupId(employeeGroupDao.getEmployeeGroupPKId(groupEventDetailMapper.getDerivecompanyId(), groupEventDetailMapper.getEmployeeGroupId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setCompanyId(final GroupEventDetailMapper groupEventDetailMapper) {
		try {
			groupEventDetailMapper.setDerivecompanyId(companyDao.getCompanyId(groupEventDetailMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGroupId(final GroupEventDetailMapper groupEventDetailMapper)
	{
		try { 
			if(groupEventDetailMapper.getGroupId() != null)
				groupEventDetailMapper.setDeriveGroupId(groupEventDao.getGroupEventId(groupEventDetailMapper.getGroupId(), groupEventDetailMapper.getDerivecompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setIndeventoryAdjustment(
			final GroupEventDetailMapper groupEventDetailMapper) {
		try {
			if (groupEventDetailMapper.getInventoryAdjustment() != null
					&& !StringUtils.isEmpty(groupEventDetailMapper
							.getInventoryAdjustment())
					&& groupEventDetailMapper.getInventoryAdjustment()
							.equalsIgnoreCase("null")) {
				
				groupEventDetailMapper.setDeriveInventoryAdjustment(Integer.parseInt(groupEventDetailMapper.getInventoryAdjustment()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
