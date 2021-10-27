package com.ab.SuperHero.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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
class HeroDaoDBTest {
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
	
	public HeroDaoDBTest() {
	}
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
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
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddAndGetHero() {
		List<Hero> heroes = new ArrayList<>();
		
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Organization organization = new Organization();
		organization.setOrganizationName("testX");
		organization.setContact("contact");
		organization.setDescription("org. desr.");
		organization.setMembersNumber(5);
		organization.setLocationId(location.getLocationId());
		organization.setHeroes(heroes);
		organization = organizationDao.addOrganization(organization);
		
		List<Organization> organizations = new ArrayList<>();
		organizations.add(organization);
		
		
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(heroes);
		sighting = sightingDao.addSighting(sighting);
		
		List<Sighting> sightings = new ArrayList<>();
		sightings.add(sighting);
		
		Hero hero = new Hero();
		hero.setHeroName("Test Name");
		hero.setHeroDescription("Test Hero description");
		hero.setHero(true);
		hero.setOrganizations(organizations);
		hero.setSightings(sightings);
		hero.setSuperPowerId(power.getPowerId());
		hero = heroDao.addHero(hero);
		
		heroes.add(hero);
		
		Hero fromDao = heroDao.getHeroById(hero.getHeroId());
		
		assertEquals(hero.getHeroId(), fromDao.getHeroId());
	}

	@Test
	public void testGetAllHeroes() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Hero hero1 = new Hero();
		hero1.setHeroName("Test Name");
		hero1.setHeroDescription("Test Hero description");
		hero1.setHero(true);
		hero1.setOrganizations(new ArrayList<>());
		hero1.setSightings(new ArrayList<>());
		hero1.setSuperPowerId(power.getPowerId());
		hero1 = heroDao.addHero(hero1);
		
		Hero hero2 = new Hero();
		hero2.setHeroName("Test Name2");
		hero2.setHeroDescription("Test Hero description");
		hero2.setHero(true);
		hero2.setOrganizations(new ArrayList<>());
		hero2.setSightings(new ArrayList<>());
		hero2.setSuperPowerId(power.getPowerId());
		hero2 = heroDao.addHero(hero2);
		
		List<Hero> heroes = heroDao.getAllHeroes();
		
		assertEquals(2, heroes.size());
		assertEquals(hero1.getHeroName(), heroes.get(0).getHeroName());
		assertEquals(hero2.getHeroName(), heroes.get(1).getHeroName());
	}
	
	@Test
	public void testUpdateHero() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Hero hero = new Hero();
		hero.setHeroName("Test Name");
		hero.setHeroDescription("Test Hero description");
		hero.setHero(true);
		hero.setOrganizations(new ArrayList<>());
		hero.setSightings(new ArrayList<>());
		hero.setSuperPowerId(power.getPowerId());
		hero = heroDao.addHero(hero);
		
		Hero fromDao = heroDao.getHeroById(hero.getHeroId());
		assertEquals(hero.getHeroId(), fromDao.getHeroId());
		
		hero.setHeroName("New test Name");
		heroDao.updateHero(hero);
		
		assertNotEquals(hero.getHeroName(), fromDao.getHeroName());
		
		fromDao = heroDao.getHeroById(hero.getHeroId());
		assertEquals(hero.getHeroId(), fromDao.getHeroId());
	}
	
	@Test
	public void testDeleteHeroById() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Hero hero = new Hero();
		hero.setHeroName("Test Name");
		hero.setHeroDescription("Test Hero description");
		hero.setHero(true);
		hero.setOrganizations(new ArrayList<>());
		hero.setSightings(new ArrayList<>());
		hero.setSuperPowerId(power.getPowerId());
		hero = heroDao.addHero(hero);
		
		Hero fromDao = heroDao.getHeroById(hero.getHeroId());
		assertEquals(hero.getHeroId(), fromDao.getHeroId());
		
		heroDao.deleteHeroById(hero.getHeroId());
		
		fromDao = heroDao.getHeroById(hero.getHeroId());
		assertNull(fromDao);
	}
	
	@Test
	public void testGetAllHeroesByPower() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
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
		
		List<Hero> heroes = heroDao.getAllHeroesByPower(power.getPowerId());
		assertEquals(2, heroes.size());
	}
	
	@Test
	public void testGetAllHeroesByOrganization() {
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
		
		Organization organization = new Organization();
		organization.setOrganizationName("testX");
		organization.setContact("contact");
		organization.setDescription("org. desr.");
		organization.setMembersNumber(5);
		organization.setLocationId(location.getLocationId());
		organization.setHeroes(new ArrayList<>());
		organization = organizationDao.addOrganization(organization);
		
		List<Organization> organizations = new ArrayList<>();
		organizations.add(organization);
		
		Hero hero1 = new Hero();
		hero1.setHeroName("Test Name");
		hero1.setHeroDescription("Test Hero description");
		hero1.setHero(true);
		hero1.setOrganizations(organizations);
		hero1.setSightings(new ArrayList<>());
		hero1.setSuperPowerId(power.getPowerId());
		hero1 = heroDao.addHero(hero1);
		
		Hero hero2 = new Hero();
		hero2.setHeroName("Test Name");
		hero2.setHeroDescription("Test Hero description");
		hero2.setHero(true);
		hero2.setOrganizations(organizations);
		hero2.setSightings(new ArrayList<>());
		hero2.setSuperPowerId(power.getPowerId());
		hero2 = heroDao.addHero(hero2);
		
		List<Hero> heroes = heroDao.getAllHeroesByOrganization(organization.getOrganizationId());
		assertEquals(2, heroes.size());
	}
	
