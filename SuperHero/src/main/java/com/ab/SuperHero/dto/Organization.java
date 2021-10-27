package com.ab.SuperHero.dto;

import java.util.List;
import java.util.Objects;

public class Organization {
	
	private int organizationId;
	private String organizationName;
	private String contact;
	private String description;
	private int membersNumber;
	private int locationId;
	private int heroId;
	private List<Hero> heroes;
	
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMembersNumber() {
		return membersNumber;
	}
	public void setMembersNumber(int membersNumber) {
		this.membersNumber = membersNumber;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public List<Hero> getHeroes() {
		return heroes;
	}
	public void setHeroes(List<Hero> heroes) {
		this.heroes = heroes;
	}
	@Override
	public int hashCode() {
		return Objects.hash(contact, description, heroId, heroes, locationId, membersNumber, organizationId,
				organizationName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		return Objects.equals(contact, other.contact) && Objects.equals(description, other.description)
				&& heroId == other.heroId && Objects.equals(heroes, other.heroes) && locationId == other.locationId
				&& membersNumber == other.membersNumber && organizationId == other.organizationId
				&& Objects.equals(organizationName, other.organizationName);
	}
	
	
}
