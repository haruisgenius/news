CREATE TABLE IF NOT EXISTS `news`.`news` (
  `news_number` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `release_date` DATE NOT NULL,
  `news_group` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `content` VARCHAR(16000) NOT NULL,
  `is_open` TINYINT NOT NULL,
  `update_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`news_number`));
  
  CREATE TABLE IF NOT EXISTS `news`.`news_group` (
  `group_name` VARCHAR(45) NOT NULL,
  `news_amount` INT NULL,
  PRIMARY KEY (`group_name`));
  
  CREATE TABLE IF NOT EXISTS `news`.`category` (
  `category_name` VARCHAR(45) NOT NULL,
  `category_group` VARCHAR(45) NOT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`category`));