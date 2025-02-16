/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.6.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: heal
-- ------------------------------------------------------
-- Server version	11.6.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `daily_workout_plans`
--

DROP TABLE IF EXISTS `daily_workout_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_workout_plans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `workout_type_id` bigint(20) NOT NULL,
  `sets` int(11) NOT NULL,
  `reps` int(11) NOT NULL,
  `weight` double DEFAULT NULL,
  `completed` tinyint(1) DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `workout_type_id` (`workout_type_id`),
  CONSTRAINT `daily_workout_plans_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `daily_workout_plans_ibfk_2` FOREIGN KEY (`workout_type_id`) REFERENCES `workout_types` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_workout_plans`
--

LOCK TABLES `daily_workout_plans` WRITE;
/*!40000 ALTER TABLE `daily_workout_plans` DISABLE KEYS */;
INSERT INTO `daily_workout_plans` VALUES
(25,3,'2025-02-15',2,2,2,2,0,'2025-02-13 23:04:51','2025-02-14'),
(26,3,'2025-02-16',2,2,2,2,0,'2025-02-13 23:05:00','2025-02-14'),
(27,3,'2025-02-14',2,1,1,10,0,'2025-02-13 23:14:00','2025-02-14'),
(29,1,'2025-02-14',2,3,10,50,0,'2025-02-14 17:14:00','2025-02-15'),
(30,1,'2025-02-14',2,3,10,50,0,'2025-02-14 17:16:52','2025-02-15');
/*!40000 ALTER TABLE `daily_workout_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'123','123@naver.com','$2a$10$S2VVypsQ1WPaE5dYnrRRDO57n9QsWVEUzRrirvDgqA69PA9wVUHSu',NULL,NULL),
(2,'김보현','11@naver.com','$2a$10$tORjWj0hiCQqOBR5MhiI.ek6gV3oBIWm0IB8G7ThOlqilJw5lukpO',NULL,NULL),
(3,'김김','113@naver.com','$2a$10$SB3WGoAIFJmtJBtn6jHGTOEmPsj5xVZtDj0ItMteyILIRlgzdquaG',NULL,NULL),
(4,'김보슬','122@naver.com','$2a$10$bCpiR/BndYklUigaXIv3/eOPQNobYtdnzSg.JrT6vOVNjaghTbFYm',NULL,NULL),
(5,'12322','222@naver.com','$2a$10$s9y56ZTpxQKia4PN.T8f6eWcjqQvI0I9Q/w6RBiOMYmAcXDE3yTEi',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_goals`
--

DROP TABLE IF EXISTS `workout_goals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout_goals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `goal_name` varchar(100) NOT NULL,
  `target_sets` int(11) DEFAULT NULL,
  `target_reps` int(11) DEFAULT NULL,
  `target_duration` int(11) DEFAULT NULL,
  `current_sets` int(11) DEFAULT 0,
  `current_reps` int(11) DEFAULT 0,
  `current_duration` int(11) DEFAULT 0,
  `goal_achieved` tinyint(1) DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `workout_goals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_goals`
--

LOCK TABLES `workout_goals` WRITE;
/*!40000 ALTER TABLE `workout_goals` DISABLE KEYS */;
INSERT INTO `workout_goals` VALUES
(2,3,'벤치',3,10,9,3,0,0,0,'2025-02-12 14:44:28','2025-02-12 14:44:31'),
(3,3,'벤치프레스',9,2,6,1,0,0,0,'2025-02-12 14:46:49','2025-02-12 14:46:52'),
(4,3,'스쾃',15,10,15,0,0,0,0,'2025-02-12 20:58:56','2025-02-12 20:58:56');
/*!40000 ALTER TABLE `workout_goals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_records`
--

DROP TABLE IF EXISTS `workout_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `workout_plan_id` bigint(20) NOT NULL,
  `workout_type_id` bigint(20) NOT NULL,
  `workout_name` varchar(100) NOT NULL,
  `sets` int(11) NOT NULL,
  `reps` int(11) NOT NULL,
  `weight` double DEFAULT NULL,
  `completed_at` timestamp NULL DEFAULT current_timestamp(),
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `workout_plan_id` (`workout_plan_id`),
  KEY `workout_type_id` (`workout_type_id`),
  CONSTRAINT `workout_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `workout_records_ibfk_2` FOREIGN KEY (`workout_plan_id`) REFERENCES `daily_workout_plans` (`id`) ON DELETE CASCADE,
  CONSTRAINT `workout_records_ibfk_3` FOREIGN KEY (`workout_type_id`) REFERENCES `workout_types` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_records`
--

LOCK TABLES `workout_records` WRITE;
/*!40000 ALTER TABLE `workout_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `workout_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_sets`
--

DROP TABLE IF EXISTS `workout_sets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout_sets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_id` bigint(20) NOT NULL,
  `set_number` int(11) NOT NULL,
  `weight` double NOT NULL,
  `reps` int(11) NOT NULL,
  `completed` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `plan_id` (`plan_id`),
  CONSTRAINT `workout_sets_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `daily_workout_plans` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_sets`
--

LOCK TABLES `workout_sets` WRITE;
/*!40000 ALTER TABLE `workout_sets` DISABLE KEYS */;
/*!40000 ALTER TABLE `workout_sets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_types`
--

DROP TABLE IF EXISTS `workout_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `workout_name` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `workout_name` (`workout_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_types`
--

LOCK TABLES `workout_types` WRITE;
/*!40000 ALTER TABLE `workout_types` DISABLE KEYS */;
INSERT INTO `workout_types` VALUES
(2,'스쿼트','웨이트','하체 근육을 발달시키는 대표적인 운동','2025-02-12 01:32:54','2025-02-12 01:32:54'),
(3,'데드리프트','웨이트','전신 근력을 키우는 대표적인 운동','2025-02-12 01:32:54','2025-02-12 01:32:54'),
(4,'러닝 머신','유산소','지구력을 향상시키는 유산소 운동','2025-02-12 01:32:54','2025-02-12 01:32:54'),
(5,'사이클','유산소','하체 근육을 강화하는 유산소 운동','2025-02-12 01:32:54','2025-02-12 01:32:54'),
(6,'Test Workout','Test Category','Test Description','2025-02-14 17:14:37','2025-02-14 17:14:37');
/*!40000 ALTER TABLE `workout_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'heal'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-02-16 12:42:33
