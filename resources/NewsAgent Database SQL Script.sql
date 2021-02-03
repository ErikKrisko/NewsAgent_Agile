DROP SCHEMA IF EXISTS NewsAgent;
CREATE SCHEMA NewsAgent;
USE NewsAgent;
SET AUTOCOMMIT = 0;

/*Customer Table*/
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
	customerId INTEGER AUTO_INCREMENT,
	firstName VARCHAR(11) NOT NULL DEFAULT '',
	lastName VARCHAR(11) NOT NULL DEFAULT '',
	phoneNo VARCHAR(12) NOT NULL DEFAULT '',
	PRIMARY KEY (customerId)
);
 /*Customer Data*/
 INSERT INTO customer VALUES (NULL,'','','');
 
 /*Address Table*/
DROP TABLE IF EXISTS address;
CREATE TABLE address (
	addressId INTEGER AUTO_INCREMENT,
	fullAddress VARCHAR(50) NOT NULL DEFAULT '',
	areaCode VARCHAR(10) NOT NULL DEFAULT '',
	eircode VARCHAR(8) NOT NULL DEFAULT '',
	customerId INTEGER NOT NULL,
	PRIMARY KEY (addressId)
);
 /*Address Data*/
 INSERT INTO address VALUES (NULL,'','','');
 
 /*Holiday Table*/
DROP TABLE IF EXISTS holiday;
CREATE TABLE holiday (
	holidayId INTEGER AUTO_INCREMENT,
	startDate DATE NOT NULL DEFAULT '',
	endDate DATE NOT NULL DEFAULT '',
	customerId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	PRIMARY KEY (holidayId,customerId)
);
 /*Holiday Data*/
 INSERT INTO holiday VALUES (NULL,'','',1);
 
 /*Delivery Table*/
DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
	deliveryId INTEGER AUTO_INCREMENT,
	date DATE NOT NULL DEFAULT '',
	FOREIGN KEY (employeeId) REFERENCES employee(employeeId),
	PRIMARY KEY (holidayId,employeeId)
);
 /*Delivery Data*/
 INSERT INTO delivery VALUES (NULL,'',1);
 
    /*Employee Table*/
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	employeeId INTEGER AUTO_INCREMENT,
	firstName VARCHAR(11) NOT NULL DEFAULT '',
	lastName VARCHAR(11) NOT NULL DEFAULT '',
	PRIMARY KEY (employeeId)
);
 /*Employee Data*/
 INSERT INTO employee VALUES (NULL,'','');
 
 /*Publication Table*/
DROP TABLE IF EXISTS publication;
CREATE TABLE publication (
	prodId INTEGER AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL DEFAULT '',
	type VARCHAR(15) NOT NULL DEFAULT '',
	price DOUBLE(4,2) NOT NULL DEFAULT '',
	PRIMARY KEY (prodId)
);
 /*Publication Data*/
 INSERT INTO publication VALUES (NULL,'Irish Independent','Newspaper',11.25);
 
  /*Invoice Table*/
DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
	invoiceId INTEGER AUTO_INCREMENT,
	issueDate DATE NOT NULL DEFAULT '',
	PRIMARY KEY (invoiceId)
);
 /*Invoice Data*/
 INSERT INTO invoice VALUES (NULL,'');
 
   /*Stock Table*/
DROP TABLE IF EXISTS stock;
CREATE TABLE stock (
	stockId INTEGER AUTO_INCREMENT,
	deliveryDate DATE NOT NULL DEFAULT '',
	PRIMARY KEY (stockId)
);
 /*Stock Data*/
 INSERT INTO stock VALUES (NULL,'');
 
    /*Frequency Table*/
