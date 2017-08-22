--liquibase formatted sql

-- CREATE DATABASE roastery COLLATE 'utf8_general_ci';
--changeset tw:1

CREATE TABLE user (
  username         VARCHAR(50) NOT NULL PRIMARY KEY,
  email            VARCHAR(50),
  password         VARCHAR(500),
  activated        BOOLEAN     DEFAULT FALSE,
  activationkey    VARCHAR(50) DEFAULT NULL,
  resetpasswordkey VARCHAR(50) DEFAULT NULL
);

CREATE TABLE authority (
  name VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE user_authority (
  username  VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES user (username),
  FOREIGN KEY (authority) REFERENCES authority (name),
  UNIQUE INDEX user_authority_idx_1 (username, authority)
);

CREATE TABLE oauth_access_token (
  token_id          VARCHAR(256) DEFAULT NULL,
  token             BLOB,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name         VARCHAR(256) DEFAULT NULL,
  client_id         VARCHAR(256) DEFAULT NULL,
  authentication    BLOB,
  refresh_token     VARCHAR(256) DEFAULT NULL
);

CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256) DEFAULT NULL,
  token          BLOB,
  authentication BLOB
);

CREATE TABLE customer (
  id         VARCHAR(50) NOT NULL PRIMARY KEY,
  first_name            VARCHAR(50),
  last_name            VARCHAR(50)
);

CREATE TABLE address (
  id         VARCHAR(50) NOT NULL PRIMARY KEY,
  city            VARCHAR(50)
);

CREATE TABLE customer_address (
  customer_id         VARCHAR(50) NOT NULL,
  address_id         VARCHAR(50) NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer (id),
  FOREIGN KEY (address_id) REFERENCES address (id),
  UNIQUE INDEX customer_address_idx_1 (customer_id, address_id)
);

--changeset xiangchen:2

INSERT INTO user (username, email, password, activated) VALUES
  ('admin', 'admin@mail.me', 'd19dc25bec1c14c7fabc293ea34111eafe725bf3f060017b7dc6a30eab64e6e668ce4b149edf27f1', TRUE);
INSERT INTO user (username, email, password, activated) VALUES
  ('todo', 'todo@mail.me', '365ba0ecf14eb0c1d8ca97e46d2f50eec5538055f930b84596024f0e3613a5cbc4f27b379ab46e49', TRUE);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username, authority) VALUES ('todo', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_ADMIN');

alter table customer MODIFY id BIGINT AUTO_INCREMENT;
alter table address MODIFY id BIGINT AUTO_INCREMENT;

INSERT INTO customer (id, first_name, last_name) VALUES ('0','firstname0', 'lastname0');
INSERT INTO customer (id, first_name, last_name) VALUES ('1','firstname1', 'lastname1');

INSERT INTO address (id, city) VALUES ('0','chengdu');
INSERT INTO address (id, city) VALUES ('1','beijing');
INSERT INTO address (id, city) VALUES ('2','xian');

INSERT INTO customer_address (customer_id, address_id) VALUES ('0', '0');
INSERT INTO customer_address (customer_id, address_id) VALUES ('0', '1');
INSERT INTO customer_address (customer_id, address_id) VALUES ('1', '2');
