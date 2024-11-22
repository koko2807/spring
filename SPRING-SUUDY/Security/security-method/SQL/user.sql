-- Active: 1731384027892@@127.0.0.1@3306@aloha
-- user

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `NO` bigint NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `EMAIL` varchar(200) DEFAULT NULL,
  `CREATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENABLED` int DEFAULT 1,
  PRIMARY KEY (`NO`)
) COMMENT='회원';