package com.ab.SuperHero.dao;

import java.util.List;

import com.ab.SuperHero.dto.Hero;

public interface HeroDao {
	
	Hero addHero(Hero hero);
	
	void updateHero(Hero hero);
	
	void deleteHeroById(int heroId);
	
	Hero getHeroById(int heroId);
	
	List<Hero> getAllHeroes();
	
	List<Hero> getAllHeroesByPower(int powerId);
	
	List<Hero> getAllHeroesByOrganization(int organizationId);
	
	List<Hero> getAllHeroesBySighting(int sightingId);
		
}
