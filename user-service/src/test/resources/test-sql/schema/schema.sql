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