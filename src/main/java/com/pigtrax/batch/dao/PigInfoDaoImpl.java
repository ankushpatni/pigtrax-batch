package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pigtrax.batch.beans.PigInfo;
import com.pigtrax.batch.dao.interfaces.PigInfoDao;

@Repository
public class PigInfoDaoImpl implements PigInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);

	@Override
	public int insertPigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PigInfo\"(\"pigId\", \"sireId\", \"damId\", \"entryDate\", \"id_Origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\", \"tattoo\", \"alternateTattoo\", \"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\",  "
				+ "\"id_Premise\", \"id_SexType\", \"parity\",\"isActive\",\"id_GfunctionType\",\"id_Room\") "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				ps.setString(1, pigInfo.getPigId());
				ps.setString(2, pigInfo.getSireId());
				ps.setString(3, pigInfo.getDamId());
				ps.setDate(4, new java.sql.Date(pigInfo.getEntryDate().getTime()));
				ps.setObject(5, pigInfo.getOriginId(), java.sql.Types.INTEGER);
				ps.setObject(6, pigInfo.getGline(), java.sql.Types.INTEGER);
				ps.setObject(7, pigInfo.getGcompany(), java.sql.Types.INTEGER);
				if (pigInfo.getBirthDate() != null) {
					ps.setDate(8, new java.sql.Date(new DateTime(pigInfo.getBirthDate()).toLocalDate().toDateMidnight().getMillis()));
				} else {
					ps.setNull(8, java.sql.Types.DATE);
				}
				ps.setString(9, pigInfo.getTattoo());
				ps.setString(10, pigInfo.getAlternateTattoo());
				ps.setString(11, pigInfo.getRemarks());
				ps.setString(12, pigInfo.getUserUpdated());
				ps.setInt(13, pigInfo.getCompanyId());
				ps.setObject(14, pigInfo.getPremiseId(), java.sql.Types.INTEGER);
				ps.setObject(15, pigInfo.getSexTypeId(), java.sql.Types.INTEGER);
				if(pigInfo.getParity() != null)
					ps.setInt(16, pigInfo.getParity());
				else
					ps.setInt(16,0);
				ps.setBoolean(17, pigInfo.isActive());
				ps.setObject(18, pigInfo.getGfunctionTypeId(), java.sql.Types.INTEGER);
				ps.setObject(19, pigInfo.getRoomId(), java.sql.Types.INTEGER);
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}

	@Override
	public Integer getPigInfoId(String pigId, int companyId) throws SQLException {
		logger.debug("Pig Id/company Ids are :" + pigId + "/" + companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"PigInfo\" where lower(\"pigId\") = ? and \"id_Company\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (pigId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, pigId.trim().toLowerCase());
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
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}
	
	
	@Override
	public Integer getPigInfoId(String pigId, int companyId, int premiseId) throws SQLException {
		logger.debug("Pig Id/company Ids are :" + pigId + "/" + companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"PigInfo\" where lower(\"pigId\") = ? and \"id_Company\" = ? and \"id_Premise\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (pigId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, pigId.trim().toLowerCase());
					ps.setInt(2, companyId);
					ps.setInt(3, premiseId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}
	
	
	@Override
	public Integer getPigInfoIdForTattoo(String tattoo, int companyId, int premiseId) throws SQLException {
		logger.debug("Pig Id/company Ids are :" + tattoo + "/" + companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"PigInfo\" where lower(\"tattoo\") = ? and \"id_Company\" = ? and \"id_Premise\" = ?");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (tattoo != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, tattoo.trim().toLowerCase());
					ps.setInt(2, companyId);
					ps.setInt(3, premiseId);
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}
	
	
	@Override
	public Integer getActivePigInfoId(String pigId, int companyId) throws SQLException {
		logger.debug("Pig Id/company Ids are :" + pigId + "/" + companyId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select id from pigtrax.\"PigInfo\" where lower(\"pigId\") = ? and \"id_Company\" = ? and \"isActive\" is true");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		if (pigId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, pigId.trim().toLowerCase());
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
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}

	@Override
	public Integer getPKfromPigId(final Map<String, String> searchCriteria) throws SQLException {
		if (searchCriteria != null && !searchCriteria.isEmpty()) {
			String companyId = searchCriteria.get("companyId");
			String pigId = searchCriteria.get("pigId");
			StringBuffer qryBuffer = new StringBuffer();
			qryBuffer
					.append("select id from pigtrax.\"PigInfo\" where upper(\"pigId\") = COALESCE(?,\"pigId\") and \"id_Company\" = COALESCE(?,\"id_Company\")");
			final String qry = qryBuffer.toString();
			Long retValList1 = null;

			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if (pigId != null) {
						ps.setString(1, pigId.trim().toUpperCase());
					} else {
						ps.setNull(1, java.sql.Types.VARCHAR);
					}
					if (companyId != null) {
						ps.setInt(2, Integer.parseInt(companyId));
					} else {
						ps.setNull(2, java.sql.Types.INTEGER);
					}
				}
			}, new ResultSetExtractor<Long>() {
				public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getLong(1);
					}
					return null;
				}
			});
			logger.debug("penId retVal is :" + retValList1);
			if (retValList1 != null) {
				return Integer.decode(retValList1.toString());
			}
		}
		return null;
	}
	
	@Override
	public boolean isPigASow(final Integer pigInfoId) {
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select CASE WHEN \"id_SexType\" = 1 THEN 'false' ELSE 'true' END  as isSow from pigtrax.\"PigInfo\" where \"id\" = ?");
		final String qry = qryBuffer.toString();
		Boolean retValList1 = null;
		if (pigInfoId != null) {
			retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1,pigInfoId);
				}
			}, new ResultSetExtractor<Boolean>() {
				public Boolean extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getBoolean(1);
					}
					return null;
				}
			});
			logger.debug("pigInfoId retVal is :" + retValList1);
			if (retValList1 != null) {
				return retValList1;
			}
			else
				return false;
		}
		return false;
	}
	
	
	@Override
	public PigInfo getPigDetails(final Integer pigInfoId)
	{
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer.append("select \"id\", \"pigId\",\"sireId\",\"damId\",\"id_Origin\",\"gline\","
				+ "\"gcompany\",\"birthDate\",\"tattoo\",\"alternateTattoo\",\"remarks\",\"id_Company\","
				+ "\"id_Barn\",\"id_SexType\",\"entryDate\",\"isActive\",\"id_GfunctionType\" "
				+ "from pigtrax.\"PigInfo\" where \"id\" = ?");
		final String qry = qryBuffer.toString();
		List<PigInfo> pigInfoList = null;
		if (pigInfoId != null) {
			pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1,pigInfoId);
				}
			}, new PigInfoRecordMapper());
			if(pigInfoList != null && 0<pigInfoList.size())
				return pigInfoList.get(0);
		}
		return null;
	}
	
	
	private static final class PigInfoRecordMapper implements RowMapper<PigInfo> {
		public PigInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigInfo pigInfo = new PigInfo();
			pigInfo.setId(rs.getInt("id"));
			pigInfo.setPigId(rs.getString("pigId"));
			pigInfo.setSireId(rs.getString("sireId"));
			pigInfo.setDamId(rs.getString("damId"));
			pigInfo.setOriginId(rs.getInt("id_Origin"));
			pigInfo.setGline(rs.getObject("gline") != null ? rs.getInt("gline") : null);
			pigInfo.setGcompany((rs.getObject("gcompany") != null) ? rs.getInt("gcompany") : 0);
			pigInfo.setBirthDate(rs.getDate("birthDate"));
			pigInfo.setTattoo(rs.getString("tattoo"));
			pigInfo.setAlternateTattoo(rs.getString("alternateTattoo"));
			pigInfo.setRemarks(rs.getString("remarks"));
			pigInfo.setCompanyId(rs.getInt("id_Company"));
			pigInfo.setBarnId((rs.getObject("id_Barn")!=null)?(Integer)rs.getObject("id_Barn") : null);			
			pigInfo.setSexTypeId(rs.getInt("id_SexType")); 
			pigInfo.setEntryDate(rs.getDate("entryDate"));
			pigInfo.setActive(rs.getBoolean("isActive"));
			pigInfo.setGfunctionTypeId((rs.getObject("id_GfunctionType")!=null)?(Integer)rs.getObject("id_GfunctionType") : null);
			return pigInfo;
		}
	}
	
	/**
	 * Update the pig status based on 
	 */
	
	public int updatePigInfoStatus(final Integer id, final Boolean pigStatus)
	{
		String query = "update pigtrax.\"PigInfo\" SET \"isActive\"=?  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, pigStatus);
				ps.setInt(2, id);
			}
		});
	
	}
	@Override
	public int updatePigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"PigInfo\" set \"pigId\"=?, \"sireId\" = ?, \"damId\" = ?, \"origin\"= ?, \"gline\"= ?, "
				+ "\"gcompany\" = ?, \"birthDate\" = ?, \"tattoo\" = ?, \"alternateTattoo\" = ?, "
				+ "\"remarks\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_Company\" = ?,"
				+ " \"id_Room\" = ?, \"id_Barn\" = ?, \"id_SexType\" =?, \"entryDate\" = ?, \"isActive\" = ?,\"id_GfunctionType\"=?,\"id_Origin\"=?,"
				+ " \"id_Premise\" = ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				logger.info("company id in dao impl = "+pigInfo.getCompanyId());
				
				ps.setString(1, pigInfo.getPigId());
				ps.setString(2, pigInfo.getSireId());
				ps.setString(3, pigInfo.getDamId());
				ps.setString(4, pigInfo.getOrigin());
				if(pigInfo.getGline() != null  && pigInfo.getGline() != 0)
					ps.setObject(5, pigInfo.getGline(), java.sql.Types.INTEGER);
				else
					ps.setNull(5, java.sql.Types.INTEGER);
				if(pigInfo.getGcompany() != null  && pigInfo.getGcompany() != 0)
					ps.setObject(6, pigInfo.getGcompany(), java.sql.Types.INTEGER);
				else
					ps.setNull(6, java.sql.Types.INTEGER);
				if(pigInfo.getBirthDate() != null)
					ps.setDate(7,  new java.sql.Date(pigInfo.getBirthDate().getTime()));
				else
					ps.setNull(7, java.sql.Types.DATE);
				ps.setString(8, pigInfo.getTattoo());
				ps.setString(9, pigInfo.getAlternateTattoo());
				ps.setString(10, pigInfo.getRemarks());				
				ps.setString(11, pigInfo.getUserUpdated());
				ps.setInt(12, pigInfo.getCompanyId());
				if(pigInfo.getRoomId() != null && pigInfo.getRoomId() != 0)
					ps.setObject(13, pigInfo.getRoomId());
				else
					ps.setNull(13, java.sql.Types.INTEGER);
				if(pigInfo.getBarnId() != null  && pigInfo.getBarnId() != 0)
					ps.setObject(14, pigInfo.getBarnId(), java.sql.Types.INTEGER);
				else
					ps.setNull(14, java.sql.Types.INTEGER);
				ps.setObject(15, pigInfo.getSexTypeId(), java.sql.Types.INTEGER);		
				ps.setDate(16,  new java.sql.Date(pigInfo.getEntryDate().getTime()));
				ps.setBoolean(17,pigInfo.isActive());
				if(pigInfo.getGfunctionTypeId() != null  && pigInfo.getGfunctionTypeId() != 0)
					ps.setObject(18, pigInfo.getGfunctionTypeId(), java.sql.Types.INTEGER);
				else
					ps.setNull(18, java.sql.Types.INTEGER);
				if(pigInfo.getOriginId() != null  && pigInfo.getOriginId() != 0)
					ps.setObject(19, pigInfo.getOriginId(), java.sql.Types.INTEGER);
				else
					ps.setNull(19, java.sql.Types.INTEGER);
				if(pigInfo.getPremiseId() != null  && pigInfo.getPremiseId() != 0)
					ps.setObject(20, pigInfo.getPremiseId(), java.sql.Types.INTEGER);
				else
					ps.setNull(20, java.sql.Types.INTEGER);
				ps.setInt(21, pigInfo.getId());
			}
		});
		
	}
	

}
