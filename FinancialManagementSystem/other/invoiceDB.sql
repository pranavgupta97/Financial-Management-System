-- use pgupta;

DROP TABLE IF EXISTS `Address`;
DROP TABLE IF EXISTS `State`;
DROP TABLE IF EXISTS `Country`;
DROP TABLE IF EXISTS `ProductListInvoices`;
DROP TABLE IF EXISTS `Invoice`;
DROP TABLE IF EXISTS `email`;
DROP TABLE IF EXISTS `Customers`;
DROP TABLE IF EXISTS `Products`;
DROP TABLE IF EXISTS `Person`;


--
-- Table structure for table `Person`
--



CREATE TABLE `Person` (
  `personID` int(50) NOT NULL AUTO_INCREMENT,
  `personCode` varchar(50) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  PRIMARY KEY (`personID`)
);

	alter table Person AUTO_INCREMENT = 1;
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('944c', 'Sylvester', 'McCoy' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('306a', 'Jon', 'Pertwee' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('aef1', 'Gordon', 'Gekko' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('321f', 'Brock', 'Sampson' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('321nd', 'William', 'Thotroy' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('231', 'Holley', 'Earl' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('321dr', 'Samantha', 'Baker' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('nwdoc1', 'Chris', 'Eccleston' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('2ndbestd', 'David', 'Castro' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('wrddoc', 'Matt', 'Smith' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('bbchar', 'Kaylee', 'Tennant' );
	INSERT INTO Person(personCode, firstName, lastName) VALUES ('55bb', 'Bud', 'Fox' );
	
    
--
-- Table structure for table `Products`
--



CREATE TABLE `Products` (
`productID` int AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `type` varchar(50),
  `nameOfProduct` varchar(50),
  `pricePerUnit` double,
  `activationFee` double,
  `annualFee` double,
  `hourlyFee` double,
  `personID` int(50),
  PRIMARY KEY (`productID`),
  FOREIGN KEY (`personID`) REFERENCES Person(`personID`)
  
);

Alter Table Products AUTO_INCREMENT = 1;
INSERT INTO Products (code, type, nameOfProduct, pricePerUnit) VALUES('b29e', 'E', 'Satellite Receiver', 2500.0);
INSERT INTO Products (code, type, nameOfProduct, pricePerUnit) VALUES('ff23', 'E', 'Wireless5G Router', 124.99);
INSERT INTO Products (code, type, nameOfProduct, pricePerUnit) VALUES('fp12', 'E', 'Netgear DSL Modem', 63.99);
INSERT INTO Products (code, type, nameOfProduct, pricePerUnit) VALUES('1239', 'E', 'Digital Setup Box', 200.00);
INSERT INTO Products (code, type, nameOfProduct, activationFee, annualFee) VALUES('90fa', 'S', 'Long Distance Service', 2000.00, 12000.00);
INSERT INTO Products (code, type, nameOfProduct, personID, hourlyFee) VALUES('3289', 'C', 'Turbo Upgrade-Internet', 3, 25.00);
INSERT INTO Products (code, type, nameOfProduct, personID, hourlyFee) VALUES('728g', 'C', 'Home Wifi Setup', 5, 150.00);
INSERT INTO Products (code, type, nameOfProduct, activationFee, annualFee) VALUES('3294', 'S', 'L2 Home Security', 0.00, 35000.00);
INSERT INTO Products (code, type, nameOfProduct, activationFee, annualFee) VALUES('3295', 'S', 'Domain registration', 350.00, 1200.00);
--
-- Table structure for table `Customers`
--



CREATE TABLE `Customers` (
  `customerID` int(50) NOT NULL AUTO_INCREMENT ,
  `customerCode` varchar(50) NOT NULL,
  `customerType` varchar(50) NOT NULL,
  `personID` int(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`customerID`), 
  FOREIGN KEY (`personID`) REFERENCES Person(`personID`)

);

alter table Customers AUTO_INCREMENT = 1;
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C001', 'B', 6, 'University of Nebraska-Lincoln');
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C002', 'B', 1, 'Stark Industries');
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C003', 'R', 2, 'Tom Eddision');
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C004', 'B', 4, 'National Soccer Administration');
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C005', 'R', 11, 'Vandelay Apartments');
INSERT INTO Customers (customerCode, customerType, PersonID, name) VALUES ('C006', 'R', 7, 'Mr. Smiths #1 Supply Co. Inc.');	


--
-- Table structure for table `email`
--



CREATE TABLE `email` (
  `personID` int(50) NOT NULL,
  `email` varchar(50), 
  FOREIGN KEY (`personID`) REFERENCES Person(`personID`)
  
);

INSERT INTO email (personID, email) VALUES (1, 'smccoy@cubs.com');
INSERT INTO email (personID, email) VALUES (1, 'sylve_mccoy13@gmail.com');
INSERT INTO email (personID, email) VALUES (2, 'j_pertwee@gmail.com');
INSERT INTO email (personID, email) VALUES (2, 'jon@venture.com');
INSERT INTO email (personID, email) VALUES (3, 'gekko@crazy.net');
INSERT INTO email (personID) VALUES (4);
INSERT INTO email (personID, email) VALUES (5, 'willth@doctors.com');
INSERT INTO email (personID, email) VALUES (5, 'wth@who.com');
INSERT INTO email (personID, email) VALUES (6, 'famousholl@who.com');
INSERT INTO email (personID, email) VALUES (6, 'hearl@cse.unl.edu');
INSERT INTO email (personID, email) VALUES (6, 'holleyearl@whovian.com');
INSERT INTO email (personID, email) VALUES (6, 'earl12@bbc.com');
INSERT INTO email (personID, email) VALUES (7, 'sb@baker.com');
INSERT INTO email (personID) VALUES (8);
INSERT INTO email (personID, email) VALUES (9, 'david@shakespeare.com');
INSERT INTO email (personID, email) VALUES (9, 'dcastro@unl.edu');
INSERT INTO email (personID, email) VALUES (10, 'msmith@who.com');
INSERT INTO email (personID, email) VALUES (10, 'smatt@cse.unl.edu');
INSERT INTO email (personID) VALUES (11);



--
-- Table structure for table `Invoice`
--



CREATE TABLE `Invoice` (
  `invoiceID` int AUTO_INCREMENT,
  `invoiceCode` varchar(50) NOT NULL,
  `customerID` int(50) NOT NULL,
  `invoiceReportDate` varchar(50) NOT NULL,
  `personID` int(50) NOT NULL,
  PRIMARY KEY (`invoiceID`),
  FOREIGN KEY (`customerID`) REFERENCES Customers(`customerID`),
  FOREIGN KEY (`personID`) REFERENCES Person(`personID`)

);

alter table Invoice AUTO_INCREMENT = 1;
INSERT INTO Invoice (invoiceCode, customerID, invoiceReportDate, personID) VALUE ('INV001', 2, '2016-02-01', 8);
INSERT INTO Invoice (invoiceCode, customerID, invoiceReportDate, personID) VALUE ('INV002', 1, '2016-01-19', 10);
INSERT INTO Invoice (invoiceCode, customerID, invoiceReportDate, personID) VALUE ('INV003', 5, '2015-11-23', 9);
INSERT INTO Invoice (invoiceCode, customerID, invoiceReportDate, personID) VALUE ('INV004', 2, '2016-03-01', 9);

--
-- Table structure for table `ProductListInvoices`
--



CREATE TABLE `ProductListInvoices` (
  `productListID` int(50) AUTO_INCREMENT,
  `invoiceID` int(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `units` double ,
  `firstDate` varchar(50) ,
  `lastDate` varchar(50) ,
  PRIMARY KEY (`productListID`),
  FOREIGN KEY (`invoiceID`) REFERENCES Invoice(`invoiceID`)
  
);

alter table ProductListInvoices AUTO_INCREMENT = 1;
	INSERT INTO ProductListInvoices (invoiceID, code, units) VALUES (1, 'b29e', 2);
   INSERT INTO ProductListInvoices (invoiceID, code, firstDate, lastDate) VALUES (1, '90fa','2016-01-01', '2016-06-30');
    INSERT INTO ProductListInvoices (invoiceID, code, units) VALUES (2, 'ff23', 25), (2, '3289', 10), (2, '1239', 1);
    INSERT INTO ProductListInvoices (invoiceID, code, units) VALUES (3, 'ff23', 10);
    INSERT INTO ProductListInvoices (invoiceID, code, firstDate, lastDate) VALUES (3, '90fa', '2015-11-15', '2016-12-31');
    INSERT INTO ProductListInvoices (invoiceID, code, units) VALUES (4, '728g', 10);
    INSERT INTO ProductListInvoices (invoiceID, code, firstDate, lastDate) VALUES (4, '3294', '2015-01-01', '2016-12-31'), (4, '3295', '2016-02-11', '2018-12-31');

--
-- Table structure for table `Country`
--

CREATE TABLE `Country` (
  `countryID` int(5) NOT NULL AUTO_INCREMENT,
  `country` varchar(10),
  PRIMARY KEY (`countryID`)
);

INSERT INTO Country (country) VALUES ('USA');
INSERT INTO Country (country) VALUES ('Canada');


--
-- Table structure for table `States`
--

CREATE TABLE `State` (
  `stateID` int(50) NOT NULL AUTO_INCREMENT,
  `state` varchar(10),
  PRIMARY KEY (`stateID`)
);

INSERT INTO State (state) VALUES ('IL');
INSERT INTO State (state) VALUES ('NE');
INSERT INTO State (state) VALUES ('NY');
INSERT INTO State (state) VALUES ('NM');
INSERT INTO State (state) VALUES ('KS');
INSERT INTO State (state) VALUES ('MN');
INSERT INTO State (state) VALUES ('IA');
INSERT INTO State (state) VALUES ('ON');



--
-- Table structure for table `Address`
--



CREATE TABLE `Address` (
  `addressID` int(50) NOT NULL AUTO_INCREMENT,
  `street` varchar(50)  NOT NULL,
  `city` varchar(50) NOT NULL,
  `zip` int(50),
  `countryID` int(50) NOT NULL,
  `stateID` int(50) NOT NULL,
  `personID` int(50),
  `customerID` int(50),
  PRIMARY KEY (`addressID`),
  FOREIGN KEY (`personID`) REFERENCES Person(`personID`),
   FOREIGN KEY (`customerID`) REFERENCES Customers(`customerID`),
   FOREIGN KEY (`countryID`) REFERENCES Country(`countryID`),
   FOREIGN KEY (`stateID`) REFERENCES State(`stateID`)

);



INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (1, '1060 West Addison Street', 'Chicago', 1, 60613, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (2, '123 N 1st Street', 'Omaha', 2, 68116, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (3, '1 Wall Street', 'New York', 3, 10005-0012, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (4, '321 Bronx Street', 'New York', 3, 10004, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (5, '1060 West Addison Street', 'Chicago', 1, 60613, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (6, '1 Blue Jays Way', 'Toronto', 8, 'M5V 1J1', 2);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (7, 'Avery Hall', 'Lincoln', 2, 68503, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (8, '1 E 161st St', 'Bronx',  3, 10451, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (9, '700 E Grand Ave', 'Chicago', 1, 60611, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (10, '333 W 35th St', 'Chicago', 1,60616, 1);
INSERT INTO Address (personID, street, city, stateID, zip, countryID) VALUES (11, '800 West 7th Street', 'Albuquerque', 4, 87105, 1);


INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (1, '259 Avery Hall', 'Lincoln', 2, 68588, 1);
INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (2, '912 E Kirwin Ave', 'Salina', 5, 67401, 1);
INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (3, '1182 James Ave N', 'Minneapolis', 6, 55411, 1);
INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (4, '151 Easton Ave', 'Waterloo', 7, 50702, 1);
INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (5, '1060 West Addison', 'Chicago', 1, 60601, 1);
INSERT INTO Address (customerID, street, city, stateID, zip, countryID) VALUES (6, '456 West 7th St.', 'Omaha', 2, 68500, 1);



