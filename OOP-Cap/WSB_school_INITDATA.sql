/* ==========================================================
   REFRESH SAMPLE DATA  –  uruchamialne wielokrotnie
   ========================================================== */

SET FOREIGN_KEY_CHECKS = 0;

/* ---- 1. Czyścimy tabele w kolejności zależności ---- */
TRUNCATE TABLE grade;
TRUNCATE TABLE student_group_member;
TRUNCATE TABLE subject;
TRUNCATE TABLE student;
TRUNCATE TABLE student_group;
TRUNCATE TABLE employee_role;
TRUNCATE TABLE employee;
TRUNCATE TABLE person;
TRUNCATE TABLE institution;
TRUNCATE TABLE address;

SET FOREIGN_KEY_CHECKS = 1;

/* ---- 2. Wstawiamy dane testowe ---- */

/* address */
INSERT INTO address (address_id, street, building_no, unit_no, city,
                     postal_code, postal_num, country)
VALUES
  (1, 'Mazowiecka', '10', '5',  'Warszawa', '36-213', 35213, 'Poland');

/* institution */
INSERT INTO institution (institution_id, name, phone, email, website, address_id)
VALUES
  (1, 'Uniwersytet Testowy', '+481231231233', 'kontakt@utest.pl',
     'https://www.utest.pl', 1);

/* person – (1) pracownik-tutor, (2) student, (3) wykładowca */
INSERT INTO person (person_id, first_name, last_name, gender, pesel, birth_date,
                    email, iban, phone, institution_id, address_id)
VALUES
  (1, 'Anna',  'Kowalska',  'F', '90010112345', '1990-01-01',
      'anna.kowalska@utest.pl', 'PL61109010140000071219812874',
      '+48123456789', 1, 1),

  (2, 'Jan',   'Nowak',     'M', '01020345678', '2001-02-03',
      'jan.nowak@sample.pl',  NULL, NULL, NULL, 1),

  (3, 'Piotr', 'Zieliński', 'M', '80070778901', '1980-07-07',
      'piotr.zielinski@utest.pl', 'PL10105000997603123456789123',
      '+48777666555', 1, 1);

/* employee */
INSERT INTO employee (employee_id, hire_date, termination_date) VALUES
  (1, '2021-09-01', NULL),
  (3, '2010-10-01', '2020-09-30');

/* employee_role */
INSERT INTO employee_role (employee_id, role, start_date, end_date) VALUES
  (1, 'Tutor',    '2021-09-01', NULL),
  (3, 'Lecturer', '2010-10-01', '2020-09-30');

/* student_group – cohort_year spełnia triggery */
INSERT INTO student_group (group_id, name, cohort_year, tutor_id) VALUES
  (1, 'INF-22', 2022, 1);

/* student */
INSERT INTO student (student_id) VALUES (2);

/* student_group_member */
INSERT INTO student_group_member (student_id, group_id) VALUES (2, 1);

/* subject */
INSERT INTO subject (subject_id, name, description, lecturer_id) VALUES
  (1, 'Bazy Danych',
     'Teoria i praktyka relacyjnych baz danych.', 1),
  (2, 'Algorytmy',
     'Algorytmy i struktury danych.', 3);

/* grade */
INSERT INTO grade (student_id, subject_id, grade_date, grade_value,
                   comment, grade_type, weight)
VALUES
  (2, 1, CURDATE(), 4.5, 'Egzamin śródsemestralny', 'exam', 1.0);
