DROP SCHEMA IF EXISTS newsagent;
CREATE SCHEMA newsagent;
USE newsagent;
SET AUTOCOMMIT = 0;

/*Address Table*/
DROP TABLE IF EXISTS address;
CREATE TABLE address (
     address_id INTEGER AUTO_INCREMENT,
     full_address VARCHAR(50) NOT NULL DEFAULT '',
     area_code INTEGER NOT NULL DEFAULT 0,
     eir_code VARCHAR(8) NOT NULL DEFAULT '',
     PRIMARY KEY (address_id)
);

/*Customer Table*/
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
	customer_id INTEGER AUTO_INCREMENT,
	first_name VARCHAR(20) NOT NULL DEFAULT '',
	last_name VARCHAR(20) NOT NULL DEFAULT '',
	phone_no VARCHAR(12) NOT NULL DEFAULT '',
	address_id INTEGER NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address(address_id) ON DELETE CASCADE,
	PRIMARY KEY (customer_id)
);

/*Invoice Table*/
DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
     invoice_id INTEGER AUTO_INCREMENT,
     issue_date DATE NOT NULL,
     invoice_status BIT NOT NULL,
     invoice_total DECIMAL(5,2) NOT NULL,
     customer_id INTEGER NOT NULL,
     FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
     PRIMARY KEY (invoice_id)
);

/*Holiday Table*/
DROP TABLE IF EXISTS holiday;
CREATE TABLE holiday (
	holiday_id INTEGER AUTO_INCREMENT,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
    customer_id INTEGER NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
	PRIMARY KEY (holiday_id)
);

/*Employee Table*/
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	employee_id INTEGER AUTO_INCREMENT,
	first_name VARCHAR(20) NOT NULL DEFAULT '',
	last_name VARCHAR(20) NOT NULL DEFAULT '',
	PRIMARY KEY (employee_id)
);

/*Publication Table*/
DROP TABLE IF EXISTS publication;
CREATE TABLE publication (
	prod_id INTEGER AUTO_INCREMENT,
	prod_name VARCHAR(25) NOT NULL DEFAULT '',
	prod_type VARCHAR(25) NOT NULL DEFAULT '',
	prod_price DECIMAL(4,2) NOT NULL,
	frequency VARCHAR(25) NOT NULL,
	PRIMARY KEY (prod_id)
);

/*Delivery Table*/
DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
      delivery_id INTEGER AUTO_INCREMENT,
      delivery_date DATE NOT NULL,
      delivery_status BIT NOT NULL,
      customer_id INTEGER NOT NULL,
      invoice_id INTEGER NOT NULL,
      prod_id INTEGER NOT NULL,
      FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
      FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id) ON DELETE CASCADE,
      FOREIGN KEY (prod_id) REFERENCES publication(prod_id) ON DELETE CASCADE,
      PRIMARY KEY (delivery_id)
);
 
/*Stock Table*/
DROP TABLE IF EXISTS stock;
CREATE TABLE stock (
	stock_id INTEGER AUTO_INCREMENT,
	stock_date DATE NOT NULL,
	stock_quantity INTEGER NOT NULL,
	stock_code VARCHAR(10) NOT NULL,
    prod_id INTEGER NOT NULL,
    stock_quantity_received INTEGER NOT NULL,
    FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
	PRIMARY KEY (stock_id)
);
 
/*Prod_for_delivery relation Table*/
/*REMOVED as it was like a third leg stuck to the chest. Changes made to delivery table*/
-- DROP TABLE IF EXISTS prod_for_delivery;
-- CREATE TABLE prod_for_delivery (
-- 	prod_id INTEGER NOT NULL,
-- 	delivery_id INTEGER NOT NULL,
-- 	FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
-- 	FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id) ON DELETE CASCADE,
-- 	PRIMARY KEY (prod_id,delivery_id)
-- );
	
/*Delivers relation Table*/
DROP TABLE IF EXISTS delivers;
CREATE TABLE delivers (
    delivery_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,
	FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id) ON DELETE CASCADE,
	FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE,
	PRIMARY KEY (delivery_id,employee_id)
);

/*Subscription relation Table*/
DROP TABLE IF EXISTS subscription;
CREATE TABLE subscription (
      customer_id INTEGER NOT NULL,
      prod_id INTEGER NOT NULL,
      count INTEGER NOT NULL,
      FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
      FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
      PRIMARY KEY (customer_id,prod_id)
);