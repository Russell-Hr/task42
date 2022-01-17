DROP TABLE customer;

CREATE TABLE customer
(
    id INT auto_increment UNIQUE PRIMARY key,
    name VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    balance INT NOT NULL DEFAULT '0'
)ENGINE=INNODB;