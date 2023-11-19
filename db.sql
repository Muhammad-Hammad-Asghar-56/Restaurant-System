-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Burger',1),(2,'Pizza',1),(3,'Saji',1),(4,'Chiken',1),(5,'AK',0),(6,'NEWTEST',1),(7,'New Catg',0),(8,'test_Ctg',0),(9,'New Category',0),(10,'aaaa',0),(11,'Test112',0);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enumtype`
--

DROP TABLE IF EXISTS `enumtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enumtype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enumtype`
--

LOCK TABLES `enumtype` WRITE;
/*!40000 ALTER TABLE `enumtype` DISABLE KEYS */;
INSERT INTO `enumtype` VALUES (1,'with','Feature'),(2,'without','Feature'),(3,'only With','Feature'),(4,'Small Dose','Feature'),(5,'Large Dose','Feature'),(6,'Simple','Product'),(7,'KG','Product'),(8,'Free','Product');
/*!40000 ALTER TABLE `enumtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezreport`
--

DROP TABLE IF EXISTS `ezreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ezreport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issueDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `StartedFrom` int DEFAULT NULL,
  `EndingOn` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezreport`
--

LOCK TABLES `ezreport` WRITE;
/*!40000 ALTER TABLE `ezreport` DISABLE KEYS */;
INSERT INTO `ezreport` VALUES (1,'2023-10-19 15:44:13',18,55),(2,'2023-10-19 15:50:09',18,55),(3,'2023-10-25 12:14:46',18,55),(4,'2023-10-25 12:19:24',18,55),(5,'2023-11-01 12:26:33',1,55),(6,'2023-11-01 13:19:07',1,55),(7,'2023-11-01 13:22:06',1,55),(8,'2023-11-01 13:23:47',1,55),(9,'2023-11-01 13:26:26',1,55),(10,'2023-11-01 13:27:17',1,55),(11,'2023-11-01 13:36:45',1,55),(12,'2023-11-01 13:38:34',1,55),(13,'2023-11-01 13:39:19',1,55),(14,'2023-11-01 13:40:10',1,55),(15,'2023-11-01 13:42:35',1,55),(16,'2023-11-01 13:43:21',1,55),(17,'2023-11-01 13:59:16',1,55),(18,'2023-11-01 14:00:00',1,55),(19,'2023-11-01 14:00:55',1,55),(20,'2023-11-01 14:09:25',1,55),(21,'2023-11-01 14:21:28',1,55),(22,'2023-11-01 14:22:55',1,55),(23,'2023-11-01 14:24:14',1,55),(24,'2023-11-01 14:30:59',1,55),(25,'2023-11-04 08:35:04',1,55),(26,'2023-11-06 21:18:53',1,55),(27,'2023-11-06 21:28:18',1,55),(28,'2023-11-07 08:39:16',1,55),(29,'2023-11-07 18:31:47',1,55),(30,'2023-11-07 19:24:33',1,55),(31,'2023-11-07 19:40:15',1,55),(32,'2023-11-07 19:53:06',1,55),(33,'2023-11-07 20:19:00',1,55),(34,'2023-11-07 23:00:46',56,56),(35,'2023-11-09 15:05:31',1,56),(36,'2023-11-09 16:31:44',21,57),(37,'2023-11-12 00:16:25',58,60),(38,'2023-11-13 18:15:17',61,62),(39,'2023-11-13 18:22:47',63,63);
/*!40000 ALTER TABLE `ezreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `price` int DEFAULT NULL,
  `active` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature`
--

LOCK TABLES `feature` WRITE;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` VALUES (1,'Feature 1',10,1),(2,'Feature 2',20,1),(3,'Mayo',11,1),(4,'Fired Chicken',50,0),(5,'Chilli Chicken',120,0),(6,'NEWFEATURE',20,0),(7,'Abc',102,0),(8,'test',123,0),(9,'TestFeature',21,0),(10,'abc',120,0),(11,'bcb',25,1);
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `linkedproductfeature`
--

DROP TABLE IF EXISTS `linkedproductfeature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `linkedproductfeature` (
  `productId` int NOT NULL,
  `featureId` int NOT NULL,
  PRIMARY KEY (`productId`,`featureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linkedproductfeature`
--

LOCK TABLES `linkedproductfeature` WRITE;
/*!40000 ALTER TABLE `linkedproductfeature` DISABLE KEYS */;
INSERT INTO `linkedproductfeature` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(2,1),(2,2),(2,3),(2,4),(6,1),(6,2),(6,3),(6,4),(6,5),(7,1),(7,2),(7,3),(8,1),(8,3),(8,4),(9,1),(9,2),(9,4),(10,1),(10,2),(10,4),(10,5),(10,6),(11,1),(11,2),(11,3),(11,4),(11,5),(11,6),(12,1),(12,2),(12,3),(12,4),(12,5),(12,6),(13,1),(13,2),(13,3),(13,4),(13,5),(13,6),(15,3),(15,4),(15,5),(16,1),(16,2),(16,3),(17,1),(17,2),(17,3),(18,1),(18,2),(18,3),(19,1),(19,2),(19,3),(20,1),(20,2);
/*!40000 ALTER TABLE `linkedproductfeature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderfeature`
--

DROP TABLE IF EXISTS `orderfeature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderfeature` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderItemId` int DEFAULT NULL,
  `featureId` int DEFAULT NULL,
  `typeId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeId` (`typeId`),
  KEY `featureId` (`featureId`),
  KEY `orderItemId` (`orderItemId`),
  CONSTRAINT `orderfeature_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `enumtype` (`id`),
  CONSTRAINT `orderfeature_ibfk_2` FOREIGN KEY (`featureId`) REFERENCES `feature` (`id`),
  CONSTRAINT `orderfeature_ibfk_3` FOREIGN KEY (`orderItemId`) REFERENCES `orderitems` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderfeature`
--

LOCK TABLES `orderfeature` WRITE;
/*!40000 ALTER TABLE `orderfeature` DISABLE KEYS */;
INSERT INTO `orderfeature` VALUES (1,1,1,1),(2,2,2,2),(3,3,2,3),(4,3,3,3),(5,3,4,3),(6,4,1,1),(7,4,2,3),(8,4,3,5),(9,4,4,1),(10,4,5,4),(11,6,1,1),(12,6,2,1),(13,6,3,1),(14,6,4,4),(15,6,5,1),(16,6,6,1),(17,7,1,1),(18,7,2,1),(19,7,3,1),(20,8,1,1),(21,8,2,1),(22,8,3,1),(23,9,1,1),(24,9,2,1),(25,9,3,1),(26,10,1,1),(27,10,2,1),(28,10,4,1),(29,10,3,1),(30,11,1,1),(31,11,2,1),(32,11,4,5),(33,12,2,1),(34,12,3,1),(35,12,4,1),(36,13,1,1),(37,13,2,1),(38,16,2,2),(39,16,3,2),(40,16,4,4),(41,16,5,4),(42,18,2,2),(43,18,3,2),(44,18,4,4),(45,18,5,4),(46,20,2,2),(47,20,3,2),(48,20,4,2),(49,20,5,2),(50,21,3,3),(51,22,1,2),(52,22,4,3),(53,22,5,3),(54,23,1,1),(55,23,2,1),(56,23,3,1),(57,26,1,2),(58,26,4,3),(59,26,5,3),(60,27,4,3),(61,28,3,3),(62,28,4,3),(63,29,4,3),(64,30,1,1),(65,30,2,1),(66,30,3,1),(67,30,4,1),(68,32,1,1),(69,32,2,1),(70,32,3,1),(71,33,1,1),(72,34,3,1),(73,34,4,1),(74,35,5,4),(75,36,4,3),(76,37,3,1),(77,37,4,1),(78,37,5,1),(79,38,2,1),(80,38,3,1),(81,39,1,1),(82,39,2,1),(83,41,3,3),(84,42,4,1),(85,43,3,3),(86,44,2,2),(87,44,1,1);
/*!40000 ALTER TABLE `orderfeature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderID` int DEFAULT NULL,
  `productID` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `disc` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `productID` (`productID`),
  KEY `orderID` (`orderID`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`id`),
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`orderID`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES (1,1,1,2,0.02),(2,1,2,1,0.03),(3,6,1,1,0),(4,7,1,1,5),(5,8,14,1,0),(6,8,1,3,10),(7,9,12,3,0),(8,9,13,3,0),(9,9,2,3,10),(10,10,13,2,8),(11,10,2,2,5),(12,10,12,1,10),(13,11,1,1,0),(14,11,6,1,0),(15,12,6,1,0),(16,12,1,1,0),(17,13,6,1,0),(18,13,1,1,0),(19,14,6,1,0),(20,14,1,1,0),(21,14,13,2,5),(22,15,12,2,5.2),(23,15,1,1,0),(24,15,6,1,0),(25,16,6,1,0),(26,16,12,1,0),(27,16,8,2,5),(28,17,2,1,1.63),(29,17,8,2,5),(30,18,1,1,2),(31,18,6,1,10),(32,19,1,1,0),(33,19,13,2,5),(34,20,15,2,5),(35,21,15,1,2),(36,22,8,1,5),(37,23,15,1,2),(38,23,1,1,2),(39,24,1,3,0),(40,24,6,2,0),(41,25,1,4,0),(42,26,15,1,0),(43,26,1,1,0),(44,27,1,1,0),(45,28,16,0,0),(46,28,6,3,0),(47,29,16,0,0),(48,29,15,2,0),(49,30,16,0,0),(50,30,6,3,0),(51,31,16,0,0),(52,31,2,2,0),(53,32,15,1,0),(54,32,16,0,0),(55,33,8,50,0),(56,33,16,1,0),(57,33,12,1,0),(58,34,6,1,0),(59,34,8,1,0),(60,34,8,1,0),(61,35,8,3,0),(62,36,8,9,0),(63,37,8,21,0),(64,37,8,20,0),(65,38,8,2,0),(66,39,8,2,0),(67,40,8,2,0),(68,41,8,3,0),(69,41,6,2,0),(70,42,8,2,0),(71,43,8,1,0),(72,44,8,1,0),(73,45,8,1,0),(74,46,6,1,0),(75,46,8,1,5),(76,47,8,2,8),(77,48,8,1,5),(78,49,8,1,5),(79,50,8,1,0),(80,50,16,5,0),(81,51,2,1,0),(82,51,12,1,5),(83,52,6,1,0),(84,52,16,2,0),(85,52,14,1,0),(86,53,8,1,0),(87,54,8,1,0),(88,54,16,1,0),(89,55,6,1,0),(90,56,12,3,0),(91,57,6,4,0),(92,58,15,3,0),(93,59,15,2,0),(94,60,15,1,0),(95,61,15,1,0),(96,62,15,4,0),(97,63,16,2,0),(98,63,6,1,0),(99,64,16,5,0),(100,65,16,2,0),(101,66,6,1,0),(102,67,6,1,0);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `disc` float DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'Order comment 1',0.05,'2023-11-09 07:06:29'),(2,2,'Order comment 2',0.1,'2023-11-09 07:06:29'),(3,3,'this is the first Order',0,'2023-11-09 07:06:29'),(4,3,'',0,'2023-11-09 07:06:29'),(5,3,'',0,'2023-11-09 07:06:29'),(6,3,'This is the final order',0,'2023-11-09 07:06:29'),(7,3,'This is my test order',0,'2023-11-09 07:06:29'),(8,3,'This is my test Order',0,'2023-11-09 07:06:29'),(9,3,'This is my test product',0,'2023-11-09 07:06:29'),(10,3,'this is my test comment',0,'2023-11-09 07:06:29'),(11,5,'',0,'2023-11-09 07:06:29'),(12,5,'',0,'2023-11-09 07:06:29'),(13,5,'',0,'2023-11-09 07:06:29'),(14,5,'',0,'2023-11-09 07:06:29'),(15,5,'',0,'2023-11-09 07:06:29'),(16,5,'',0,'2023-11-09 07:06:29'),(17,5,'',0,'2023-11-09 07:06:29'),(18,5,'',0,'2023-11-09 07:06:29'),(19,5,'',0,'2023-11-09 07:06:29'),(20,5,'',0,'2023-11-09 07:06:29'),(21,5,'',0,'2023-11-09 10:56:54'),(22,5,'',0,'2023-11-09 10:56:54'),(23,5,'',0,'2023-11-09 10:56:54'),(24,5,'',0,'2023-11-09 10:56:54'),(25,5,'',0,'2023-11-09 10:56:54'),(26,5,'',0,'2023-11-09 10:56:54'),(27,5,'',0,'2023-11-09 10:56:54'),(28,5,'',0,'2023-11-09 10:56:54'),(29,5,'',0,'2023-11-09 10:56:54'),(30,5,'',0,'2023-11-09 10:56:54'),(31,5,'',0,'2023-11-09 10:56:54'),(32,5,'',0,'2023-11-09 10:56:54'),(33,5,'',0,'2023-11-09 10:56:54'),(34,5,'',0,'2023-11-09 10:56:54'),(35,5,'',0,'2023-11-09 10:56:54'),(36,5,'',0,'2023-11-09 10:56:54'),(37,5,'',0,'2023-11-09 10:56:54'),(38,5,'',0,'2023-11-09 10:56:54'),(39,5,'',0,'2023-11-09 10:56:54'),(40,5,'',0,'2023-11-09 10:56:54'),(41,5,'',0,'2023-11-09 10:56:54'),(42,5,'',0,'2023-11-09 10:56:54'),(43,5,'',0,'2023-11-09 10:56:54'),(44,5,'',0,'2023-11-09 10:56:54'),(45,5,'',0,'2023-11-09 10:56:54'),(46,5,'',0,'2023-11-09 10:56:54'),(47,5,'',0,'2023-11-09 10:56:54'),(48,5,'',0,'2023-11-09 10:56:54'),(49,5,'',0,'2023-11-09 10:56:54'),(50,5,'',0,'2023-11-09 10:56:54'),(51,5,'',0,'2023-11-09 10:56:54'),(52,5,'',0,'2023-11-09 10:56:54'),(53,5,'',0,'2023-11-09 10:56:54'),(54,5,'',0,'2023-11-09 10:56:54'),(55,5,'',0,'2023-11-09 10:56:54'),(56,5,'',0,'2023-11-09 10:56:54'),(57,5,'',0,'2023-11-09 11:23:42'),(58,5,'',0,'2023-11-11 14:11:27'),(59,5,'',0,'2023-11-11 17:57:37'),(60,5,'',0,'2023-11-11 18:07:54'),(61,5,'',0,'2023-11-12 11:27:13'),(62,5,'',0,'2023-11-12 11:27:44'),(63,5,'',0,'2023-11-13 13:20:46'),(64,5,'',0,'2023-11-13 13:39:16'),(65,5,'',0,'2023-11-13 13:40:51'),(66,5,'',0,'2023-11-13 13:42:26'),(67,5,'',0,'2023-11-13 13:43:39');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `vatTax` float DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `type` (`type`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`type`) REFERENCES `enumtype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Bazingas',1,12.5,0,0,6),(2,'Cheese',2,0.2,1,0,6),(3,'Zinger',2,0.5,0,200,8),(4,'Fried Chickens',4,12,0,0,8),(6,'Product',3,20,1,0,8),(7,'TEST',4,100,0,10,7),(8,'Bazingass',4,20,1,200,7),(9,'TACOS',6,20,0,0,8),(10,'ETC',7,12,1,100,6),(11,'Test_Product1',8,100,0,200,6),(12,'Test1',2,0.1,1,100,6),(13,'Test2',2,0.2,1,200,7),(14,'Test3',2,0.5,1,200,8),(15,'TestVer2Item',6,24,1,200,6),(16,'HI2',1,13,1,20.5,7),(17,'abc',1,13,0,50,6),(18,'ABC2',10,24,1,2,6),(19,'abcd1',9,24,1,250,7),(20,'TestProduct',11,24,0,20,6);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor` (
  `SensorID` int NOT NULL,
  `location` varchar(45) NOT NULL,
  PRIMARY KEY (`SensorID`),
  UNIQUE KEY `SensorID_UNIQUE` (`SensorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax`
--

DROP TABLE IF EXISTS `tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `disc` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax`
--

LOCK TABLES `tax` WRITE;
/*!40000 ALTER TABLE `tax` DISABLE KEYS */;
INSERT INTO `tax` VALUES (1,'VatTax',13),(2,'VatTax',24);
/*!40000 ALTER TABLE `tax` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `userpass` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `auth_disc` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Master','Master','12345',1,100),(2,'Waiter','Waiter1','123456',0,10),(3,'Waiter','Waiter123','12345',1,20),(4,'Waiter','ABC','12345678',1,20),(5,'Waiter','hammad','12345',1,20),(6,'Admin','NewUser','1',1,10),(7,'Waiter','abc','1',1,10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xreport`
--

DROP TABLE IF EXISTS `xreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xreport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issueDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `StartedFrom` int DEFAULT NULL,
  `EndingOn` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xreport`
--

LOCK TABLES `xreport` WRITE;
/*!40000 ALTER TABLE `xreport` DISABLE KEYS */;
INSERT INTO `xreport` VALUES (1,'2023-10-25 12:23:01',18,55),(2,'2023-10-25 12:27:08',1,55),(3,'2023-10-25 12:27:17',1,55),(4,'2023-10-25 12:32:23',1,55),(5,'2023-10-25 12:34:58',1,55),(6,'2023-11-01 12:23:08',1,55),(7,'2023-11-01 12:28:33',1,55),(8,'2023-11-01 12:31:41',1,55),(9,'2023-11-06 21:28:57',1,55),(10,'2023-11-09 16:36:48',1,57),(11,'2023-11-12 16:27:32',58,61),(12,'2023-11-13 18:15:11',62,62),(13,'2023-11-13 18:22:44',63,63);
/*!40000 ALTER TABLE `xreport` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-15 11:29:26