DROP TABLE IF EXISTS frequency;
CREATE TABLE frequency (
	frequencyId INTEGER AUTO_INCREMENT,
	baseDate DATE NOT NULL DEFAULT '',
	interval VARCHAR(10) NOT NULL DEFAULT '',
	PRIMARY KEY (stockId)
);
 /*Stock Data*/
 INSERT INTO stock VALUES (NULL,'','');
 
 
 
 
  /*Is_in Table*/
 DROP TABLE IF EXISTS is_in;
  CREATE TABLE is_in (
	customerId INTEGER NOT NULL,
	addressId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (addressId) REFERENCES address(addressId),
	PRIMARY KEY (customerId,addressId)	
);
/*Is_in Data*/
	INSERT INTO is_in VALUES (1,1);
	
/*May_have Table*/
 DROP TABLE IF EXISTS may_have;
  CREATE TABLE may_have (
	customerId INTEGER NOT NULL,
	holidayId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (holidayId) REFERENCES holiday(holidayId),
	PRIMARY KEY (customerId,holidayId)	
);
/*May_have Data*/
	INSERT INTO may_have VALUES (1,1);
	
/*Must_have Table*/
 DROP TABLE IF EXISTS must_have;
  CREATE TABLE must_have (
	prodId INTEGER NOT NULL,
	frequencyId INTEGER NOT NULL,
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	FOREIGN KEY (frequencyId) REFERENCES frequency(frequencyId),
	PRIMARY KEY (prodId,frequencyId)	
);
/*Must_have Data*/
	INSERT INTO must_have VALUES (1,1);
	
/*Contains Table*/
 DROP TABLE IF EXISTS contains;
  CREATE TABLE contains (
	deliveryId INTEGER NOT NULL,
	prodId INTEGER NOT NULL,
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	PRIMARY KEY (deliveryId,prodIdId)	
);
/*Contains Data*/
	INSERT INTO contains VALUES (1,1);
	
/*Delivers Table*/
 DROP TABLE IF EXISTS delivers;
  CREATE TABLE delivers (
	employeeId INTEGER NOT NULL,
	deliveryId INTEGER NOT NULL,
	FOREIGN KEY (employeeId) REFERENCES employee(employeeId),
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	PRIMARY KEY (employeeId,deliveryId)	
);
/*Delivers Data*/
	INSERT INTO delivers VALUES (1,1);
	
/*Completed Table*/
 DROP TABLE IF EXISTS delivers;
  CREATE TABLE delivers (
	completed VARCHAR(1) NOT NULL DEFAULT '',
	deliveryId INTEGER NOT NULL,
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	PRIMARY KEY (deliveryId)	
);
/*Completed Data*/
	INSERT INTO delivers VALUES ('T',1);
	
/*Issued_for Table*/
 DROP TABLE IF EXISTS issued_for;
  CREATE TABLE issued_for (
	customerId INTEGER NOT NULL,
	invoiceId INTEGER NOT NULL,
	issue_date DATE NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (invoiceId) REFERENCES invoice(invoiceId),
	FOREIGN KEY (issue_date) REFERENCES invoice(issue_date),
	PRIMARY KEY (customerId,invoiceId,issue_date)	
);
/*Issued_for Data*/
	INSERT INTO issued_for VALUES (1,1,'2020-12-20');
	
/*Subscribes Table*/
 DROP TABLE IF EXISTS subscribes;
  CREATE TABLE subscribes (
	customerId INTEGER NOT NULL,
	prodIdId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	PRIMARY KEY (customerId,prodId)	
);
/*Subscribes Data*/
	INSERT INTO delivers VALUES (1,1);
	
/*In_stock Table*/
 DROP TABLE IF EXISTS delivers;
  CREATE TABLE delivers (
	prodId INTEGER NOT NULL,
	stockId INTEGER NOT NULL,
	quantity INTEGER NOT NULL ,
	quantity_recieved INTEGER NOT NULL ,
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	FOREIGN KEY (stockId) REFERENCES stock(stockId),
	PRIMARY KEY (prodId,stockId)	
);
/*In_stock Data*/
	INSERT INTO in_stock VALUES (1,1);