//	@Test
//	public void testGetAllHeroesBySighting() {
//		Power power = new Power();
//		power.setPowerName("test power");
//		power.setPowerDescription("test power description");
//		power = powerDao.addPower(power);
//		
//		Location location = new Location();
//		location.setLocationName("home");
//		location.setStreet("Stub str.");
//		location.setCity("Ville");
//		location.setZip("12345");
//		location.setDescription("descript");
//		location.setLatitude(45.546544);
//		location.setLongitude(-73.451124);
//		location = locationDao.addLocation(location);
//		
//		Sighting sighting = new Sighting();
//		sighting.setDate(LocalDate.parse("2021-10-25"));
//		sighting.setLocationId(location.getLocationId());
//		sighting.setHeroes(new ArrayList<>());
//		sighting = sightingDao.addSighting(sighting);
//		
//		List<Sighting> sightings = new ArrayList<>();
//		sightings.add(sighting);
//		
//		Hero hero1 = new Hero();
//		hero1.setHeroName("Test Name");
//		hero1.setHeroDescription("Test Hero description");
//		hero1.setHero(true);
//		hero1.setOrganizations(new ArrayList<>());
//		hero1.setSightings(sightings);
//		hero1.setSuperPowerId(power.getPowerId());
//		hero1 = heroDao.addHero(hero1);
//		
//		Hero hero2 = new Hero();
//		hero2.setHeroName("Test Name");
//		hero2.setHeroDescription("Test Hero description");
//		hero2.setHero(true);
//		hero2.setOrganizations(new ArrayList<>());
//		hero2.setSightings(sightings);
//		hero2.setSuperPowerId(power.getPowerId());
//		hero2 = heroDao.addHero(hero2);
//		
//		List<Hero> heroes = heroDao.getAllHeroesBySighting(sighting.getSightingId());
//		assertEquals(2, heroes.size());	
//	}
	
//	@Test
//	public void testGetAllHeroesByLocation() {
//		Power power = new Power();
//		power.setPowerName("test power");
//		power.setPowerDescription("test power description");
//		power = powerDao.addPower(power);
//		
//		Location location = new Location();
//		location.setLocationName("home");
//		location.setStreet("Stub str.");
//		location.setCity("Ville");
//		location.setZip("12345");
//		location.setDescription("descript");
//		location.setLatitude(45.546544);
//		location.setLongitude(-73.451124);
//		location = locationDao.addLocation(location);
//		
//		Sighting sighting = new Sighting();
//		sighting.setDate(LocalDate.parse("2021-10-25"));
//		sighting.setLocationId(location.getLocationId());
//		sighting.setHeroes(new ArrayList<>());
//		sighting = sightingDao.addSighting(sighting);
//		
//		List<Sighting> sightings = new ArrayList<>();
//		sightings.add(sighting);
//		
//		Hero hero1 = new Hero();
//		hero1.setHeroName("Test Name");
//		hero1.setHeroDescription("Test Hero description");
//		hero1.setHero(true);
//		hero1.setOrganizations(new ArrayList<>());
//		hero1.setSightings(sightings);
//		hero1.setSuperPowerId(power.getPowerId());
//		hero1 = heroDao.addHero(hero1);
//		
//		Hero hero2 = new Hero();
//		hero2.setHeroName("Test Name");
//		hero2.setHeroDescription("Test Hero description");
//		hero2.setHero(true);
//		hero2.setOrganizations(new ArrayList<>());
//		hero2.setSightings(sightings);
//		hero2.setSuperPowerId(power.getPowerId());
//		hero2 = heroDao.addHero(hero2);
//		
//		List<Hero> heroes = heroDao.getAllHeroesByLocation(location.getLocationId());
//		
//		assertEquals(2, heroes.size());
//		
//	}
	
	
}
