create database event_management;

CREATE TABLE `users` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `role` varchar(25) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB CHARSET=latin1;

insert into users values('joshp','123456','Josh','P','admin','Josh.P@gmail.com','12345678',1);

CREATE TABLE `companyinfo` (
  `companyid` int(5) NOT NULL AUTO_INCREMENT,
  `companyname` varchar(255) NOT NULL,
  `companyphone` varchar(10) NOT NULL,
  `companyemail` varchar(50) NOT NULL,
  `companyaddress` varchar(255) NOT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`companyid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;

insert into companyinfo(companyname,companyphone,companyemail,companyaddress,enabled) values('Eagle Event Planning','876373778','eagle@eventplanning.com','CA',1);

CREATE TABLE `customer` (
`id` int(5) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `customerno` int(10) NOT NULL,
  `customeremail` varchar(50) DEFAULT NULL,
  `customerphone` varchar(10) DEFAULT NULL,
  `customeraddress` varchar(200) DEFAULT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;

CREATE TABLE `event` (
  `eventid` int(5) NOT NULL AUTO_INCREMENT,
  `eventname` varchar(255) NOT NULL,
  `venue` varchar(200) NOT NULL,
  `eventdate` DATE NOT NULL,
  `noofguests` int(5) NOT NULL,
  `enabled` int(2) DEFAULT NULL,
  `username` varchar(25) NOT NULL,
  `cid` int(5) NOT NULL,
  PRIMARY KEY (`eventid`),
  FOREIGN KEY(`username`)
  REFERENCES users(`username`),
  FOREIGN KEY(`cid`)
  REFERENCES customer(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;


CREATE TABLE `guesttable` (
  `tableid` int(5) NOT NULL AUTO_INCREMENT,
  `capacity` int(5) NOT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`tableid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;


insert into guesttable(`capacity`,`enabled`) values(4,1);
insert into guesttable(`capacity`,`enabled`) values(6,1);
-- insert into guesttable(`capacity`,`enabled`) values(8,1);
-- insert into guesttable(`capacity`,`enabled`) values(10,1);
-- insert into guesttable(`capacity`,`enabled`) values(12,1);

CREATE TABLE `eventguest` (
  `guestid` int(6) NOT NULL AUTO_INCREMENT,
  `guestname` varchar(100) NOT NULL,
  `guestphone` varchar(12) NOT NULL,
  `eventid` int(5) NOT NULL,
  `tableid` int(5) DEFAULT NULL,
  `seatno` int(5) DEFAULT NULL,
  `enabled` int(2) DEFAULT NULL,
  PRIMARY KEY (`guestid`),
  FOREIGN KEY(`eventid`)
  REFERENCES event(`eventid`),
  FOREIGN KEY(`tableid`)
  REFERENCES guesttable(`tableid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;

ALTER TABLE `eventguest` 
ADD COLUMN `allottedtableno` VARCHAR(20) NULL DEFAULT NULL AFTER `tableid`;

ALTER TABLE `eventmanagement`.`eventguest` 
ADD COLUMN `sametable` VARCHAR(45) NULL DEFAULT NULL COMMENT '' AFTER `allottedtableno`,
ADD COLUMN `notsametable` VARCHAR(45) NULL DEFAULT NULL COMMENT '' AFTER `sametable`;
