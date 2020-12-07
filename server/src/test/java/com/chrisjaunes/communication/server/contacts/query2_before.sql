use communication;
LOCK TABLES `account` WRITE;
DELETE FROM`account` WHERE account='aaa' or account = 'bbb' or account = 'ccc' or account = 'ddd' or account = 'eee' ;
INSERT INTO `account` VALUES
('aaa','aaa','lwx', NULL, NULL),
('bbb','bbb','bbb', NULL, NULL),
('ccc','ccc','ccc', NULL, NULL),
('ddd','ddd','ddd', NULL, NULL),
('eee','eee','eee', NULL, NULL);
UNLOCK TABLES;