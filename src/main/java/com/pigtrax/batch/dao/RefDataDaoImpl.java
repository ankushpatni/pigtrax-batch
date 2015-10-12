package com.pigtrax.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pigtrax.batch.beans.RefDataTranslation;
import com.pigtrax.batch.dao.interfaces.RefDataDao;

@Repository
public class RefDataDaoImpl implements RefDataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<RefDataTranslation> getSexData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SexType\" FROM pigtraxrefdata.\"SexTypeTranslation\" order by \"fieldLanguage\", \"id_SexType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

	@Override
	public List<RefDataTranslation> getGfunctionType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GfunctionType\" FROM pigtraxrefdata.\"GfunctionTypeTranslation\" order by \"fieldLanguage\", \"id_GfunctionType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

	private final static class CacheRefDataRowMaper implements RowMapper<RefDataTranslation> {
		@Override
		public RefDataTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
			String fieldValue = rs.getString(1);
			String fieldLanguage = rs.getString(2);
			Integer fieldCode = rs.getInt(3);
			return new RefDataTranslation(fieldLanguage, fieldValue, fieldCode);
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}