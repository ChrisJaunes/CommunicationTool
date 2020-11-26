-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: app_serve
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--
DROP DATABASE IF EXISTS Communication;
CREATE DATABASE Communication;
use Communication;

CREATE TABLE IF NOT EXISTS `test` (
    `test_id` int PRIMARY KEY,
    `test_content` varchar(10)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
LOCK TABLES `test` WRITE;
INSERT INTO `test` VALUES
('111','111'),
('555','555'),
('123','123');
UNLOCK TABLES;


DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account` varchar(40) PRIMARY KEY,
  `password` varchar(40) NOT NULL,
  `nickname` varchar(40) DEFAULT NULL,
  `avatar` longtext DEFAULT NULL,
  `text_style` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `account` WRITE;
INSERT INTO `account` VALUES
    ('111','111','111', NULL, NULL),
    ('555','555','555', NULL, NULL),
    ('123','123','123', NULL, NULL);
UNLOCK TABLES;