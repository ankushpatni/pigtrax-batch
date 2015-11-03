package com.pigtrax.batch.drivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.drivable.interfaces.Derivable;
import com.pigtrax.batch.mapper.GroupEventInfoMapper;
import com.pigtrax.batch.mapper.interfaces.Mapper;
import com.pigtrax.batch.util.DateUtil;
@Component
public class GroupEventInfoDerivable implements Derivable {	

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				GroupEventInfoMapper groupEventInfoMapper = (GroupEventInfoMapper) mapper;
				setGroupEntryDate(groupEventInfoMapper);
				setCompanyId(groupEventInfoMapper);
				setPhaseOfProductionType(groupEventInfoMapper);
				setGroupCloseDate(groupEventInfoMapper);
				setCurrentInventry(groupEventInfoMapper);
				setPreviousGroupId(groupEventInfoMapper);
				//remark not required
			}
		}
	}
	
	private void setGroupEntryDate(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			groupEventInfoMapper.setDeriveGroupStartDateTime(DateUtil.getDateFromString(groupEventInfoMapper.getGroupStartDateTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void setCompanyId(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			groupEventInfoMapper.setDeriveCompanyId(companyDao.getCompanyId(groupEventInfoMapper.getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void setPhaseOfProductionType(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			Integer phaseOfProductionTypeIdFromRefData = RefData.PHASEOFPRODUCTIONTYPE.getId(groupEventInfoMapper.getPhaseOfProductionType());
			if (phaseOfProductionTypeIdFromRefData > -1) {
				groupEventInfoMapper.setDerivePhaseOfProductionTypeId(phaseOfProductionTypeIdFromRefData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setGroupCloseDate(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			if (null != groupEventInfoMapper.getGroupCloseDateTime() && 
					!groupEventInfoMapper.getGroupCloseDateTime().equalsIgnoreCase("null") 
					&& !StringUtils.isEmpty(groupEventInfoMapper.getGroupCloseDateTime())) {
				groupEventInfoMapper.setDeriveGroupcloseDateTime((DateUtil
						.getDateFromString(groupEventInfoMapper
								.getGroupCloseDateTime())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setCurrentInventry(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			if (null != groupEventInfoMapper.getCurrentInventory() && 
					!groupEventInfoMapper.getCurrentInventory().equalsIgnoreCase("null") &&
					!StringUtils.isEmpty(groupEventInfoMapper.getCurrentInventory())) {
				Integer currentInventory = Integer
						.parseInt(groupEventInfoMapper.getCurrentInventory());
				if (currentInventory > -1) {
					groupEventInfoMapper
							.setDerivePhaseOfProductionTypeId(currentInventory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setPreviousGroupId(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			if (null != groupEventInfoMapper.getPreviousGroupId() && 
					!groupEventInfoMapper.getPreviousGroupId().equalsIgnoreCase("null") &&
					!StringUtils.isEmpty(groupEventInfoMapper.getPreviousGroupId())) {
				
				groupEventInfoMapper.setDerivePreviousGroupId(groupEventDao.getGroupEventId(groupEventInfoMapper.getPreviousGroupId(),groupEventInfoMapper.getDeriveCompanyId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
