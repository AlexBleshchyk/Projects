package com.ab.SuperHero.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ab.SuperHero.dao.HeroDaoDB.HeroMapper;
import com.ab.SuperHero.dto.Hero;
import com.ab.SuperHero.dto.Sighting;

@Repository
public class SightingDaoDB implements SightingDao{
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional
	public Sighting addSighting(Sighting sighting) {
		final String INSERT_SIGHTING = "INSERT INTO Sighting "
				+ "(sightDate,Location_locationId)"
				+ "VALUES(?,?)";
		jdbc.update(INSERT_SIGHTING,
				sighting.getDate(),
				sighting.getLocationId());
		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		sighting.setSightingId(newId);
		insertHeroSighting(sighting);
		return sighting;
	}
	private void insertHeroSighting(Sighting sighting) {
		final String INSERT_HERO_SIGHTING = "INSERT INTO "
				+ "heroSighting(Hero_heroId, Sighting_sightingId, Sighting_Location_locationId) "
				+ "VALUES(?,?,?)";
		for(Hero hero : sighting.getHeroes()) {
			jdbc.update(INSERT_HERO_SIGHTING,
					hero.getHeroId(),
					sighting.getSightingId(),
					sighting.getLocationId());
		}
	}

	@Override
	@Transactional
	public void updateSighting(Sighting sighting) {
		final String UPDATE_SIGHTING = "UPDATE Sighting SET "
				+ "sightDate = ?, Location_locationId = ? WHERE sightingId = ?";
		jdbc.update(UPDATE_SIGHTING,
				sighting.getDate(),
				sighting.getLocationId(),
				sighting.getSightingId());
		final String DELETE_HERO_SIGHTING = "DELETE FROM heroSighting "
				+ "WHERE Sighting_sightingId = ?";
		jdbc.update(DELETE_HERO_SIGHTING, sighting.getSightingId());
		insertHeroSighting(sighting);
	}

	@Override
	@Transactional
	public void deleteSighting(int sightingId) {
		final String DELETE_SIGHTING_HERO = "DELETE FROM heroSighting "
				+ "WHERE Sighting_sightingId = ?";
		jdbc.update(DELETE_SIGHTING_HERO, sightingId);
		final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sightingId = ?";
		jdbc.update(DELETE_SIGHTING, sightingId);	
	}

	@Override
	public Sighting getSightingById(int sightingId) {
		try{
			final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sightingId = ?";
			return jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
		}catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Sighting> getAllSightings() {
		final String SELECT_ALL_SIGHTING = "SELECT * FROM sighting";
		return jdbc.query(SELECT_ALL_SIGHTING, new SightingMapper());
	}

	@Override
	public List<Sighting> getAllSightingByDate(LocalDate date) {
		final String SELECT_SIGHTING_BY_DATE = "SELECT * FROM Sighting s "
				+ "JOIN heroSighting hs "
				+ "ON hs.Sighting_sightingId = s.sightingId "
				+ "JOIN Hero h "
				+ "ON h.heroId = hs.Hero_heroId "
				+ "JOIN Location l "
				+ "ON l.locationId = hs.Sighting_Location_locationId "
				+ "WHERE sightDate = ?";
		return jdbc.query(SELECT_SIGHTING_BY_DATE, new SightingMapper(), date);
	}

	@Override
	public List<Sighting> getAllSightingByHero(int heroId) {
		final String SELECT_SIGHTING_BY_HERO = "SELECT * FROM Sighting s "
				+ "JOIN heroSighting hs "
				+ "ON hs.Sighting_sightingId = s.sightingId "
				+ "WHERE hs.Hero_heroId = ?";
		return jdbc.query(SELECT_SIGHTING_BY_HERO, new SightingMapper(), heroId);
	}

	@Override
	public List<Sighting> getAllSightingByLocation(int locationId) {
		final String SELECT_SIGHTING_BY_LOCATION = "SELECT * FROM Sighting "
				+ "WHERE Location_locationId = ?";
		return jdbc.query(SELECT_SIGHTING_BY_LOCATION, new SightingMapper(), locationId);
	}

	@Override
	public List<Hero> getAllHeroesBySighting(int sightingId) {
		final String SELECT_HERO_BY_SIGHTING = "SELECT * FROM Hero h "
				+ "JOIN heroSighting hs "
				+ "ON hs.Hero_heroId = h.heroId "
				+ "WHERE hs.Sighting_sightingId = ?";
		return jdbc.query(SELECT_HERO_BY_SIGHTING, new HeroMapper(), sightingId);
	}

	@Override
	public List<Hero> getAllHeroesByLocation(int locationId) {
		final String SELECT_HERO_BY_LOCATION = "SELECT * FROM Hero h "
				+ "JOIN heroSighting hs "
				+ "ON hs.Hero_heroId = h.heroId "
				+ "WHERE hs.Sighting_Location_locationId = ?";
		return jdbc.query(SELECT_HERO_BY_LOCATION, new HeroMapper(), locationId);
	}
	
	
	private static final class SightingMapper implements RowMapper<Sighting>{

		@Override
		public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sighting sighting = new Sighting();
			sighting.setSightingId(rs.getInt("sightingId"));
			sighting.setDate(rs.getDate("sightDate").toLocalDate());
			sighting.setLocationId(rs.getInt("Location_locationId"));
			return sighting;
		}
		
	}



	
}
