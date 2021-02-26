use Communication;
LOCK TABLES `test` WRITE;
INSERT INTO `test` VALUES
('111','111'),
('222','222'),
('333','333'),
('444','444'),
('555','555');
UNLOCK TABLES;

LOCK TABLES `account` WRITE;
INSERT INTO `account` VALUES
('111','111','111', NULL, NULL),
('222','222','222', NULL, NULL),
('333','333','333', NULL, NULL),
('444','444','444', NULL, NULL),
('555','555','555', NULL, NULL);
UNLOCK TABLES;

LOCK TABLES `contacts` WRITE;
INSERT INTO `contacts` VALUES
('111','222','2020-11-17 00:00:00', 1),
('111','333','2020-11-17 00:00:00', 1),
('111','444','2020-11-17 00:00:00', 2),
('111','555','2020-11-17 00:00:00', 2),
('222','333','2020-11-17 00:00:00', 1),
('222','444','2020-11-17 00:00:00', 2);
UNLOCK TABLES;

LOCK TABLES `talk_messages` WRITE;
INSERT INTO `talk_messages` VALUES
('111','222','2020-11-17 00:00:00', 1, 'sql-test1'),
('111','333','2020-11-17 00:00:00', 1, 'sql-test2'),
('111','444','2020-11-17 00:00:00', 1, 'sql-test3'),
('111','555','2020-11-17 00:00:00', 1, 'sql-test4'),
('222','333','2020-11-17 00:00:00', 1, 'sql-test5'),
('111','222','2020-11-17 00:00:01', 1, 'sql-test6'),
('111','222','2020-11-17 00:00:02', 1, 'sql-test7'),
('111','222','2020-11-17 00:00:03', 1, 'sql-test8'),
('111','222','2020-11-17 00:00:04', 1, 'sql-test9'),
('222','111','2020-11-17 00:00:05', 1, 'sql-testA');
UNLOCK TABLES;

LOCK TABLES `group` WRITE;
INSERT INTO `group` (`group_name`, `group_avatar`)VALUES
('222',null),
('222',null),
('222',null),
('222',null),
('333',null),
('333',null),
('333',null),
('444',null),
('444',null),
('555',null);
UNLOCK TABLES;

LOCK TABLES `group_member` WRITE;
INSERT INTO `group_member` VALUES
(1,'111',0),
(1,'222',0),
(1,'333',0),
(1,'444',0),
(1,'555',0),
(2,'333',0),
(2,'444',0),
(3,'111',0);
UNLOCK TABLES;

LOCK TABLES `group_messages` WRITE;
INSERT INTO `group_messages` VALUES
(1,'222','2020-11-17 00:00:00', 1, 'sql-test1'),
(1,'333','2020-11-17 00:00:00', 1, 'sql-test2'),
(1,'444','2020-11-17 00:00:00', 1, 'sql-test3'),
(1,'555','2020-11-17 00:00:00', 1, 'sql-test4'),
(1,'333','2020-11-17 00:00:01', 1, 'sql-test5'),
(1,'222','2020-11-17 00:00:02', 1, 'sql-test6'),
(1,'222','2020-11-17 00:00:03', 1, 'sql-test7'),
(1,'222','2020-11-17 00:00:04', 1, 'sql-test8'),
(1,'222','2020-11-17 00:00:05', 1, 'sql-test9'),
(1,'111','2020-11-17 00:00:06', 1, 'sql-testA'),
(2,'111','2020-11-17 00:00:07', 1, 'sql-testB'),
(2,'111','2020-11-17 00:00:08', 1, 'sql-testC'),
(3,'111','2020-11-17 00:00:09', 1, 'sql-testD'),
(3,'111','2020-11-17 00:00:10', 1, 'sql-testE'),
(3,'111','2020-11-17 00:00:11', 1, 'sql-testF');
UNLOCK TABLES;