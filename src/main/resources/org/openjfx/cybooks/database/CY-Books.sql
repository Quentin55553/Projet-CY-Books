--
-- Database: `CY-Books`
--
DROP DATABASE IF EXISTS `CY-Books`;

CREATE DATABASE `CY-Books`;
USE `CY-Books`;


-- -----------------------------------------------------------


CREATE TABLE `books` (
  `id` varchar(50) NOT NULL PRIMARY KEY,
  `quantity` int(11) DEFAULT 0,
  `stock` int(11) DEFAULT 0
);


INSERT INTO `books` (`id`, `quantity`, `stock`) VALUES
                                                  ('12148/bc6p06wqkzq', 5, 5),
                                                  ('12148/bc6p06zd9f2', 5, 5),
                                                  ('12148/bc6p06zdb17', 5, 5),
                                                  ('12148/bc6p06zdk33', 5, 5),
                                                  ('12148/bc6p06zgx6s', 5, 5),
                                                  ('12148/bc6p070wjcv', 5, 5),
                                                  ('12148/bc6p070wtkd', 5, 5),
                                                  ('12148/bc6p070z05c', 5, 5),
                                                  ('12148/bc6p070zkng', 5, 5),
                                                  ('12148/bc6p070zmhd', 5, 5),
                                                  ('12148/bc6p070zp78', 5, 5),
                                                  ('12148/bc6p0713fvn', 5, 5),
                                                  ('12148/bc6p0714050', 5, 5),
                                                  ('12148/bc6p07142nf', 5, 5),
                                                  ('12148/bc6p07144dn', 5, 5),
                                                  ('12148/bc6p07149zq', 5, 5),
                                                  ('12148/bc6p0715d50', 5, 5),
                                                  ('12148/bc6p0715f28', 5, 5),
                                                  ('12148/bc6p0715jj1', 5, 5),
                                                  ('12148/bc6p0715s5d', 5, 5),
                                                  ('12148/bc6p0715vrw', 5, 5),
                                                  ('12148/bc6p0715zdc', 5, 5),
                                                  ('12148/bc6p071620f', 5, 5),
                                                  ('12148/bc6p07163km', 5, 5),
                                                  ('12148/bc6p0716c4m', 5, 5),
                                                  ('12148/bpt6k33646735', 2, 0),
                                                  ('12148/cb32729500t', 5, 5),
                                                  ('12148/cb34348467k', 5, 4),
                                                  ('12148/cb34372185v', 5, 5),
                                                  ('12148/cb344012968', 5, 5),
                                                  ('12148/cb344198366', 5, 5);


-- -----------------------------------------------------------


CREATE TABLE `customers` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `tel` varchar(12) UNIQUE DEFAULT NULL,
  `email` varchar(50) UNIQUE DEFAULT NULL,
  `address` text DEFAULT NULL,
  `loan_count` int DEFAULT 0
);


INSERT INTO `customers` (`last_name`, `first_name`, `tel`, `email`, `address`, `loan_count`) VALUES
('FILLION', 'Quentin', '0601020304', 'quentin.fillion@gmail.com', 'adresse1', 1),
('Belliere', 'Theo', '0602030405', 'theo.belliere@gmail.com', 'adresse2', 4);


-- -----------------------------------------------------------


CREATE TABLE `librarians` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `login` varchar(50) UNIQUE NOT NULL,
  `password` varchar(100) NOT NULL
);


INSERT INTO `librarians` (`last_name`, `first_name`, `login`, `password`) VALUES
('admin', 'admin', 'admin', '$2a$10$3UUh4ZNoASpFshfQFMMMx.Rb02A0CfIBd70nh0XWuc4VIoTcLKqym');


-- -----------------------------------------------------------


CREATE TABLE `loans` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `book_id` varchar(50) NOT NULL,
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
('12148/bpt6k33646735', 1, '2024-05-14', '2024-05-18', 0),
('12148/bpt6k33646735', 2, '2024-05-14', '2024-05-20', 0),
('12148/cb34348467k', 2, '2024-05-12', '2024-05-16', 0),
('12148/cb34348467k', 2, '2024-05-12', '2024-05-19', 1),
('12148/cb34348467k', 2, '2024-05-12', '2024-05-16', 1);