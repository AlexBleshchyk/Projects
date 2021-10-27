DROP DATABASE IF EXISTS SuperHeroDB;
CREATE DATABASE SuperHeroDB;
USE SuperHeroDB;

CREATE TABLE IF NOT EXISTS SuperPower (
  superPowerId INT NOT NULL AUTO_INCREMENT,
  superPowerName VARCHAR(45) NOT NULL,
  superPowerDescription VARCHAR(45) NULL,
  PRIMARY KEY (superPowerId)
);

CREATE TABLE IF NOT EXISTS Hero (
  heroId INT NOT NULL AUTO_INCREMENT,
  heroName VARCHAR(45) NOT NULL,
  heroDescription VARCHAR(45),
  isHero TINYINT NULL,
  SuperPower_superPowerId INT,
  PRIMARY KEY (heroId, SuperPower_superPowerId),
  CONSTRAINT fk_Hero_SuperPower FOREIGN KEY (SuperPower_superPowerId)
    REFERENCES SuperPower (superPowerId)
);
  
CREATE TABLE IF NOT EXISTS Location (
    locationId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    locationName VARCHAR(45),
    street VARCHAR(45),
    city VARCHAR(45),
    zip VARCHAR(45),
    locationDescription VARCHAR(45),
    latitude DOUBLE not null,
    longitude DOUBLE
);
  
CREATE TABLE IF NOT EXISTS `Organization` (
    organizationId INT NOT NULL AUTO_INCREMENT,
    organizationName VARCHAR(45),
    contact VARCHAR(45),
    organizationDescription VARCHAR(45) NULL,
    membersNumber INT,
    Location_locationId INT,
    PRIMARY KEY (organizationId, Location_locationId),
    CONSTRAINT fk_Organization_Location FOREIGN KEY (Location_locationId)
        REFERENCES Location (locationId)
);

CREATE TABLE IF NOT EXISTS organizationHero (
    organizationHeroId INT NOT NULL AUTO_INCREMENT,
    Hero_heroId INT,
    Organization_organizationId INT,
    PRIMARY KEY (organizationHeroId, Hero_heroId, Organization_organizationId),
    CONSTRAINT fk_organization_hero_Hero FOREIGN KEY (Hero_heroId)
        REFERENCES Hero (heroId),
    CONSTRAINT fk_organization_hero_Organization FOREIGN KEY (Organization_organizationId)
        REFERENCES `Organization` (organizationId)
);

CREATE TABLE IF NOT EXISTS Sighting (
    sightingId INT NOT NULL AUTO_INCREMENT,
    sightDate DATE,
    Location_locationId INT,
    PRIMARY KEY (sightingId, Location_locationId),
    CONSTRAINT fk_Sighting_Location FOREIGN KEY (Location_locationId)
        REFERENCES Location (locationId)
);

CREATE TABLE IF NOT EXISTS heroSighting (
    heroSightingId INT NOT NULL AUTO_INCREMENT,
    Sighting_sightingId INT,
    Sighting_Location_locationId INT ,
    Hero_heroId INT,
    PRIMARY KEY (heroSightingId, Sighting_sightingId, Sighting_Location_locationId, Hero_heroId),
    CONSTRAINT fk_heroSighting_Sighting FOREIGN KEY (Sighting_sightingId, Sighting_Location_locationId)
        REFERENCES Sighting (sightingId, Location_locationId),
    CONSTRAINT fk_heroSighting_Hero FOREIGN KEY (Hero_heroId)
        REFERENCES Hero (heroId)
);