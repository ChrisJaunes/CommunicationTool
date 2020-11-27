USE Communication;
DROP TABLE IF EXISTS `friendship`;
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
    `account1` varchar(40) NOT NULL,
    `account2` varchar(40) NOT NULL,
    `time` datetime NOT NULL,
    `operation` int DEFAULT (0),
    PRIMARY KEY (`account1`, `account2`),
    FOREIGN KEY (`account1`) REFERENCES `account`(`account`),
    FOREIGN KEY (`account2`) REFERENCES `account`(`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
