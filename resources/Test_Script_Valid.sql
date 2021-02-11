DROP SCHEMA IF EXISTS yieldnoerror;
CREATE SCHEMA yieldnoerror;
USE yieldnoerror;
SET AUTOCOMMIT = 0;

DROP TABLE IF EXISTS error;
CREATE TABLE noerror (
    noerror_id INTEGER AUTO_INCREMENT,
    PRIMARY KEY (noerror_id)
);

DROP DATABASE IF EXISTS yieldnoerror;