package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pigtrax.batch.beans.GroupEvent;
import com.pigtrax.batch.dao.interfaces.GroupEventDao;

@Repository
public class GroupEventDaoImpl implements GroupEventDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(GroupEventDaoImpl	.class);

	
	@Override
	public Integer getGroupEventId(String groupId, int companyId) throws SQLException { 
		logger.debug("groupId/company Ids are :" + groupId+"/"+companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"GroupEvent\" where lower(\"groupId\") = ? and \"id_Company\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (groupId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId.trim().toLowerCase());
					ps.setInt(2, companyId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("groupId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}

		return null;

	}
	
	@Override
	public GroupEvent getGroupEventByGeneratedGroupId(final int groupId, final int companyId)
	{
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\",\"id_Company\",  \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\" "+
				"from pigtrax.\"GroupEvent\" where  \"id\" = ? and \"id_Company\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setInt(2, companyId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList.get(0);
			}
			return null;
	}
	
	@Override
	public int addGroupEvent(final com.pigtrax.batch.beans.GroupEvent groupEvent) throws SQLException {

		final String Qry = "insert into pigtrax.\"GroupEvent\"(\"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\","
				+ " \"remarks\", \"lastUpdated\",\"userUpdated\", \"id_Company\" ,\"currentInventory\"  , \"previousGroupId\", \"id_PhaseOfProductionType\",\"id_Premise\") "
				+ "values(?,?,?,?,?,current_timestamp,?,?,?,?,?,?)";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, groupEvent.getGroupId().toUpperCase());
				ps.setDate(2, new java.sql.Date(groupEvent
						.getGroupStartDateTime().getTime()));
				if( null != groupEvent.getGroupCloseDateTime())
				{
				ps.setDate(3, new java.sql.Date(groupEvent
						.getGroupCloseDateTime().getTime()));
				}
				else
				{
					ps.setNull(3, java.sql.Types.DATE);
				}
				
				ps.setBoolean(4, true);
				ps.setString(5, groupEvent.getRemarks());
				ps.setString(6, groupEvent.getUserUpdated());
				ps.setInt(7, groupEvent.getCompanyId());
				if(null != groupEvent.getCurrentInventory())
				{
					ps.setInt(8, groupEvent.getCurrentInventory());
				}
				else
				{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				ps.setString(9, groupEvent.getPreviousGroupId());
				if(null != groupEvent.getPhaseOfProductionTypeId())
				{
					ps.setInt(10, groupEvent.getPhaseOfProductionTypeId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				
				if(null != groupEvent.getPremiseId())
				{
					ps.setInt(11, groupEvent.getPremiseId());
				}
				else
				{
					ps.setNull(11, java.sql.Types.INTEGER);
				}
				
				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);

		return keyVal;
	}
	
public int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws SQLException{
		
		String query = "update pigtrax.\"GroupEvent\" SET \"currentInventory\"=?  where \"id\" = ? and \"id_Company\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEvent.getCurrentInventory());				
				ps.setInt(2,groupEvent.getId());
				ps.setInt(3, groupEvent.getCompanyId());
			}
		});
		
	}
	
	public int updateGroupEventCurrentInventorywithStatus(final GroupEvent groupEvent) throws SQLException{
		
		String query = "update pigtrax.\"GroupEvent\" SET \"currentInventory\"=?,\"isActive\"=?  where \"id\" = ? and \"id_Company\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEvent.getCurrentInventory());	
				ps.setBoolean(2, groupEvent.isActive());	
				ps.setInt(3,groupEvent.getId());
				ps.setInt(4, groupEvent.getCompanyId());
			}
		});
		
	}
	
	
	private static final class GroupEventMapper implements RowMapper<GroupEvent> {
		public GroupEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			GroupEvent groupEvent = new GroupEvent();
			groupEvent.setId(rs.getInt("id"));
			groupEvent.setGroupId(rs.getString("groupId"));
			groupEvent.setGroupStartDateTime(rs.getDate("groupStartDateTime"));
			groupEvent.setGroupCloseDateTime(rs.getDate("groupCloseDateTime"));
			groupEvent.setActive(rs.getBoolean("isActive"));
			groupEvent.setRemarks(rs.getString("remarks"));
			groupEvent.setLastUpdated(rs.getDate("lastUpdated"));
			groupEvent.setUserUpdated(rs.getString("userUpdated"));
			groupEvent.setCompanyId(rs.getInt("id_Company"));
			groupEvent.setCurrentInventory(rs.getInt("currentInventory"));
			groupEvent.setPreviousGroupId(rs.getString("previousGroupId"));
			groupEvent.setPhaseOfProductionTypeId(rs.getInt("id_PhaseOfProductionType"));
			return groupEvent;
		}
	}
	
	
	@Override
	public int updateGroupEvent(final GroupEvent groupEvent) throws SQLException
	{
		String query = "update pigtrax.\"GroupEvent\" SET \"groupStartDateTime\"=?, \"groupCloseDateTime\"=?, \"isActive\"=?, \"remarks\"=?, \"lastUpdated\"=?,"+
				" \"userUpdated\"=? , \"currentInventory\"=? ,\"previousGroupId\" =? , \"id_PhaseOfProductionType\" = ?, \"id_Premise\"=?  where \"id\" = ? ";
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if( null != groupEvent.getGroupStartDateTime())
					{
						ps.setDate(1, new java.sql.Date(groupEvent
							.getGroupStartDateTime().getTime()));
					}
					else
					{
						ps.setNull(1, java.sql.Types.DATE);
					}
					if( null != groupEvent.getGroupCloseDateTime())
					{
					ps.setDate(2, new java.sql.Date(groupEvent
							.getGroupCloseDateTime().getTime()));
					}
					else
					{
						ps.setNull(2, java.sql.Types.DATE);
					}
					
					ps.setBoolean(3, groupEvent.isActive());
					ps.setString(4, groupEvent.getRemarks());
					ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(6, groupEvent.getUserUpdated());
					
					if(null != groupEvent.getCurrentInventory())
					{
						ps.setInt(7, groupEvent.getCurrentInventory());
					}
					else
					{
						ps.setNull(7, java.sql.Types.INTEGER);
					}
					ps.setString(8, groupEvent.getPreviousGroupId());
					if(null != groupEvent.getPhaseOfProductionTypeId())
					{
						ps.setInt(9, groupEvent.getPhaseOfProductionTypeId());
					}
					else
					{
						ps.setNull(9, java.sql.Types.INTEGER);
					}
					
					if(null != groupEvent.getPremiseId() && groupEvent.getPremiseId() != 0)
					{
						ps.setInt(10, groupEvent.getPremiseId());
					}
					else
					{
						ps.setNull(10, java.sql.Types.INTEGER);
					}
					
					ps.setInt(11, groupEvent.getId());
				}
			});	
	}

}
