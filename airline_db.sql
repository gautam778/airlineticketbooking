-- Airline Ticket Booking Database

-- ------------------------------------------------------
-- Table structure for `flights`
-- ------------------------------------------------------

DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flight_number` varchar(20) NOT NULL,
  `source` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `seats_available` int NOT NULL,
  `type` varchar(20) DEFAULT 'domestic',
  PRIMARY KEY (`id`)
);

-- Insert sample flight data
INSERT INTO `flights` VALUES
(1,'AI101','Delhi','Mumbai','2025-09-10',100,'domestic'),
(2,'AI202','Mumbai','Bangalore','2025-09-12',100,'domestic'),
(3,'AI303','Delhi','Chennai','2025-09-15',100,'international'),
(4,'AI404','Delhi','London','2025-09-20',100,'international'),
(5,'AI102','Mumbai','Bangalore','2025-09-11',100,'domestic'),
(6,'AI103','Chennai','Kolkata','2025-09-12',100,'domestic'),
(7,'AI104','Delhi','Chennai','2025-09-13',100,'domestic'),
(8,'AI105','Hyderabad','Pune','2025-09-14',100,'domestic'),
(9,'AI106','Kolkata','Delhi','2025-09-15',100,'domestic'),
(10,'AI107','Bangalore','Hyderabad','2025-09-16',100,'domestic'),
(11,'AI108','Pune','Mumbai','2025-09-17',100,'domestic'),
(12,'AI109','Delhi','Goa','2025-09-18',100,'domestic'),
(13,'AI110','Chennai','Hyderabad','2025-09-19',100,'domestic'),
(14,'AI201','Delhi','New York','2025-09-20',100,'international'),
(15,'AI202','Mumbai','London','2025-09-21',100,'international'),
(16,'AI203','Chennai','Singapore','2025-09-22',100,'international'),
(17,'AI204','Delhi','Dubai','2025-09-23',100,'international'),
(18,'AI205','Bangalore','Frankfurt','2025-09-24',100,'international'),
(19,'AI206','Kolkata','Bangkok','2025-09-25',100,'international'),
(20,'AI207','Hyderabad','Sydney','2025-09-26',100,'international'),
(21,'AI208','Mumbai','Toronto','2025-09-27',100,'international'),
(22,'AI209','Delhi','Paris','2025-09-28',100,'international'),
(23,'AI210','Chennai','Tokyo','2025-09-29',100,'international');

-- ------------------------------------------------------
-- Table structure for `tickets`
-- ------------------------------------------------------

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flight_number` varchar(20) NOT NULL,
  `passenger_name` varchar(100) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `age` int NOT NULL,
  `booking_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- ------------------------------------------------------
-- Table structure for `users`
-- ------------------------------------------------------

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);

-- Insert default admin user
INSERT INTO `users` VALUES (1,'admin','1234');
