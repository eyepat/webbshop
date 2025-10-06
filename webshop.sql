-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: webshop
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `T_ITEM`
--

DROP TABLE IF EXISTS `T_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `T_ITEM` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_ITEM`
--

LOCK TABLES `T_ITEM` WRITE;
/*!40000 ALTER TABLE `T_ITEM` DISABLE KEYS */;
INSERT INTO `T_ITEM` VALUES (1,'Äpple 🍎','Rött äpple','frukt',5.00),(2,'Banan 🍌','Söt banan','frukt',6.00),(3,'Apelsin 🍊','Saftig apelsin','frukt',7.50),(4,'Jordgubbar 🍓','Svenska, söta och fräscha','frukt',25.90),(5,'Blåbär 🫐','Perfekta till yoghurt och bak','frukt',29.90),(6,'Vindruvor 🍇','Kärnfria, krispiga','frukt',24.50),(7,'Mango 🥭','Mogen och söt','frukt',19.90),(8,'Ananas 🍍','Saftig tropisk smak','frukt',29.00),(9,'Päron 🍐','Möra och saftiga','frukt',14.90),(10,'Kiwi 🥝','Syrlig med C-vitamin','frukt',12.90),(11,'Vattenmelon 🍉','Kall och uppfriskande','frukt',39.00),(12,'Persika 🍑','Mjuk, söt och solmogen','frukt',17.50);
/*!40000 ALTER TABLE `T_ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_USER`
--

DROP TABLE IF EXISTS `T_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `T_USER` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_USER`
--

LOCK TABLES `T_USER` WRITE;
/*!40000 ALTER TABLE `T_USER` DISABLE KEYS */;
INSERT INTO `T_USER` VALUES (1,'aya','sha256:YK2O7YhGRMBOVy8S96+FMQ==:ARG3UohxOBC8T9avH+jbnxDIYk30+nG+3EKRhdIIu0k='),(2,'bahaa','sha256:4qeTBFHeLb4JpqmbBwWFKQ==:uVqFYvFrzI2TytrbU6srHnPcvDX9EoHa7cZo6Ku+sgo='),(3,'baha','sha256:15od06NlHnSHK++fLIK74Q==:lC7c3PnjmU2A/7Y35CHl3dQ31SiTAOtNta57cOjxtAE='),(4,'Aya S','sha256:/htFhVROa6kyEUGa0L46Sg==:DZCRTKKdy3UvEph1KwHehf9Yre4j+fik8DkDgXxJgFI=');
/*!40000 ALTER TABLE `T_USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-05 16:53:36
