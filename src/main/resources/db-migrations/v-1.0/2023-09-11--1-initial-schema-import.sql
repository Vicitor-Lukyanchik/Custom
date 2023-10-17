DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS chop CASCADE;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(50)           NOT NULL,
    password VARCHAR(60)           NOT NULL,
    status   VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE role
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    name   VARCHAR(50),
    status VARCHAR(25)           NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);

CREATE TABLE chop
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    number VARCHAR(6)           NOT NULL,
    name   VARCHAR(20)          NOT NULL,
    description VARCHAR(50) NOT NULL
);