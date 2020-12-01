USE Communication;
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `group`;
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
    `group` int AUTO_INCREMENT,
    `nickname` varchar(40) NOT NULL,
    `avatar` longtext,
    PRIMARY KEY (`group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `group_member`;
DROP TABLE IF EXISTS `group_member`;
CREATE TABLE `group_member` (
    `group` int NOT NULL,
    `account` varchar(40) NOT NULL,
    `operation` int DEFAULT 0,
    PRIMARY KEY (`group`, `account`),
    FOREIGN KEY (`group`) REFERENCES `group`(`group`),
    FOREIGN KEY (`account`) REFERENCES `account`(`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `group_messages`;
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_messages` (
    `group` int NOT NULL,
    `account` varchar(40) NOT NULL,
    `send_time` datetime NOT NULL,
    `content_type` int DEFAULT 0,
    `content` longtext DEFAULT NULL,
    PRIMARY KEY (`group`, `account`, `send_time`),
    FOREIGN KEY (`group`) REFERENCES `group`(`group`),
    FOREIGN KEY (`account`) REFERENCES `account`(`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
SET foreign_key_checks = 1;