CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS move
(
    source     CHAR(2) NOT NULL,
    target     CHAR(2) NOT NULL,
    created_at TIMESTAMP(3) DEFAULT NOW(3)
);
