package com.ab.SuperHero.dao;

import java.util.List;

import com.ab.SuperHero.dto.Organization;

public interface OrganizationDao {

	Organization addOrganization(Organization organization);

	void updateOrganization(Organization organization);

	void deleteOrganization(int organizationId);

	Organization getOrganizationById(int organizationId);

	List<Organization> getAllOrganization();

	List<Organization> getAllOrganizationByHero(int heroId);

	List<Organization> getAllOrganizationByLocation(int locationId);
}
