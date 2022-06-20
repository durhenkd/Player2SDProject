-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema player2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema player2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `player2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `player2` ;

-- -----------------------------------------------------
-- Table `player2`.`p2_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `telephone` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_rt9vwbx8ct6uxdchumctxqtgq` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UK_rd5xv4yki3q0od4246pghwsur` (`telephone` ASC) VISIBLE,
  UNIQUE INDEX `UK_h1wgj38sh8i9c6sgujkb2fjos` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_k3qkrslob4vhrf8bghdb0h4ll` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_clique`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_clique` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `credentials_id` BIGINT NULL DEFAULT NULL,
  `category_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_lu8ljefm7xbq0y5nfq9teekgp` (`name` ASC) VISIBLE,
  INDEX `FK8h2pcgur3ptlb2sim2ocvk5yb` (`credentials_id` ASC) VISIBLE,
  INDEX `FK5a1xbx9cgwhcylh0hxodi42oy` (`category_id` ASC) VISIBLE,
  CONSTRAINT `FK5a1xbx9cgwhcylh0hxodi42oy`
    FOREIGN KEY (`category_id`)
    REFERENCES `player2`.`p2_category` (`id`),
  CONSTRAINT `FK8h2pcgur3ptlb2sim2ocvk5yb`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `player2`.`p2_account` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content_path` VARCHAR(255) NOT NULL,
  `datetime` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_clique_posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_clique_posts` (
  `p2_clique_id` BIGINT NOT NULL,
  `posts_id` BIGINT NOT NULL,
  UNIQUE INDEX `UK_c19ukov4y36uo0k81myelykje` (`posts_id` ASC) VISIBLE,
  INDEX `FKk2nqj90m8x0woif1rehj4596c` (`p2_clique_id` ASC) VISIBLE,
  CONSTRAINT `FKev9ojx4mn0kaspfvds0lldpgw`
    FOREIGN KEY (`posts_id`)
    REFERENCES `player2`.`p2_post` (`id`),
  CONSTRAINT `FKk2nqj90m8x0woif1rehj4596c`
    FOREIGN KEY (`p2_clique_id`)
    REFERENCES `player2`.`p2_clique` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_player` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `bio` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NOT NULL,
  `gender` INT NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `pic_path` VARCHAR(255) NOT NULL,
  `credentials_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK5l4sewhmnb4j7wc2v8lhdx817` (`credentials_id` ASC) VISIBLE,
  CONSTRAINT `FK5l4sewhmnb4j7wc2v8lhdx817`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `player2`.`p2_account` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_match`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_match` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `accepted1` INT NOT NULL,
  `accepted2` INT NOT NULL,
  `post_it1` VARCHAR(255) NULL DEFAULT NULL,
  `post_it2` VARCHAR(255) NULL DEFAULT NULL,
  `player1_id` BIGINT NULL DEFAULT NULL,
  `player2_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK1elbnbf0p9q3yg35wdohpy0ll` (`player1_id` ASC) VISIBLE,
  INDEX `FK1cl9cvydubs8d63t6cgon2b3h` (`player2_id` ASC) VISIBLE,
  CONSTRAINT `FK1cl9cvydubs8d63t6cgon2b3h`
    FOREIGN KEY (`player2_id`)
    REFERENCES `player2`.`p2_player` (`id`),
  CONSTRAINT `FK1elbnbf0p9q3yg35wdohpy0ll`
    FOREIGN KEY (`player1_id`)
    REFERENCES `player2`.`p2_player` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `player2`.`p2_player_follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player2`.`p2_player_follows` (
  `p2_player_id` BIGINT NOT NULL,
  `follows_id` BIGINT NOT NULL,
  INDEX `FKfw3afki7kjcbxfg5cntao30jr` (`follows_id` ASC) VISIBLE,
  INDEX `FK8sx4twnstv355cruwgny10urp` (`p2_player_id` ASC) VISIBLE,
  CONSTRAINT `FK8sx4twnstv355cruwgny10urp`
    FOREIGN KEY (`p2_player_id`)
    REFERENCES `player2`.`p2_player` (`id`),
  CONSTRAINT `FKfw3afki7kjcbxfg5cntao30jr`
    FOREIGN KEY (`follows_id`)
    REFERENCES `player2`.`p2_clique` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
