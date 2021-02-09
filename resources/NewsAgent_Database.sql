DROP SCHEMA IF EXISTS newsagent;
CREATE SCHEMA newsagent;
USE newsagent;
SET AUTOCOMMIT = 0;

/*Customer Table*/
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
	customer_id INTEGER AUTO_INCREMENT,
	first_name VARCHAR(20) NOT NULL DEFAULT '',
	last_name VARCHAR(20) NOT NULL DEFAULT '',
	phone_no VARCHAR(12) NOT NULL DEFAULT '',
	PRIMARY KEY (customer_id)
);
 
/*Address Table*/
DROP TABLE IF EXISTS address;
CREATE TABLE address (
	address_id INTEGER AUTO_INCREMENT,
	full_address VARCHAR(50) NOT NULL DEFAULT '',
	area_code VARCHAR(10) NOT NULL DEFAULT '',
	eir_code VARCHAR(8) NOT NULL DEFAULT '',
	PRIMARY KEY (address_id)
);
 
/*Holiday Table*/
DROP TABLE IF EXISTS holiday;
CREATE TABLE holiday (
	holiday_id INTEGER AUTO_INCREMENT,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	PRIMARY KEY (holiday_id)
);
 
/*Delivery Table*/
DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
	delivery_id INTEGER AUTO_INCREMENT,
	delivery_date DATE NOT NULL,
	PRIMARY KEY (delivery_id)
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
	prod_type VARCHAR(15) NOT NULL DEFAULT '',
	prod_price DOUBLE(4,2) NOT NULL,
	PRIMARY KEY (prod_id)
);
 
/*Invoice Table*/
DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
	invoice_id INTEGER AUTO_INCREMENT,
	issue_date DATE NOT NULL,
	PRIMARY KEY (invoice_id)
);
 
/*Stock Table*/
DROP TABLE IF EXISTS stock;
CREATE TABLE stock (
	stock_id INTEGER AUTO_INCREMENT,
	delivery_date DATE NOT NULL,
	PRIMARY KEY (stock_id)
);

/*Frequency Table*/
DROP TABLE IF EXISTS frequency;
CREATE TABLE frequency (
	freq_id INTEGER AUTO_INCREMENT,
	freq_date DATE NOT NULL,
	freq_interval VARCHAR(10) NOT NULL DEFAULT '',
	PRIMARY KEY (freq_id)
);
 
/*Address relation Table*/
DROP TABLE IF EXISTS customer_lives;
CREATE TABLE customer_lives (
	customer_id INTEGER NOT NULL,
	address_id INTEGER NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (address_id) REFERENCES address(address_id),
	PRIMARY KEY (customer_id,address_id)
);
	
/*Holidays relation Table*/
DROP TABLE IF EXISTS customer_holiday;
CREATE TABLE customer_holiday (
    customer_id INTEGER NOT NULL,
	holiday_id INTEGER NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (holiday_id) REFERENCES holiday(holiday_id),
	PRIMARY KEY (customer_id,holiday_id)
);
	
/*Frequency relation Table*/
DROP TABLE IF EXISTS prod_freq;
CREATE TABLE prod_freq (
	prod_id INTEGER NOT NULL,
	freq_id INTEGER NOT NULL,
	FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
	FOREIGN KEY (freq_id) REFERENCES frequency(freq_id),
	PRIMARY KEY (prod_id, freq_id)
);
	
/*Delivery - Publication relation Table*/
DROP TABLE IF EXISTS prod_for_delivery;
CREATE TABLE prod_for_delivery (
	delivery_id INTEGER NOT NULL,
	prod_id INTEGER NOT NULL,
	FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id),
	FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
	PRIMARY KEY (delivery_id, prod_id)
);

/*Employee - Delivery relation Table*/
DROP TABLE IF EXISTS delivers;
CREATE TABLE delivers (
	employee_id INTEGER NOT NULL,
	delivery_id INTEGER NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
	FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id),
	PRIMARY KEY (employee_id, delivery_id)
);
	
/*Delivery - Invoice relation Table*/
DROP TABLE IF EXISTS delivery_status;
CREATE TABLE delivery_status (
    delivery_status BIT,
	delivery_id INTEGER NOT NULL,
	invoice_id INTEGER NOT NULL,
	FOREIGN KEY (delivery_id) REFERENCES delivery(delivery_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id),
	PRIMARY KEY (delivery_id, invoice_id)
);
	
/*Invoice - Customer relation Table*/
DROP TABLE IF EXISTS invoice_for;
CREATE TABLE invoice_for (
	customer_id INTEGER NOT NULL,
	invoice_id INTEGER NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id),
	PRIMARY KEY (customer_id, invoice_id)
);
	
/*Customer - publication relation Table*/
DROP TABLE IF EXISTS subscription;
CREATE TABLE subscription (
	customer_id INTEGER NOT NULL,
	prod_id INTEGER NOT NULL,
	prod_count INTEGER NOT NULL,
-- 	frequency_id INTEGER NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
    PRIMARY KEY (customer_id, prod_id)
);

/*Publication - Stock relation Table*/
DROP TABLE IF EXISTS stock_received;
CREATE TABLE stock_received (
    prod_id INTEGER NOT NULL,
    stock_id INTEGER NOT NULL,
    stock_quantity INTEGER NOT NULL,
    stock_quantity_received INTEGER NOT NULL,
    FOREIGN KEY (prod_id) REFERENCES publication(prod_id),
    FOREIGN KEY (stock_id) REFERENCES stock(stock_id),
    PRIMARY KEY (prod_id, stock_id)
);