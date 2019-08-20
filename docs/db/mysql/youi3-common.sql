DROP TABLE IF EXISTS `youi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `youi_user` (
  `USER_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `AGENCY_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOGIN_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `youi_user` VALUES ('10010001','000000','$2a$10$tUXdi6O8uMn1WxSLiXJTtezxMRnq/ZbYt.ux2EQtYNktIi4jPHHVa','10010001');