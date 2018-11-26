DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  id         BIGINT IDENTITY,
  login      VARCHAR(255) UNIQUE,
  password   VARCHAR(255),
  enabled    BOOLEAN,
  permission VARCHAR(255)
);

DROP TABLE IF EXISTS Authority;
CREATE TABLE Authority (
  id          BIGINT IDENTITY,
  name        VARCHAR(255) UNIQUE,
  description VARCHAR(255)
);

INSERT INTO USER (login, password, enabled, permission) VALUES ('user1', 'password1', TRUE, 'USER');
INSERT INTO USER (login, password, enabled, permission) VALUES ('user2', 'password2', TRUE, 'USER');
INSERT INTO USER (login, password, enabled, permission) VALUES ('user3', 'password3', TRUE, 'USER');
INSERT INTO USER (login, password, enabled, permission) VALUES ('admin', 'admin', TRUE, 'ADMIN');
INSERT INTO USER (login, password, enabled, permission) VALUES ('superadmin', 'superadmin', TRUE, 'SUPER_ADMIN');