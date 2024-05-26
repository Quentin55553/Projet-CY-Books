--
-- Database: `CY-Books`
--
DROP DATABASE IF EXISTS `CY-Books`;

CREATE DATABASE `CY-Books`;
USE `CY-Books`;


-- -----------------------------------------------------------


CREATE TABLE `books` (
  `id` varchar(50) NOT NULL PRIMARY KEY,
  `quantity` int(11) DEFAULT 5,
  `stock` int(11) DEFAULT 5
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
('Fillion', 'Quentin', '0601020304', 'quentin.fillion@gmail.com', '1 Avenue des Champs-Elysées', 1),
('Belliere', 'Théo', '0602030405', 'theo.belliere@gmail.com', '3 Rue de la Paix', 4),
('Martin', 'Julie', '0603040506', 'julie.martin@gmail.com', '28 Avenue de Rivoli', 2),
('Duval', 'Lucas', '0604050607', 'lucas.duval@gmail.com', '6 Boulevard Saint-Germain', 3),
('Leroy', 'Emma', '0605060708', 'emma.leroy@gmail.com', '17 Rue de Belleville', 0),
('Roux', 'Hugo', '0606070809', 'hugo.roux@gmail.com', '56 Rue de Charonne', 1),
('Moreau', 'Clara', '0607080901', 'clara.moreau@gmail.com', '12 Avenue Montaigne', 0),
('Simon', 'Arthur', '0608090102', 'arthur.simon@gmail.com', '16 Rue de la Pompe', 1),
('Laurent', 'Chloe', '0609010203', 'chloe.laurent@gmail.com', '20 Rue Université', 0),
('Michel', 'Louis', '0610203040', 'louis.michel@gmail.com', '7 Rue de la République', 2),
('Bernard', 'Alice', '0610203041', 'alice.bernard@gmail.com', '14 Boulevard Haussmann', 0),
('Petit', 'Jean', '0610203042', 'jean.petit@gmail.com', '18 Avenue de Clichy', 1),
('Durand', 'Nina', '0610203043', 'nina.durand@gmail.com', '5 Allée des Acacias', 3),
('Lemoine', 'Paul', '0610203044', 'paul.lemoine@gmail.com', '15 Rue de Lyon', 0),
('Morel', 'Lea', '0610203045', 'lea.morel@gmail.com', '13 Rue du Bac', 2),
('Fournier', 'Max', '0610203046', 'max.fournier@gmail.com', '10 Quai de la Seine', 0),
('Garnier', 'Sophie', '0610203047', 'sophie.garnier@gmail.com', '9 Place de la Concorde', 0),
('Lambert', 'Nicolas', '0610203048', 'nicolas.lambert@gmail.com', '10 Rue Amazone', 1),
('Henry', 'Marie', '0610203049', 'marie.henry@gmail.com', '8 Avenue Victor Hugo', 2),
('Rousseau', 'Thomas', '0610203050', 'thomas.rousseau@gmail.com', '60 Avenue du Parc', 1);


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
('12148/cb34348467k', 2, '2024-05-12', '2024-05-16', 1),
('12148/bc6p06wqkzq', 3, '2024-05-14', '2024-05-18', 0),
('12148/bc6p06zd9f2', 4, '2024-05-14', '2024-05-20', 0),
('12148/bc6p06zdb17', 4, '2024-05-14', '2024-05-22', 1),
('12148/bc6p06zdk33', 5, '2024-05-12', '2024-05-16', 0),
('12148/bc6p06zgx6s', 6, '2024-05-12', '2024-05-19', 0),
('12148/bc6p070wjcv', 7, '2024-05-12', '2024-05-17', 1),
('12148/bc6p070wtkd', 7, '2024-05-13', '2024-05-18', 0),
('12148/bc6p070z05c', 8, '2024-05-13', '2024-05-19', 0),
('12148/bc6p070zkng', 8, '2024-05-13', '2024-05-20', 1),
('12148/bc6p070zmhd', 9, '2024-05-14', '2024-05-21', 0),
('12148/bc6p070zp78', 9, '2024-05-14', '2024-05-22', 0),
('12148/bc6p0713fvn', 10, '2024-05-14', '2024-05-20', 1),
('12148/bc6p0714050', 10, '2024-05-14', '2024-05-21', 0),
('12148/bc6p07142nf', 11, '2024-05-15', '2024-05-22', 0),
('12148/bc6p07144dn', 12, '2024-05-15', '2024-05-23', 0),
('12148/bc6p07149zq', 13, '2024-05-15', '2024-05-24', 1),
('12148/bc6p0715d50', 13, '2024-05-15', '2024-05-22', 0),
('12148/bc6p0715f28', 14, '2024-05-15', '2024-05-23', 1),
('12148/bc6p0715jj1', 14, '2024-05-16', '2024-05-24', 0),
('12148/bc6p0715s5d', 15, '2024-05-16', '2024-05-25', 0),
('12148/bc6p0715vrw', 15, '2024-05-16', '2024-05-24', 0);