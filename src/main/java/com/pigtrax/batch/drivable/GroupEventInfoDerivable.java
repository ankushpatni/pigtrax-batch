package com.pigtrax.batch.drivable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pigtrax.batch.beans.RoomPK;
import com.pigtrax.batch.config.RefData;
import com.pigtrax.batch.core.ProcessDTO;
import com.pigtrax.batch.dao.interfaces.CompanyDao;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;
import com.pigtrax.batch.dao.interfaces.PremisesDao;
import com.pigtrax.batch.dao.interfaces.RoomDao;
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
	
	@Autowired
	PremisesDao premiseDao;
	
	@Autowired
	RoomDao roomDao;
	
	@Override
	public void derive(final List<Mapper> list, final ProcessDTO processDTO) {
		if (list != null) {
			for (Mapper mapper : list) {
				GroupEventInfoMapper groupEventInfoMapper = (GroupEventInfoMapper) mapper;
				if(!groupEventInfoMapper.isEmpty())
				{
					setCompanyId(groupEventInfoMapper, processDTO);
					setPremiseId(groupEventInfoMapper, processDTO);
					setDeriveRoomIds(groupEventInfoMapper);
					setGroupEntryDate(groupEventInfoMapper);				
					setPhaseOfProductionType(groupEventInfoMapper);
					setGroupCloseDate(groupEventInfoMapper);
					setCurrentInventry(groupEventInfoMapper);
					setPreviousGroupId(groupEventInfoMapper);
					//remark not required
				}
			} 
		}
	}
	
	private void setPremiseId(GroupEventInfoMapper groupEventInfoMapper, ProcessDTO processDTO) {
		try {
			groupEventInfoMapper.setDerivePremiseId(processDTO.getPremiseId()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	private void setDeriveRoomIds(GroupEventInfoMapper groupEventInfoMapper) {
		List<RoomPK> roomIds = null;
		try {
			if(groupEventInfoMapper.getRooms() != null && groupEventInfoMapper.getRooms().trim().length() > 0)
			{
				StringTokenizer tokens = new StringTokenizer(groupEventInfoMapper.getRooms(), "|");
				if(tokens != null)
				{
					roomIds = new ArrayList<RoomPK>();
					int i = 0;
					while(tokens.hasMoreElements())
					{							
						String token = tokens.nextToken();
						if(token != null)
						{
							Integer roomId = roomDao.getRoomPkId(token, groupEventInfoMapper.getDeriveCompanyId(), groupEventInfoMapper.getDerivePremiseId());
							RoomPK roomPk = new RoomPK();	
							if(roomId != null )
								roomPk.setId(roomId.toString());
							if(roomId != null && !roomIds.contains(roomPk))
								roomIds.add(roomPk);
						}
					}
				}
				groupEventInfoMapper.setDeriveRoomIds(roomIds);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void setGroupEntryDate(final GroupEventInfoMapper groupEventInfoMapper) {
		try {
			groupEventInfoMapper.setDeriveGroupStartDateTime(DateUtil.getDateFromString(groupEventInfoMapper.getGroupStartDateTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void setCompanyId(final GroupEventInfoMapper groupEventInfoMapper, ProcessDTO processDTO) {
		try {
			groupEventInfoMapper.setDeriveCompanyId(processDTO.getCompanyId());
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
			if (null != groupEventInfoMapper.getGroupCloseDate() && 
					!groupEventInfoMapper.getGroupCloseDate().equalsIgnoreCase("null") 
					&& !StringUtils.isEmpty(groupEventInfoMapper.getGroupCloseDate())) {
				groupEventInfoMapper.setDeriveGroupcloseDateTime((DateUtil
						.getDateFromString(groupEventInfoMapper
								.getGroupCloseDate())));
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
							.setDeriveCurrentInventory(currentInventory);
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
