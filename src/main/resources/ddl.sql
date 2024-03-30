CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS room
(
    id         INT AUTO_INCREMENT,
    user_white VARCHAR(20) NOT NULL,
    user_black VARCHAR(20) NOT NULL,
    winner     VARCHAR(20) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS move
(
    room_id    INT     NOT NULL,
    source     CHAR(2) NOT NULL,
    target     CHAR(2) NOT NULL,
    created_at TIMESTAMP(3) DEFAULT NOW(3),
    FOREIGN KEY (room_id) REFERENCES room (id)
);