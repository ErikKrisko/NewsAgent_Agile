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
 
 /*Address Table*/
DROP TABLE IF EXISTS address;
CREATE TABLE address (
	addressId INTEGER AUTO_INCREMENT,
	fullAddress VARCHAR(50) NOT NULL DEFAULT '',
	areaCode VARCHAR(10) NOT NULL DEFAULT '',
	eircode VARCHAR(8) NOT NULL DEFAULT '',
	PRIMARY KEY (addressId)
);
 
 /*Holiday Table*/
DROP TABLE IF EXISTS holiday;
CREATE TABLE holiday (
	holidayId INTEGER AUTO_INCREMENT,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	PRIMARY KEY (holidayId)
);
 
 /*Delivery Table*/
DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
	deliveryId INTEGER AUTO_INCREMENT,
	date DATE NOT NULL,
	PRIMARY KEY (deliveryId)
);
    /*Employee Table*/
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	employeeId INTEGER AUTO_INCREMENT,
	firstName VARCHAR(11) NOT NULL DEFAULT '',
	lastName VARCHAR(11) NOT NULL DEFAULT '',
	PRIMARY KEY (employeeId)
);

 /*Publication Table*/
DROP TABLE IF EXISTS publication;
CREATE TABLE publication (
	prodId INTEGER AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL DEFAULT '',
	type VARCHAR(15) NOT NULL DEFAULT '',
	price DOUBLE(4,2) NOT NULL DEFAULT,
	PRIMARY KEY (prodId)
);
 
  /*Invoice Table*/
DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
	invoiceId INTEGER AUTO_INCREMENT,
	issueDate DATE NOT NULL,
	PRIMARY KEY (invoiceId)
);
 
   /*Stock Table*/
DROP TABLE IF EXISTS stock;
CREATE TABLE stock (
	stockId INTEGER AUTO_INCREMENT,
	deliveryDate DATE NOT NULL,
	PRIMARY KEY (stockId)
);
    /*Frequency Table*/
DROP TABLE IF EXISTS frequency;
CREATE TABLE frequency (
	frequencyId INTEGER AUTO_INCREMENT,
	baseDate DATE NOT NULL,
	interval VARCHAR(10) NOT NULL DEFAULT '',
	PRIMARY KEY (frequencyId)
);
 
  /*Is_in Table*/
DROP TABLE IF EXISTS customer_lives;
CREATE TABLE customer_lives (
	customerId INTEGER NOT NULL,
	addressId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (addressId) REFERENCES address(addressId),
	PRIMARY KEY (customerId,addressId)	
);
	
/*May_have Table*/
DROP TABLE IF EXISTS customer_holiday;
CREATE TABLE customer_holiday (
	customerId INTEGER NOT NULL,
	holidayId INTEGER NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (holidayId) REFERENCES holiday(holidayId),
	PRIMARY KEY (customerId,holidayId)	
);
	
/*Must_have Table*/
DROP TABLE IF EXISTS prod_freq;
CREATE TABLE prod_freq (
	prodId INTEGER NOT NULL,
	frequencyId INTEGER NOT NULL,
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	FOREIGN KEY (frequencyId) REFERENCES frequency(frequencyId),
	PRIMARY KEY (prodId,frequencyId)	
);
	
/*Contains Table*/
DROP TABLE IF EXISTS prod_for_delivery;
CREATE TABLE prod_for_delivery (
	deliveryId INTEGER NOT NULL,
	prodId INTEGER NOT NULL,
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
	PRIMARY KEY (deliveryId,prodIdId)	
);

/*Delivers Table*/
DROP TABLE IF EXISTS delivers;
CREATE TABLE delivers (
	employeeId INTEGER NOT NULL,
	deliveryId INTEGER NOT NULL,
	FOREIGN KEY (employeeId) REFERENCES employee(employeeId),
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	PRIMARY KEY (employeeId,deliveryId)	
);
	
/*Completed Table*/
DROP TABLE IF EXISTS delivery_status;
CREATE TABLE delivery_status (
    completed VARCHAR(1) NOT NULL DEFAULT '',
	deliveryId INTEGER NOT NULL,
	FOREIGN KEY (deliveryId) REFERENCES delivery(deliveryId),
	PRIMARY KEY (deliveryId)	
);
	
/*Issued_for Table*/
DROP TABLE IF EXISTS invoice_for;
CREATE TABLE invoice_for (
	customerId INTEGER NOT NULL,
	invoiceId INTEGER NOT NULL,
	issue_date DATE NOT NULL,
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (invoiceId) REFERENCES invoice(invoiceId),
	PRIMARY KEY (customerId,invoiceId)
);
	
/*Subscribes Table*/
DROP TABLE IF EXISTS cust_subscription;
CREATE TABLE cust_subscription (
	customerId INTEGER NOT NULL,
	prodId INTEGER NOT NULL,
	count INTEGER NOT NULL,
	base_date DATE NOT NULL,
	interval VARCHAR(10) NOT NULL DEFAULT '',
	FOREIGN KEY (customerId) REFERENCES customer(customerId),
	FOREIGN KEY (prodId) REFERENCES publication(prodId),
    PRIMARY KEY (customerId,prodId)
);
	
/*In_stock Table*/
DROP TABLE IF EXISTS delivers;
CREATE TABLE delivers
(
    prodId INTEGER NOT NULL,
    stockId INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    quantity_received INTEGER NOT NULL,
    FOREIGN KEY (prodId) REFERENCES publication (prodId),
    FOREIGN KEY (stockId) REFERENCES stock (stockId),
    PRIMARY KEY (prodId, stockId)
);