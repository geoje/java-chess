CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS room
(
    id       INT         NOT NULL,
    username VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS move
(
    room_id    INT     NOT NULL,
    source     CHAR(2) NOT NULL,
    target     CHAR(2) NOT NULL,
    created_at TIMESTAMP(3) DEFAULT NOW(3)
);