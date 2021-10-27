package com.ab.SuperHero.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ab.SuperHero.dto.Hero;
import com.ab.SuperHero.dto.Location;
import com.ab.SuperHero.dto.Organization;
import com.ab.SuperHero.dto.Power;
import com.ab.SuperHero.dto.Sighting;

@SpringBootTest
class OrganizationDaoDBTest {
	@Autowired
	HeroDao heroDao;
	@Autowired
	OrganizationDao organizationDao;
	@Autowired
	LocationDao locationDao;
	@Autowired
	PowerDao powerDao;
	@Autowired
	SightingDao sightingDao;
	
	public OrganizationDaoDBTest() {
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		List<Hero> heroes = heroDao.getAllHeroes();
		for(Hero hero : heroes) {
			heroDao.deleteHeroById(hero.getHeroId());
		}
		
		List<Organization> organizations = organizationDao.getAllOrganization();
		for (Organization organization : organizations) {
			organizationDao.deleteOrganization(organization.getOrganizationId());
		}
		
		List<Power> powers = powerDao.getAllPower();
		for (Power power : powers) {
			powerDao.deletePower(power.getPowerId());
		}
		
		List<Sighting> sightings = sightingDao.getAllSightings();
		for (Sighting sighting : sightings) {
			sightingDao.deleteSighting(sighting.getSightingId());
		}
		
		List<Location> locations = locationDao.getAllLocation();
		for (Location location : locations) {
			locationDao.deleteLocation(location.getLocationId());
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddAndGetOrganization() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
				
		Organization organization = new Organization();
		organization.setOrganizationName("testX");
		organization.setContact("contact");
		organization.setDescription("org. desr.");
		organization.setMembersNumber(5);
		organization.setLocationId(location.getLocationId());
		organization.setHeroes(new ArrayList<>());
		organization = organizationDao.addOrganization(organization);
		
		Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
		
		assertEquals(organization.getOrganizationName(), fromDao.getOrganizationName());
	}
	
	@Test
	public void testGetAllOrganizations() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Organization organization1 = new Organization();
		organization1.setOrganizationName("testX");
		organization1.setContact("contact");
		organization1.setDescription("org. desr.");
		organization1.setMembersNumber(5);
		organization1.setLocationId(location.getLocationId());
		organization1.setHeroes(new ArrayList<>());
		organization1 = organizationDao.addOrganization(organization1);
		
		Organization organization2 = new Organization();
		organization2.setOrganizationName("testX");
		organization2.setContact("contact");
		organization2.setDescription("org. desr.");
		organization2.setMembersNumber(5);
		organization2.setLocationId(location.getLocationId());
		organization2.setHeroes(new ArrayList<>());
		organization2 = organizationDao.addOrganization(organization2);
		
		List<Organization> organizations = organizationDao.getAllOrganization();
		
		assertEquals(2, organizations.size());
		assertEquals(organization1.getOrganizationName(), organizations.get(0).getOrganizationName());
		assertEquals(organization2.getOrganizationName(), organizations.get(1).getOrganizationName());
	}

	@Test
	public void testUpdateOrganization() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Organization organization = new Organization();
		organization.setOrganizationName("testX");
		organization.setContact("contact");
		organization.setDescription("org. desr.");
		organization.setMembersNumber(5);
		organization.setLocationId(location.getLocationId());
		organization.setHeroes(new ArrayList<>());
		organization = organizationDao.addOrganization(organization);
		
		Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
		assertEquals(organization.getOrganizationName(), fromDao.getOrganizationName());
		
		organization.setOrganizationName("new testX");
		organizationDao.updateOrganization(organization);
		
		assertNotEquals(organization.getOrganizationName(), fromDao.getOrganizationName());
		
		fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
		assertEquals(organization.getOrganizationName(), fromDao.getOrganizationName());
	}
	
	@Test
	public void testDeleteOrganization() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Organization organization = new Organization();
		organization.setOrganizationName("testX");
		organization.setContact("contact");
		organization.setDescription("org. desr.");
		organization.setMembersNumber(5);
		organization.setLocationId(location.getLocationId());
		organization.setHeroes(new ArrayList<>());
		organization = organizationDao.addOrganization(organization);
		
		Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
		assertEquals(organization.getOrganizationName(), fromDao.getOrganizationName());
		
		organizationDao.deleteOrganization(organization.getOrganizationId());
		
		fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
		assertNull(fromDao);
	}
	
	@Test
	public void testGetAllOrganizationByHero() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Hero hero1 = new Hero();
		hero1.setHeroName("Test Name");
		hero1.setHeroDescription("Test Hero description");
		hero1.setHero(true);
		hero1.setOrganizations(new ArrayList<>());
		hero1.setSightings(new ArrayList<>());
		hero1.setSuperPowerId(power.getPowerId());
		hero1 = heroDao.addHero(hero1);
		
		Hero hero2 = new Hero();
		hero2.setHeroName("Test Name");
		hero2.setHeroDescription("Test Hero description");
		hero2.setHero(true);
		hero2.setOrganizations(new ArrayList<>());
		hero2.setSightings(new ArrayList<>());
		hero2.setSuperPowerId(power.getPowerId());
		hero2 = heroDao.addHero(hero2);
		
		List<Hero> heroes1 = new ArrayList<>();
		heroes1.add(hero1);
		heroes1.add(hero2);
		List<Hero> heroes2 = new ArrayList<>();
		heroes2.add(hero1);
		
		Organization organization1 = new Organization();
		organization1.setOrganizationName("testX");
		organization1.setContact("contact");
		organization1.setDescription("org. desr.");
		organization1.setMembersNumber(5);
		organization1.setLocationId(location.getLocationId());
		organization1.setHeroes(heroes1);
		organization1 = organizationDao.addOrganization(organization1);
		
		Organization organization2 = new Organization();
		organization2.setOrganizationName("testX");
		organization2.setContact("contact");
		organization2.setDescription("org. desr.");
		organization2.setMembersNumber(5);
		organization2.setLocationId(location.getLocationId());
		organization2.setHeroes(heroes2);
		organization2 = organizationDao.addOrganization(organization2);
		
		List<Organization> organizations = organizationDao.getAllOrganizationByHero(hero1.getHeroId());
		assertEquals(2, organizations.size());
	}
	
	@Test
	public void testGetAllOrganizationByLocation() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Organization organization1 = new Organization();
		organization1.setOrganizationName("testX");
		organization1.setContact("contact");
		organization1.setDescription("org. desr.");
		organization1.setMembersNumber(5);
		organization1.setLocationId(location.getLocationId());
		organization1.setHeroes(new ArrayList<>());
		organization1 = organizationDao.addOrganization(organization1);
		
		Organization organization2 = new Organization();
		organization2.setOrganizationName("testX");
		organization2.setContact("contact");
		organization2.setDescription("org. desr.");
		organization2.setMembersNumber(5);
		organization2.setLocationId(location.getLocationId());
		organization2.setHeroes(new ArrayList<>());
		organization2 = organizationDao.addOrganization(organization2);
		
		List<Organization> organizations = organizationDao.getAllOrganizationByLocation(location.getLocationId());
		
		assertEquals(2, organizations.size());
	}
	
	
	
	
	
	
}
