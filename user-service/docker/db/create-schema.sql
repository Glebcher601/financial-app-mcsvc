CREATE DATABASE IF NOT EXISTS userdb;
USE userdb;

DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  login    VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  enabled  BOOLEAN
);

DROP TABLE IF EXISTS PERMISSION;
CREATE TABLE PERMISSION (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  `key`       VARCHAR(255),
  description VARCHAR(255)
);

DROP TABLE IF EXISTS USER_PERMISSION;
CREATE TABLE USER_PERMISSION (
  user_id       BIGINT,
  permission_id BIGINT,
  PRIMARY KEY (user_id, permission_id),
  CONSTRAINT user_permission_fk_1
  FOREIGN KEY (user_id) REFERENCES USER (id),
  CONSTRAINT user_permission_fk_2
  FOREIGN KEY (permission_id) REFERENCES PERMISSION (id)
);

INSERT INTO PERMISSION (id, `key`, description) VALUES (1, 'admin_permission', 'Admin permission');
INSERT INTO PERMISSION (id, `key`, description) VALUES (2, 'user_permission', 'User permission');
INSERT INTO PERMISSION (id, `key`, description) VALUES (3, 'actuator_permission', 'Actuator permission');

INSERT INTO USER (id, login, password, enabled) VALUES (4, 'admin', 'admin', TRUE);