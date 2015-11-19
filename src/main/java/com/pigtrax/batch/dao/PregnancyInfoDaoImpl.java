package com.pigtrax.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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

import com.pigtrax.batch.beans.PregnancyInfo;
import com.pigtrax.batch.dao.interfaces.PregnancyInfoDao;

@Repository
public class PregnancyInfoDaoImpl implements PregnancyInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);

	@Override
	public int insertPregnancyInfo(final PregnancyInfo pregnancyInfo) throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PregnancyEvent\"(\"id_PigInfo\", \"id_BreedingEvent\", "
				+ "\"id_EmployeeGroup\", \"id_PregnancyEventType\", \"id_PregnancyExamResultType\", \"examDate\", \"resultDate\", "
				+ "\"sowCondition\", \"lastUpdated\", \"userUpdated\") " + "values(?,?,?,?,?,?,?,?,current_timestamp,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry, new String[] { "id" });
				ps.setInt(1, pregnancyInfo.getPigInfoId());
				ps.setInt(2, pregnancyInfo.getBreedingEventId());
				ps.setObject(3, pregnancyInfo.getEmployeeGroupId(), java.sql.Types.INTEGER);
				ps.setObject(4, pregnancyInfo.getPregnancyEventTypeId(), java.sql.Types.INTEGER);
				ps.setObject(5, pregnancyInfo.getPregnancyExamResultTypeId(), java.sql.Types.INTEGER);
				ps.setObject(6, new java.sql.Date(pregnancyInfo.getExamDate().getTime()), java.sql.Types.DATE);
				ps.setDate(7, new java.sql.Date(pregnancyInfo.getResultDate().getTime()));
				ps.setInt(8, pregnancyInfo.getSowCondition());
				ps.setString(9, pregnancyInfo.getUserUpdated());
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}

	/**
	 * checkIfPregnancyEventExist for a given breedingEventId
	 * 
	 * @param breedingEventId
	 * @return
	 */
	public boolean checkIfPregnancyEventExist(final Integer breedingEventId, final Integer eventTypeId, final Integer resultTypeId) {
		String sql = "select \"id\" from pigtrax.\"PregnancyEvent\" where \"id_BreedingEvent\" = ? ";
				
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
			}
		}, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(1);
				}
				return null;
			}
		});
		if (retValList1 != null) {
			return true;
		} else
			return false;
	}

	@Override
	public Integer getPragnancyId(final Map<String, Object> creteriaMap) throws SQLException {
		Integer breedingEvenId = (Integer) creteriaMap.get("breedingEvenId");
		logger.debug("isPragnant ::creteriaMap is breedingEvenId:" + breedingEvenId);
		StringBuffer qryBuffer = new StringBuffer();
		qryBuffer
				.append("select \"id\" from pigtrax.\"PregnancyEvent\"  where \"id_BreedingEvent\" = ? and \"id_PregnancyEventType\" =1 and \"id_PregnancyExamResultType\"=1 ");
		final String qry = qryBuffer.toString();
		Long retValList1 = null;
		retValList1 = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (breedingEvenId != null) {
					ps.setInt(1, breedingEvenId);
				} else {
					ps.setNull(1, java.sql.Types.INTEGER);
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
		logger.debug("isPragnant::isPragnant retVal is :" + retValList1);
		if (retValList1 != null) {
			return Integer.decode(retValList1.toString());
		}
		return null;
	}
	
	@Override
	public List<PregnancyInfo> getOpenPregnancyRecords(Integer pigInfoId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select PE.* from pigtrax.\"PregnancyEvent\" PE where PE.\"id_PregnancyEventType\" = ? and PE.\"id_PregnancyExamResultType\" = ? "
				+ "and PE.\"id_PigInfo\" = ? and PE.\"id\" not in (select FE.\"id_PregnancyEvent\" from pigtrax.\"FarrowEvent\" FE where FE.\"id_PigInfo\" = ?) order by PE.\"id\" desc");
		List<PregnancyInfo> pregnancyEventList = jdbcTemplate.query(buffer.toString(), new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, 1);
				ps.setInt(2, 1);
				ps.setInt(3, pigInfoId);
				ps.setInt(4, pigInfoId);
			}}, new PregnancyInfoRowMapper());
		
		return pregnancyEventList;
		
	}
	
	private static final class PregnancyInfoRowMapper implements RowMapper<PregnancyInfo> {
		public PregnancyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PregnancyInfo info = new PregnancyInfo();
			info.setId(rs.getInt("id"));
			info.setPigInfoId(rs.getInt("id_PigInfo"));
			info.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			info.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			info.setPregnancyEventTypeId(rs.getInt("id_PregnancyEventType"));
			info.setPregnancyExamResultTypeId(rs.getInt("id_PregnancyExamResultType"));
			info.setExamDate(rs.getDate("examDate"));
			info.setResultDate(rs.getDate("resultDate"));
			info.setSowCondition(rs.getInt("sowCondition"));
			info.setUserUpdated(rs.getString("userUpdated"));
			return info;
		}
	}
}
