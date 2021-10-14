create database if not exists demo;

use demo;

drop table if exists student;

CREATE TABLE `student` (
  `ID` bigint(20) NOT NULL DEFAULT '0',
  `NAME` varchar(64) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

INSERT INTO `student` VALUES (101,'Quangphay','quangphay@gmail.com'),(103,'Thuan','thuan@gmail.com'),(104,'kenpham','kenpham@synamed.com');
