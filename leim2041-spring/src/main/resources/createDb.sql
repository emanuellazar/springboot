CREATE DATABASE advertisements;
--  drop database advertisements;
use advertisements;
CREATE TABLE IF NOT EXISTS RealEstate(city VARCHAR(1000), neighbourhood VARCHAR(1000), price INT, numberOfRooms INT, yearBuilt INT, hasElevator BOOLEAN, ownerId BIGINT,  FOREIGN KEY (ownerId) REFERENCES Owner(id), id BIGINT PRIMARY KEY AUTO_INCREMENT );
CREATE TABLE IF NOT EXISTS Owner(firstName VARCHAR(1000), secondName VARCHAR(1000), age INT, cnp INT, id BIGINT PRIMARY KEY AUTO_INCREMENT );