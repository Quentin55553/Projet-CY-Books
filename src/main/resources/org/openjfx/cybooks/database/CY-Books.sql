--
-- Database: `CY-Books`
--
DROP DATABASE IF EXISTS `CY-Books`;

CREATE DATABASE `CY-Books`;
USE `CY-Books`;


-- -----------------------------------------------------------


CREATE TABLE `books` (
  `id` int(11) NOT NULL PRIMARY KEY,
  `quantity` int(11) DEFAULT 0,
  `stock` int(11) DEFAULT 0
);


INSERT INTO `books` (`id`, `quantity`, `stock`) VALUES
(22784, 2, 0),
(28737, 5, 2);


-- -----------------------------------------------------------


CREATE TABLE `customers` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `tel` varchar(12) UNIQUE DEFAULT NULL,
  `email` varchar(50) UNIQUE DEFAULT NULL,
  `address` text DEFAULT NULL
);


INSERT INTO `customers` (`last_name`, `first_name`, `tel`, `email`, `address`) VALUES
('FILLION', 'Quentin', '0601020304', 'quentin.fillion@gmail.com', 'adresse1'),
('Belliere', 'Theo', '0602030405', 'theo.belliere@gmail.com', 'adresse2');


-- -----------------------------------------------------------


CREATE TABLE `librarians` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  UNIQUE KEY `login` (`login`)
);


INSERT INTO `librarians` (`last_name`, `first_name`, `login`, `password`) VALUES
('admin', 'admin', 'admin', '$2a$10$3UUh4ZNoASpFshfQFMMMx.Rb02A0CfIBd70nh0XWuc4VIoTcLKqym');


-- -----------------------------------------------------------


CREATE TABLE `loans` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `begin_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `expiration_date` date NOT NULL,
  `completed` tinyint(1) DEFAULT 0,
  KEY `book_id` (`book_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `loans_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
);


INSERT INTO `loans` (`book_id`, `customer_id`, `begin_date`, `expiration_date`, `completed`) VALUES
(22784, 1, '2024-05-14', '2024-05-18', 0),
(22784, 2, '2024-05-14', '2024-05-20', 0),
(28737, 2, '2024-05-12', '2024-05-16', 0),
(28737, 2, '2024-05-12', '2024-05-19', 1),
(28737, 2, '2024-05-12', '2024-05-16', 1);