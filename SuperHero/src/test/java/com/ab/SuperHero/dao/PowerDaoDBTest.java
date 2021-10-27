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
class PowerDaoDBTest {
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
	
	public PowerDaoDBTest() {
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
	void testAddAndGetPower() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Power fromDao = powerDao.getPowerById(power.getPowerId());
		assertEquals(power.getPowerName(), fromDao.getPowerName());
	}
	
	@Test
	public void testUpdatePower() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Power fromDao = powerDao.getPowerById(power.getPowerId());
		assertEquals(power.getPowerName(), fromDao.getPowerName());
		
		power.setPowerName("new test power");
		powerDao.updatePower(power);
		
		assertNotEquals(power.getPowerName(), fromDao.getPowerName());
		
		fromDao = powerDao.getPowerById(power.getPowerId());
		assertEquals(power.getPowerName(), fromDao.getPowerName());
	}
	
	@Test
	public void testDeletePower() {
		Power power = new Power();
		power.setPowerName("test power");
		power.setPowerDescription("test power description");
		power = powerDao.addPower(power);
		
		Power fromDao = powerDao.getPowerById(power.getPowerId());
		assertEquals(power.getPowerName(), fromDao.getPowerName());
		
		powerDao.deletePower(power.getPowerId());
		
		fromDao = powerDao.getPowerById(power.getPowerId());
		assertNull(fromDao);
	}
	
	@Test
	public void testGetAllPowers() {
		Power power1 = new Power();
		power1.setPowerName("test power");
		power1.setPowerDescription("test power description");
		power1 = powerDao.addPower(power1);
		
		Power power2 = new Power();
		power2.setPowerName("test power");
		power2.setPowerDescription("test power description");
		power2 = powerDao.addPower(power2);
		
		List<Power> powers = powerDao.getAllPower();
		assertEquals(2, powers.size());
		assertEquals(power1.getPowerName(), powers.get(0).getPowerName());
		assertEquals(power2.getPowerName(), powers.get(1).getPowerName());
	}
	
	@Test
	public void testGetAllPowerByHero() {
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
		
		List<Power> fromDao = powerDao.getAllPowerByHero(hero.getHeroId());
		assertEquals(1, fromDao.size());
	}
	
}
