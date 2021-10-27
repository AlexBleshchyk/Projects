package com.ab.SuperHero.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ab.SuperHero.dto.Hero;
import com.ab.SuperHero.dto.Organization;


@Repository
public class HeroDaoDB implements HeroDao{
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	@Transactional
	public Hero addHero(Hero hero) {
		final String INSERT_HERO = "INSERT INTO Hero(heroName, heroDescription, isHero, SuperPower_superPowerId)"
				+ "VALUES(?,?,?,?)";
		jdbc.update(INSERT_HERO,
				hero.getHeroName(),
				hero.getHeroDescription(),
				hero.isHero(),
				hero.getSuperPowerId());
		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		hero.setHeroId(newId);
		insertOrganizationHero(hero);
		//insertHeroSighting(hero);
		return hero;
	}
	private void insertOrganizationHero(Hero hero) {
		final String INSERT_ORGANIZATION_HERO = "INSERT INTO "
				+ "organizationHero(Organization_organizationId, Hero_heroId) VALUES(?,?)";
		
		for(Organization organization : hero.getOrganizations()) {
			jdbc.update(INSERT_ORGANIZATION_HERO,
					organization.getOrganizationId(),
					hero.getHeroId());
		}
	}
//	private void insertHeroSighting(Hero hero) {
//		final String INSERT_HERO_SIGHTING = "INSERT INTO "
//				+ "heroSighting(Hero_heroId, Sighting_sightingId, Sighting_Location_locationId) VALUES(?,?,?)";
//		for(Sighting sighting : hero.getSightings()) {
//			jdbc.update(INSERT_HERO_SIGHTING,
//					hero.getHeroId(),
//					sighting.getSightingId(),
//					sighting.getLocationId());
//		}
//	}

	@Override
	@Transactional
	public void updateHero(Hero hero) {
		final String DELETE_ORGANIZATION_HERO = "DELETE FROM organizationHero "
				+ "WHERE Hero_heroId = ?";
//		final String DELETE_HERO_SIGHTING = "DELETE FROM heroSighting "
//				+ "WHERE Hero_heroId = ?";
		jdbc.update(DELETE_ORGANIZATION_HERO, hero.getHeroId());
//		jdbc.update(DELETE_HERO_SIGHTING, hero.getHeroId());
		final String UPDATE_HERO = "UPDATE Hero SET "
				+ "heroName = ?, heroDescription = ?, "
				+ "isHero = ?, SuperPower_superPowerId = ? WHERE heroId = ?";
		jdbc.update(UPDATE_HERO,
				hero.getHeroName(),
				hero.getHeroDescription(),
				hero.isHero(),
				hero.getSuperPowerId(),
				hero.getHeroId());
		
		insertOrganizationHero(hero);
		//insertHeroSighting(hero);
	}

	@Override
	@Transactional
	public void deleteHeroById(int heroId) {
		final String DELETE_ORGANIZATION_HERO = "DELETE FROM organizationHero "
				+ "WHERE Hero_heroId = ?";
		jdbc.update(DELETE_ORGANIZATION_HERO, heroId);
		final String DELETE_HERO_SIGHTING = "DELETE FROM heroSighting "
				+ "WHERE Hero_heroId = ?";
		jdbc.update(DELETE_HERO_SIGHTING, heroId);
		final String DELETE_HERO = "DELETE FROM Hero WHERE heroId = ?";
		jdbc.update(DELETE_HERO, heroId);
	}

	@Override
	public Hero getHeroById(int heroId) {
		try {
			final String SELECT_HERO_BY_ID = "SELECT * FROM Hero WHERE heroId = ?";
			return jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), heroId);
		}catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Hero> getAllHeroes() {
		final String SELECT_ALL_HEROES = "SELECT * FROM Hero";
		return jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
	}

	@Override
	public List<Hero> getAllHeroesByPower(int powerId) {
		final String SELECT_HERO_BY_POWER = "SELECT * FROM Hero WHERE SuperPower_superPowerId = ?";
		return jdbc.query(SELECT_HERO_BY_POWER, new HeroMapper(), powerId);
	}

	@Override
	public List<Hero> getAllHeroesByOrganization(int organizationId) {
		final String SELECT_HERO_BY_ORGANIZATION = "SELECT * FROM Hero h "
				+ "JOIN organizationHero oh "
				+ "ON oh.Hero_heroId = h.heroId "
				+ "WHERE oh.Organization_organizationId = ?";
		return jdbc.query(SELECT_HERO_BY_ORGANIZATION, new HeroMapper(), organizationId);
	}


	
	public static final class HeroMapper implements RowMapper<Hero>{

		@Override
		public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
			Hero hero = new Hero();
			hero.setHeroId(rs.getInt("heroId"));
			hero.setHeroName(rs.getString("heroName"));
			hero.setHeroDescription(rs.getString("heroDescription"));
			hero.setHero(rs.getBoolean("isHero"));
			hero.setSuperPowerId(rs.getInt("SuperPower_superPowerId"));
			return hero;
		}
		
	}

}
