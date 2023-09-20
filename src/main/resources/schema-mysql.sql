CREATE TABLE IF NOT EXISTS `news`.`news` (
  `news_number` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `sub_title` VARCHAR(45) NOT NULL,
  `release_date` DATE NOT NULL,
  `news_group` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `content` VARCHAR(16000) NOT NULL,
  `is_open` TINYINT NOT NULL,
  `update_time` TIMESTAMP NOT NULL,
  `is_news_delete` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`news_number`));
  
  CREATE TABLE IF NOT EXISTS `news`.`news_group` (
   `group_number` int NOT NULL AUTO_INCREMENT,
   `group_name` varchar(45) NOT NULL,
   `news_amount` int NOT NULL DEFAULT '0',
   `is_group_delete` TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (`group_number`));


   CREATE TABLE IF NOT EXISTS `news`.`category` (
   `category_number` int NOT NULL AUTO_INCREMENT,
   `category_name` varchar(45) NOT NULL,
   `category_group` varchar(45) NOT NULL,
   `amount` int NOT NULL DEFAULT '0',
   `is_category_delete` TINYINT NOT NULL DEFAULT 0,
   PRIMARY KEY (`category_number`));