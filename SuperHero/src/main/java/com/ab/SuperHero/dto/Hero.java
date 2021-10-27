package com.ab.SuperHero.dto;

import java.util.List;
import java.util.Objects;

public class Hero {

	private int heroId;
	private String heroName;
	private String heroDescription;
	private boolean isHero;
	private int superPowerId;
	private int organizationId;
	private List<Organization> organizations;
	private List<Sighting> sightings;
	
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public String getHeroName() {
		return heroName;
	}
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	public String getHeroDescription() {
		return heroDescription;
	}
	public void setHeroDescription(String heroDescription) {
		this.heroDescription = heroDescription;
	}
	public boolean isHero() {
		return isHero;
	}
	public void setHero(boolean isHero) {
		this.isHero = isHero;
	}
	public int getSuperPowerId() {
		return superPowerId;
	}
	public void setSuperPowerId(int superPowerId) {
		this.superPowerId = superPowerId;
	}
	
	public List<Organization> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
	public List<Sighting> getSightings() {
		return sightings;
	}
	public void setSightings(List<Sighting> sightings) {
		this.sightings = sightings;
	}
	
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(heroDescription, heroId, heroName, isHero, organizationId, organizations, sightings,
				superPowerId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		return Objects.equals(heroDescription, other.heroDescription) && heroId == other.heroId
				&& Objects.equals(heroName, other.heroName) && isHero == other.isHero
				&& organizationId == other.organizationId && Objects.equals(organizations, other.organizations)
				&& Objects.equals(sightings, other.sightings) && superPowerId == other.superPowerId;
	}
	
	
	
	
	
}
