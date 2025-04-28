CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL  NOT NULL PRIMARY KEY,
    name     VARCHAR(255),
    surname  VARCHAR(255),
    password VARCHAR(255),
    email    VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS universities
(
    id   SERIAL  NOT NULL PRIMARY KEY,
    name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS courses
(
    id     SERIAL  NOT NULL PRIMARY KEY,
    title  VARCHAR(255),
    user_id INT REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS books
(
    id       SERIAL  NOT NULL  PRIMARY KEY,
    name     VARCHAR(255),
    author   VARCHAR(255),
    publisher VARCHAR(255),
    user_id  INT REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS universities_of_users
(
    university_id BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    CONSTRAINT pk_universities_of_users PRIMARY KEY (university_id, user_id),
    CONSTRAINT fk_university FOREIGN KEY (university_id) REFERENCES universities(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
    );