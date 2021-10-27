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
class LocationDaoDBTest {
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
	
	public LocationDaoDBTest() {
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
	void testAddAndGetLocation() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Location fromDao = locationDao.getLocationById(location.getLocationId());
		
		assertEquals(location.getLocationName(), fromDao.getLocationName());
	}
	
	@Test
	public void testGetAllLocations() {
		Location location1 = new Location();
		location1.setLocationName("home");
		location1.setStreet("Stub str.");
		location1.setCity("Ville");
		location1.setZip("12345");
		location1.setDescription("descript");
		location1.setLatitude(45.546544);
		location1.setLongitude(-73.451124);
		location1 = locationDao.addLocation(location1);
		
		Location location2 = new Location();
		location2.setLocationName("home");
		location2.setStreet("Stub str.");
		location2.setCity("Ville");
		location2.setZip("12345");
		location2.setDescription("descript");
		location2.setLatitude(45.546544);
		location2.setLongitude(-73.451124);
		location2 = locationDao.addLocation(location2);
		
		List<Location> locations = locationDao.getAllLocation();
		
		assertEquals(2, locations.size());
		assertEquals(location1.getLocationName(), locations.get(0).getLocationName());
		assertEquals(location2.getLocationName(), locations.get(1).getLocationName());
	}
	
	@Test
	public void testUpdateLocations() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Location fromDao = locationDao.getLocationById(location.getLocationId());
		assertEquals(location.getLocationName(), fromDao.getLocationName());
		
		location.setLocationName("new location");
		locationDao.updateLocation(location);
		
		assertNotEquals(location.getLocationName(), fromDao.getLocationName());
		
		fromDao = locationDao.getLocationById(location.getLocationId());
		assertEquals(location.getLocationName(), fromDao.getLocationName());	
	}
	
	@Test
	public void testDeleteLocation() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Location fromDao = locationDao.getLocationById(location.getLocationId());
		assertEquals(location.getLocationName(), fromDao.getLocationName());
		
		locationDao.deleteLocation(location.getLocationId());
		
		fromDao = locationDao.getLocationById(location.getLocationId());
		assertNull(fromDao);
	}
	
	@Test
	public void testGetAllLocationsByHero() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Location location1 = new Location();
		location1.setLocationName("home");
		location1.setStreet("Stub str.");
		location1.setCity("Ville");
		location1.setZip("12345");
		location1.setDescription("descript");
		location1.setLatitude(45.546544);
		location1.setLongitude(-73.451124);
		location1 = locationDao.addLocation(location1);
		
		Location location2 = new Location();
		location2.setLocationName("home");
		location2.setStreet("Stub str.");
		location2.setCity("Ville");
		location2.setZip("12345");
		location2.setDescription("descript");
		location2.setLatitude(45.546544);
		location2.setLongitude(-73.451124);
		location2 = locationDao.addLocation(location2);
		
		
		Hero hero1 = new Hero();
		hero1.setHeroName("Test Name");
		hero1.setHeroDescription("Test Hero description");
		hero1.setHero(true);
		hero1.setOrganizations(new ArrayList<>());
		//hero1.setSightings(sightings1);
		hero1.setSuperPowerId(power.getPowerId());
		hero1 = heroDao.addHero(hero1);
		
		List<Hero> heroes1 = new ArrayList<>();
		heroes1.add(hero1);
		
		Hero hero2 = new Hero();
		hero2.setHeroName("Test Name");
		hero2.setHeroDescription("Test Hero description");
		hero2.setHero(true);
		hero2.setOrganizations(new ArrayList<>());
		//hero2.setSightings(sightings2);
		hero2.setSuperPowerId(power.getPowerId());
		hero2 = heroDao.addHero(hero2);
		
		List<Hero> heroes2 = new ArrayList<>();
		heroes1.add(hero1);
		heroes2.add(hero2);
		
		Sighting sighting1 = new Sighting();
		sighting1.setDate(LocalDate.parse("2021-10-25"));
		sighting1.setLocationId(location1.getLocationId());
		sighting1.setHeroes(heroes1);
		sighting1 = sightingDao.addSighting(sighting1);
		
		List<Sighting> sightings1 = new ArrayList<>();
		sightings1.add(sighting1);
		
		Sighting sighting2 = new Sighting();
		sighting2.setDate(LocalDate.parse("2021-10-25"));
		sighting2.setLocationId(location2.getLocationId());
		sighting2.setHeroes(heroes2);
		sighting2 = sightingDao.addSighting(sighting2);
		
		List<Sighting> sightings2 = new ArrayList<>();
		sightings2.add(sighting1);
		sightings2.add(sighting2);
		
		List<Location> locations1 = locationDao.getAllLocationsByHero(hero1.getHeroId());
		assertEquals(2, locations1.size());
		
		List<Location> locations2 = locationDao.getAllLocationsByHero(hero2.getHeroId());
		assertEquals(1, locations2.size());
		
	}
}
