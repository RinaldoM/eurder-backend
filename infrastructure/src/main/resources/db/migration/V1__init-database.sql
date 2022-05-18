
-- POSTGRES SPECIFIC SQL

-- DROP TABLES
DROP TABLE IF EXISTS ORDR_ORDER_ITEM;
DROP TABLE IF EXISTS ORDR_ORDER;
DROP TABLE IF EXISTS ORDR_ITEM;
DROP TABLE IF EXISTS ORDR_CUSTOMER;

-- CREATE ORDR_ITEM
CREATE TABLE ORDR_ITEM(
                          ID VARCHAR(36) NOT NULL,
                          NAME VARCHAR(65) NOT NULL,
                          DESCRIPTION VARCHAR(750) NOT NULL,
                          AMOUNT_OF_STOCK INTEGER NOT NULL,
                          PRICE_AMOUNT DECIMAL(38, 10) NOT NULL,
                          CONSTRAINT ITEM_PK PRIMARY KEY (ID)
);

-- CREATE ORDR_CUSTOMER
CREATE TABLE ORDR_CUSTOMER(
                              ID VARCHAR(36) NOT NULL,
                              FIRSTNAME VARCHAR(65) NOT NULL,
                              LASTNAME VARCHAR(65) NOT NULL,
                              EMAIL_LOCAL_PART VARCHAR(65) NOT NULL,
                              EMAIL_DOMAIN VARCHAR(65) NOT NULL,
                              EMAIL_COMPLETE VARCHAR(131) NOT NULL,
                              ADDRESS_STREET_NAME VARCHAR(100) NOT NULL,
                              ADDRESS_HOUSE_NUMBER VARCHAR(10) NOT NULL,
                              ADDRESS_POSTAL_CODE VARCHAR(10) NOT NULL,
                              ADDRESS_COUNTRY VARCHAR(65) NOT NULL,
                              PHONE_NUMBER VARCHAR(65) NOT NULL,
                              PHONE_COUNTRY_CALLING_CODE VARCHAR(10) NOT NULL,
                              CONSTRAINT CUSTOMER_PK PRIMARY KEY (ID)
);

-- CREATE ORDR_ORDER
CREATE TABLE ORDR_ORDER(
                           ID VARCHAR(36) NOT NULL,
                           CUSTOMER_ID VARCHAR(36) NOT NULL,
                           CONSTRAINT ORDER_PK PRIMARY KEY (ID),
                           CONSTRAINT CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES ORDR_CUSTOMER (ID)
);

-- CREATE ORDR_ORDER_ITEM
CREATE TABLE ORDR_ORDER_ITEM(
                                ORDERED_AMOUNT INTEGER NOT NULL,
                                SHIPPING_DATE DATE NOT NULL,
                                ITEM_ID VARCHAR(36) NOT NULL,
                                ITEM_PRICE_AMOUNT DECIMAL(38, 10) NOT NULL,
                                ORDER_ID VARCHAR(36) NOT NULL
);
