-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age SMALLINT NOT NULL,
    email VARCHAR(64),
    gender CHAR NOT NULL,
    phone_num INT(12)
);

-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS role (
    role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- -----------------------------------------------------
-- Table user_role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role (
     user_id INT NOT NULL,
     role_id INT NOT NULL,
     PRIMARY KEY (user_id, role_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id),
     FOREIGN KEY (role_id) REFERENCES role(role_id)
);

-- -----------------------------------------------------
-- Table visit
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS visit (
     visit_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     doctor_id INT NOT NULL,
     patient_id INT NOT NULL,
     timeslot_id INT NOT NULL,
     comment VARCHAR(255),
     FOREIGN KEY (doctor_id) REFERENCES (doctor_id),
     FOREIGN KEY (patient_id) REFERENCES (patient_id),
     FOREIGN KEY (timeslot_id) REFERENCES (timeslot_id)
);

-- -----------------------------------------------------
-- Table timeslot
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS timeslot (
    timeslot_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start_time TIME NOT NULL,
    date DATE NOT NULL,
    office INT NOT NULL,
    CONSTRAINT UNIQUE_TIMESLOT UNIQUE (start_time, date, office)
);

INSERT INTO user(first_name, last_name, age, email, gender, phone_num)
            VALUES ('Ivan', 'Ivanov', 46, 'ivan@email.com', 'M', 9379992);
INSERT INTO user(first_name, last_name, age, email, gender, phone_num)
            VALUES ('Petr', 'Petrov', 34, 'petr@email.com', 'M', 9565941);

INSERT INTO role(name) VALUES ('Doctor');
INSERT INTO role(name) VALUES ('Patient');


COMMIT;


