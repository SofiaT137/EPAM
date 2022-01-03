USE `elective1`;

CREATE TABLE IF NOT EXISTS `elective1`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `elective1`.`account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(65) NOT NULL,
  `is_active` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`account_id`),
  INDEX `fk_account_role1_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  CONSTRAINT `fk_account_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `elective1`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `elective1`.`university_group` (
  `university_group_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`university_group_id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `elective1`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `university_group_id` INT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_account1_idx` (`account_id` ASC) VISIBLE,
  INDEX `fk_user_university_group1_idx` (`university_group_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `elective1`.`account` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_university_group1`
    FOREIGN KEY (`university_group_id`)
    REFERENCES `elective1`.`university_group` (`university_group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `elective1`.`course` (
  `course_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`course_id`))
ENGINE = InnoDB
ROW_FORMAT = COMPRESSED;


CREATE TABLE IF NOT EXISTS `elective1`.`user_has_course` (
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  INDEX `fk_user_has_course_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_user_has_course_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_course_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `elective1`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_course_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `elective1`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `elective1`.`review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `grade` TINYINT(10) NOT NULL,
  `review` TEXT(300) NULL,
  PRIMARY KEY (`review_id`),
  INDEX `fk_review_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_review_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `elective1`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_review_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `elective1`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

