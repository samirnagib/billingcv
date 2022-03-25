CREATE DATABASE  IF NOT EXISTS `cvbill_tst` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cvbill_tst`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 192.168.175.109    Database: cvbill_tst
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `billTags`
--

DROP TABLE IF EXISTS `billTags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billTags` (
  `idbillTag` int NOT NULL AUTO_INCREMENT,
  `billtagName` varchar(200) NOT NULL,
  `billPriceTB` double DEFAULT NULL,
  PRIMARY KEY (`idbillTag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billTags`
--

LOCK TABLES `billTags` WRITE;
/*!40000 ALTER TABLE `billTags` DISABLE KEYS */;
INSERT INTO `billTags` VALUES (1,'AWS - Glacier',322.22),(2,'DCCM - OnPremisses',69.09),(3,'Azure - Cool',165.46),(4,'DCCM - Fita',200),(5,'Oracle OCI HOT',25);
/*!40000 ALTER TABLE `billTags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientType`
--

DROP TABLE IF EXISTS `clientType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientType` (
  `idType` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(200) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientType`
--

LOCK TABLES `clientType` WRITE;
/*!40000 ALTER TABLE `clientType` DISABLE KEYS */;
INSERT INTO `clientType` VALUES (1,'Servidor Físico'),(2,'Servidor Virtual'),(3,'Banco de Dados Oracle'),(4,'Banco de Dados Oracle RAC'),(5,'Banco de dados SQL Server'),(6,'Hyper-V'),(7,'VMWare'),(8,'Azure Cloud'),(9,'AWS Cloud'),(10,'Media Agent GCP');
/*!40000 ALTER TABLE `clientType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idClient` int NOT NULL AUTO_INCREMENT,
  `clientName` varchar(200) NOT NULL,
  `clientHostname` varchar(200) DEFAULT NULL,
  `idType` int NOT NULL,
  `idOwner` int NOT NULL,
  PRIMARY KEY (`idClient`),
  KEY `CLIENTES` (`clientName`),
  KEY `fkServerType_idx` (`idType`),
  KEY `fkServerOwner_idx` (`idOwner`),
  CONSTRAINT `fkServerOwner` FOREIGN KEY (`idOwner`) REFERENCES `owner` (`idOwner`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fkServerType` FOREIGN KEY (`idType`) REFERENCES `clientType` (`idType`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'rj2k8vmrep01','10.1.0.150',2,5),(2,'SQLPROD02','10.1.1.115',5,3),(3,'clpaorasr003','clpaorasr003',3,3),(4,'JBORAP03','JBORAP03',4,3),(5,'cmay22lb05','10.134.160.5',1,5),(6,'cmay22lb07','10.134.160.6',1,5),(8,'clpabkpsr004','10.118.0.7',8,5),(9,'gcpprdsae1ama01','10.86.1.4',2,3);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inputBill`
--

DROP TABLE IF EXISTS `inputBill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inputBill` (
  `idInputBill` int NOT NULL AUTO_INCREMENT,
  `ib_ano_mes` varchar(6) NOT NULL,
  `id_billTag` int NOT NULL,
  `id_client` int NOT NULL,
  `cv_agent` varchar(200) DEFAULT NULL,
  `cv_instance` varchar(200) DEFAULT NULL,
  `cv_backupset` varchar(200) DEFAULT NULL,
  `cv_subclient` varchar(200) DEFAULT NULL,
  `cv_storagepolicy` varchar(200) DEFAULT NULL,
  `cv_copyname` varchar(200) DEFAULT NULL,
  `cv_mediasize` double DEFAULT NULL,
  `ib_taxcalculated` double DEFAULT NULL,
  PRIMARY KEY (`idInputBill`),
  KEY `Competencia` (`ib_ano_mes`),
  KEY `fk_billinTag_idx` (`id_billTag`),
  KEY `fk_idClient_idx` (`id_client`),
  CONSTRAINT `fk_billinTag` FOREIGN KEY (`id_billTag`) REFERENCES `billTags` (`idbillTag`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_idClient` FOREIGN KEY (`id_client`) REFERENCES `clientes` (`idClient`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inputBill`
--

LOCK TABLES `inputBill` WRITE;
/*!40000 ALTER TABLE `inputBill` DISABLE KEYS */;
/*!40000 ALTER TABLE `inputBill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner` (
  `idOwner` int NOT NULL AUTO_INCREMENT,
  `owName` varchar(200) NOT NULL,
  `owEmail1` varchar(200) DEFAULT NULL,
  `owEmail2` varchar(200) DEFAULT NULL,
  `owProjectArea` varchar(200) DEFAULT NULL,
  `owAR` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idOwner`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,'RESPONSÁVEL PADRAO','infra1@email.com',NULL,'Infra e Conteudi','010101'),(2,'Acervo',NULL,NULL,'Media e TV','302525'),(3,'Suporte Banco de Dados',NULL,NULL,'Hub Digital','131415'),(4,'SecOps','secops@g.globo',NULL,'Governança','171171'),(5,'BackOffice Backup','backup@g.globo',NULL,'Infra e Segurança','010114'),(6,'Angel da Silva Sauro','angel@canina.org','dogs@canina.org','Infra Basica Canina','08052021'),(7,'Pole da Silva Sauro','pole@canina.org','canina@canina.org','Infra Basica Canina','12122021'),(8,'Sushi The Cat','sushi@catmail.com','catmail@catmail.com','Suporte a Terceiros','070707'),(9,'Sachimi Cat','sashimi@catmail.com','catmail@catmail.com','Suporte a terceiros','070707');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `userlogin` varchar(200) NOT NULL,
  `userpasswd` varchar(200) NOT NULL,
  `userFullName` varchar(200) DEFAULT NULL,
  `userEmail` varchar(200) DEFAULT NULL,
  `userLevelAccess` int DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','senha','Usuario Administrador','admin@email.com',1),(2,'operador','oper1','Usuario Operador','oper@email.com',2),(3,'viewer','view1','Vizualizador Nº 1','view1@email.com',3),(4,'viewer2','view2','Vizualizador nº 2','view2@email.com',3),(5,'amancio','@m4nc10','Amacio Gotardo Filho','amancio@email.com',3),(7,'samir','samir','Samir Nagib','samir@email.com',1),(10,'fulano','senha','Fulano de Tal','fulano@email.com',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cvbill_tst'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-25  6:12:26
