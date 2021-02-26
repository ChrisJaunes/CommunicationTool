USE Communication;
DROP TABLE IF EXISTS `talk_messages`;
CREATE TABLE `talk_messages` (
    `account1` varchar(40) NOT NULL,
    `account2` varchar(40) NOT NULL,
    `send_time` datetime NOT NULL,
    `content_type` int DEFAULT 0,
    `content` longtext DEFAULT NULL,
    PRIMARY KEY (`account1`, `account2`, `send_time`),
    FOREIGN KEY (`account1`) REFERENCES `account`(`account`),
    FOREIGN KEY (`account2`) REFERENCES `account`(`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;