CREATE DATABASE  IF NOT EXISTS `cvbill_hlm` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cvbill_hlm`;


CREATE TABLE `billTags` (
  `idbillTag` int NOT NULL AUTO_INCREMENT,
  `billtagName` varchar(200) NOT NULL,
  `billPriceTB` double DEFAULT NULL,
  PRIMARY KEY (`idbillTag`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `clientType` (
  `idType` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(200) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','senha','Usuario Administrador','admin@email.com',1);
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
