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
public class OrganizationDaoDB implements OrganizationDao{
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional
	public Organization addOrganization(Organization organization) {
		final String INSERT_ORGANIZATION = "INSERT INTO Organization"
				+ "(organizationName, contact, organizationDescription, membersNumber, Location_locationId)"
				+ "VALUES(?,?,?,?,?)";
		jdbc.update(INSERT_ORGANIZATION,
				organization.getOrganizationName(),
				organization.getContact(),
				organization.getDescription(),
				organization.getMembersNumber(),
				organization.getLocationId());
		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		organization.setOrganizationId(newId);
		insertOrganizationHero(organization);
		return organization;
	}
	private void insertOrganizationHero(Organization organization) {
		final String INSERT_ORGANIZATION_HERO = "INSERT INTO"
				+ "organizationHero(Hero_heroId, Organization_organizationId) VALUES(?,?)";
		for(Hero hero : organization.getHeroes()) {
			jdbc.update(INSERT_ORGANIZATION_HERO,
					hero.getHeroId(),
					organization.getOrganizationId());
		}
	}
	
	@Override
	@Transactional
	public void updateOrganization(Organization organization) {
		final String UPDATE_ORGANIZATION = "UPDATE Organization SET"
				+ "organizationName = ?, contact = ?, "
				+ "organizationDescription = ?, membersNumber = ?, "
				+ "Location_locationId = ? WHERE organizationId = ?";
		jdbc.update(UPDATE_ORGANIZATION,
				organization.getOrganizationName(),
				organization.getContact(),
				organization.getDescription(),
				organization.getMembersNumber(),
				organization.getLocationId(),
				organization.getOrganizationId());
		final String DELETE_ORGANIZATION_HERO = "DELETE FROM organizationHero "
				+ "WHERE organizationId = ?";
		jdbc.update(DELETE_ORGANIZATION_HERO, organization.getOrganizationId());
		insertOrganizationHero(organization);
	}

	@Override
	@Transactional
	public void deleteOrganization(int organizationId) {
		final String DELETE_ORGANIZATION_HERO = "DELETE FROM organizationHero "
				+ "WHERE organizationId = ?";
		jdbc.update(DELETE_ORGANIZATION_HERO, organizationId);
		final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE organizationId = ?";
		jdbc.update(DELETE_ORGANIZATION, organizationId);
	}

	@Override
	public Organization getOrganizationById(int organizationId) {
		try {
			final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organization WHERE organizationId = ?";
			return jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), organizationId);
		}catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Organization> getAllOrganization() {
		final String SELECT_ALL_ORGANIZATION = "SELECT * FROM Organization";
		return jdbc.query(SELECT_ALL_ORGANIZATION, new OrganizationMapper());
	}

	@Override
	public List<Organization> getAllOrganizationByHero(int heroId) {
		final String SELECT_ORGANIZATION_BY_HERO = "SELECT * FROM Organization o"
				+ "JOIN organizationHero oh"
				+ "ON oh.Organization_organizationId = o.organizationId"
				+ "WHERE oh.Hero_heroId = ?";
		return jdbc.query(SELECT_ORGANIZATION_BY_HERO, new OrganizationMapper(), heroId);
	}

	@Override
	public List<Organization> getAllOrganizationByLocation(int locationId) {
		final String SELECT_ORGANIZATION_BY_LOCATION = "SELECT * FROM Organization WHERE Location_locationId = ?";
		return jdbc.query(SELECT_ORGANIZATION_BY_LOCATION, new OrganizationMapper(), locationId);
	}
	
	private static final class OrganizationMapper implements RowMapper<Organization>{

		@Override
		public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
			Organization organization = new Organization();
			organization.setOrganizationId(rs.getInt("organizationId"));
			organization.setOrganizationName(rs.getString("organizationName"));
			organization.setContact(rs.getString("contact"));
			organization.setDescription(rs.getString("organizationDescription"));
			organization.setMembersNumber(rs.getInt("membersNumber"));
			organization.setLocationId(rs.getInt("Location_locationId"));
			return organization;
		}
		
	}
	
	
	
	
	
}
