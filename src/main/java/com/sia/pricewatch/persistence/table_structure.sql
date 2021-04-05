create DATABASE PRICE_WATCH;

USE PRICE_WATCH;

drop table IF EXISTS SUBSCRIBE_USER;
drop table IF EXISTS FARE_HISTOGRAM;

create TABLE SUBSCRIBE_USER (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  DEVICE_UID VARCHAR(255) NOT NULL,
  TOKEN VARCHAR(255) NOT NULL,
  ORIGIN_AIRPORT_CODE VARCHAR(3) NOT NULL,
  DESTINATION_AIRPORT_CODE VARCHAR(3) NOT NULL,
  DEPARTURE_DATE DATE NOT NULL,
  DATE_RANGE INT NOT NULL,
  PRICE DECIMAL NOT NULL,
  CABIN_CLASS VARCHAR(2) NOT NULL,
  CREATED_DATE DATE NOT NULL,
  FREQUENCY INT NULL
);

create TABLE FARE_HISTOGRAM (
    ORIGIN_CODE VARCHAR(3) NOT NULL,
    DESTINATION_CODE VARCHAR(3) NOT NULL,
    DEPARTURE_DATE DATE NOT NULL,
    PRICE DECIMAL NOT NULL,
    DEVICE_UID VARCHAR(255) NOT NULL,
    IS_NOTIFIED VARCHAR(1) NOT NULL ,
    CONSTRAINT ORIGIN_DESTINATION_DEP_DATE PRIMARY KEY (ORIGIN_CODE, DESTINATION_CODE, DEPARTURE_DATE)
);