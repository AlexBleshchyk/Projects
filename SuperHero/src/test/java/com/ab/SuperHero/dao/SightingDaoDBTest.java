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
class SightingDaoDBTest {
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
	public void testAddAndGetSighting() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(new ArrayList<>());
		sighting = sightingDao.addSighting(sighting);
		
		Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
		assertEquals(sighting.getSightingId(), fromDao.getSightingId());
	}
	
	@Test
	public void testUpdateSighting() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(new ArrayList<>());
		sighting = sightingDao.addSighting(sighting);
		
		Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
		assertEquals(sighting.getSightingId(), fromDao.getSightingId());
		
		sighting.setDate(LocalDate.parse("2021-10-30"));
		sightingDao.updateSighting(sighting);
		
		assertNotEquals(sighting.getDate(), fromDao.getDate());
		
		fromDao = sightingDao.getSightingById(sighting.getSightingId());
		assertEquals(sighting.getSightingId(), fromDao.getSightingId());
	}
	
	@Test
	public void testDeleteSighting() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(new ArrayList<>());
		sighting = sightingDao.addSighting(sighting);
		
		Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
		assertEquals(sighting.getSightingId(), fromDao.getSightingId());
		
		sightingDao.deleteSighting(sighting.getSightingId());
		fromDao = sightingDao.getSightingById(sighting.getSightingId());
		assertNull(fromDao);	
	}
	
	@Test
	public void testGetAllSightings() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Sighting sighting1 = new Sighting();
		sighting1.setDate(LocalDate.parse("2021-10-25"));
		sighting1.setLocationId(location.getLocationId());
		sighting1.setHeroes(new ArrayList<>());
		sighting1 = sightingDao.addSighting(sighting1);
		
		Sighting sighting2 = new Sighting();
		sighting2.setDate(LocalDate.parse("2021-10-26"));
		sighting2.setLocationId(location.getLocationId());
		sighting2.setHeroes(new ArrayList<>());
		sighting2 = sightingDao.addSighting(sighting2);
		
		List<Sighting> sightings = sightingDao.getAllSightings();
		
		assertEquals(2, sightings.size());
		assertEquals(sighting1.getDate(), sightings.get(0).getDate());
		assertEquals(sighting2.getDate(), sightings.get(1).getDate());
	}
	
	@Test
	public void testGetAllSightingByDate() {
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
		
		List<Hero> heroes = new ArrayList<>();
		heroes.add(hero1);
		heroes.add(hero2);
		
		Sighting sighting1 = new Sighting();
		sighting1.setDate(LocalDate.parse("2021-10-25"));
		sighting1.setLocationId(location.getLocationId());
		sighting1.setHeroes(heroes);
		sighting1 = sightingDao.addSighting(sighting1);
		
		Sighting sighting2 = new Sighting();
		sighting2.setDate(LocalDate.parse("2021-10-26"));
		sighting2.setLocationId(location.getLocationId());
		sighting2.setHeroes(heroes);
		sighting2 = sightingDao.addSighting(sighting2);
		
		List<Sighting> sights = new ArrayList<>();
		sights.add(sighting1);
		sights.add(sighting2);
		
		
		List<Sighting> sightings = sightingDao.getAllSightingByDate(LocalDate.parse("2021-10-25"));
		
		assertEquals(2, sightings.size());
		assertEquals(sighting1.getDate(), sightings.get(0).getDate());
	}
	
	@Test
	public void testGetAllSightingByHero() {
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
		
		List<Hero> heroes1 = new ArrayList<>();
		heroes1.add(hero1);
		
		Hero hero2 = new Hero();
		hero2.setHeroName("Test Name2");
		hero2.setHeroDescription("Test Hero description");
		hero2.setHero(true);
		hero2.setOrganizations(new ArrayList<>());
		hero2.setSightings(new ArrayList<>());
		hero2.setSuperPowerId(power.getPowerId());
		hero2 = heroDao.addHero(hero2);
		
		List<Hero> heroes = heroDao.getAllHeroes();
		
		Sighting sighting1 = new Sighting();
		sighting1.setDate(LocalDate.parse("2021-10-25"));
		sighting1.setLocationId(location.getLocationId());
		sighting1.setHeroes(heroes1);
		sighting1 = sightingDao.addSighting(sighting1);
		
		Sighting sighting2 = new Sighting();
		sighting2.setDate(LocalDate.parse("2021-10-26"));
		sighting2.setLocationId(location.getLocationId());
		sighting2.setHeroes(heroes);
		sighting2 = sightingDao.addSighting(sighting2);
		
		List<Sighting> sightings = sightingDao.getAllSightingByHero(hero1.getHeroId());
		assertEquals(2, sightings.size());
	}

	@Test
	public void testGetAllSightingByLocation() {
		Location location = new Location();
		location.setLocationName("home");
		location.setStreet("Stub str.");
		location.setCity("Ville");
		location.setZip("12345");
		location.setDescription("descript");
		location.setLatitude(45.546544);
		location.setLongitude(-73.451124);
		location = locationDao.addLocation(location);
		
		Sighting sighting1 = new Sighting();
		sighting1.setDate(LocalDate.parse("2021-10-25"));
		sighting1.setLocationId(location.getLocationId());
		sighting1.setHeroes(new ArrayList<>());
		sighting1 = sightingDao.addSighting(sighting1);
		
		Sighting sighting2 = new Sighting();
		sighting2.setDate(LocalDate.parse("2021-10-26"));
		sighting2.setLocationId(location.getLocationId());
		sighting2.setHeroes(new ArrayList<>());
		sighting2 = sightingDao.addSighting(sighting2);
		
		List<Sighting> sightings = sightingDao.getAllSightingByLocation(location.getLocationId());
		assertEquals(2, sightings.size());
	}
	
	@Test
	public void testGetAllHeroesBySighting() {
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
		
		List<Hero> heroes = new ArrayList<>();
		heroes.add(hero1);
		heroes.add(hero2);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(heroes);
		sighting = sightingDao.addSighting(sighting);
		
		List<Hero> fromDao = sightingDao.getAllHeroesBySighting(sighting.getSightingId());
		assertEquals(2, fromDao.size());	
	}
	
	@Test
	public void testGetAllHeroesByLocation() {
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
		
		List<Hero> heroes = new ArrayList<>();
		heroes.add(hero1);
		heroes.add(hero2);
		
		Sighting sighting = new Sighting();
		sighting.setDate(LocalDate.parse("2021-10-25"));
		sighting.setLocationId(location.getLocationId());
		sighting.setHeroes(heroes);
		sighting = sightingDao.addSighting(sighting);
		
		List<Hero> fromDao = sightingDao.getAllHeroesByLocation(location.getLocationId());
		
		assertEquals(2, fromDao.size());
		
	}
	
	
	
	
	
	
	
}
