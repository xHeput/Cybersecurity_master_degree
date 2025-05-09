/* ──────────────────────────────────────────────────────────
   UNIVERSITY DATABASE  (DDL + walidacja + triggery)
   ────────────────────────────────────────────────────────── */

DROP DATABASE IF EXISTS university;

CREATE DATABASE university
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE university;

SET FOREIGN_KEY_CHECKS = 0;

/* ─────────────  DEFINICJE TABEL  ───────────── */

CREATE TABLE address (
    address_id     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    street         VARCHAR(100)  NOT NULL,
    building_no    VARCHAR(10)   NOT NULL,
    unit_no        VARCHAR(10),
    city           VARCHAR(100)  NOT NULL,
    postal_code    CHAR(6)       NOT NULL,
    postal_num     SMALLINT UNSIGNED NOT NULL,
    country        VARCHAR(50)   NOT NULL,

    CONSTRAINT chk_postal_code_format
        CHECK (postal_code REGEXP '^[0-9]{2}-[0-9]{3}$')
) ENGINE=InnoDB;

CREATE TABLE institution (
    institution_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    phone          VARCHAR(15)  NOT NULL,
    email          VARCHAR(100) NOT NULL,
    website        VARCHAR(255) NOT NULL,
    address_id     INT UNSIGNED NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address(address_id),

    CONSTRAINT chk_institution_phone
        CHECK (phone REGEXP '^[+0-9][0-9\\- ]{6,14}$'),
    CONSTRAINT chk_institution_email
        CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    CONSTRAINT chk_institution_website
        CHECK (website REGEXP '^(https?://)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(/.*)?$')
) ENGINE=InnoDB;

CREATE TABLE person (
    person_id      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    gender         ENUM('M','F') NOT NULL,
    pesel          CHAR(11)   NOT NULL UNIQUE,
    birth_date     DATE       NOT NULL,
    email          VARCHAR(100) NOT NULL,
    iban           CHAR(34) UNIQUE,
    phone          VARCHAR(15),
    institution_id INT UNSIGNED,
    address_id     INT UNSIGNED NOT NULL,
    FOREIGN KEY (institution_id) REFERENCES institution(institution_id),
    FOREIGN KEY (address_id)     REFERENCES address(address_id),

    CONSTRAINT chk_pesel_format
        CHECK (pesel REGEXP '^[0-9]{11}$'),
    CONSTRAINT chk_person_email
        CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    CONSTRAINT chk_person_phone
        CHECK (phone IS NULL OR phone REGEXP '^[+0-9][0-9\\- ]{6,14}$'),
    CONSTRAINT chk_person_iban
        CHECK (iban IS NULL OR iban REGEXP '^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$')
) ENGINE=InnoDB;

CREATE TABLE employee (
    employee_id      INT UNSIGNED PRIMARY KEY,   -- PK = FK do person
    hire_date        DATE NOT NULL,
    termination_date DATE,
    FOREIGN KEY (employee_id) REFERENCES person(person_id)
) ENGINE=InnoDB;

CREATE TABLE employee_role (
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    employee_id  INT UNSIGNED NOT NULL,
    role         ENUM('Rector','Dean','Tutor','Lecturer') NOT NULL,
    start_date   DATE NOT NULL,
    end_date     DATE,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB;

CREATE TABLE student_group (
    group_id     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) NOT NULL,
    cohort_year  SMALLINT    NOT NULL,
    tutor_id     INT UNSIGNED,
    FOREIGN KEY (tutor_id) REFERENCES employee(employee_id)
    -- UWAGA: brak CHECK (dynamiczna walidacja w triggerach poniżej)
) ENGINE=InnoDB;

CREATE TABLE student (
    student_id  INT UNSIGNED PRIMARY KEY,        -- PK = FK do person
    FOREIGN KEY (student_id) REFERENCES person(person_id)
) ENGINE=InnoDB;

CREATE TABLE student_group_member (
    student_id  INT UNSIGNED NOT NULL,
    group_id    INT UNSIGNED NOT NULL,
    PRIMARY KEY (student_id, group_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (group_id)   REFERENCES student_group(group_id)
) ENGINE=InnoDB;

CREATE TABLE subject (
    subject_id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    description  TEXT,
    lecturer_id  INT UNSIGNED,
    FOREIGN KEY (lecturer_id) REFERENCES employee(employee_id)
) ENGINE=InnoDB;

CREATE TABLE grade (
    grade_id     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    student_id   INT UNSIGNED NOT NULL,
    subject_id   INT UNSIGNED NOT NULL,
    grade_date   DATE NOT NULL,
    grade_value  DECIMAL(3,1) NOT NULL,
    comment      TEXT,
    grade_type   ENUM('final','exam','quiz','project','activity') NOT NULL,
    weight       DECIMAL(3,1),
    FOREIGN KEY (student_id)  REFERENCES student(student_id),
    FOREIGN KEY (subject_id)  REFERENCES subject(subject_id),

    CONSTRAINT chk_grade_value_range
        CHECK (grade_value BETWEEN 2.0 AND 5.0)
) ENGINE=InnoDB;

/* ─────────────  TRIGGERY  ───────────── */
DELIMITER $$

CREATE TRIGGER trg_check_cohort_year_ins
BEFORE INSERT ON student_group
FOR EACH ROW
BEGIN
  IF NEW.cohort_year < 2000 
     OR NEW.cohort_year > YEAR(CURDATE()) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'cohort_year musi być pomiędzy 2000 a rokiem bieżącym';
  END IF;
END$$

CREATE TRIGGER trg_check_cohort_year_upd
BEFORE UPDATE ON student_group
FOR EACH ROW
BEGIN
  IF NEW.cohort_year < 2000 
     OR NEW.cohort_year > YEAR(CURDATE()) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'cohort_year musi być pomiędzy 2000 a rokiem bieżącym';
  END IF;
END$$

DELIMITER ;

SET FOREIGN_KEY_CHECKS = 1;
