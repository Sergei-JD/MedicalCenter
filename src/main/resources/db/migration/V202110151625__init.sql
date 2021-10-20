-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age SMALLINT NOT NULL,
    email VARCHAR(64),
    password VARCHAR(8),
    gender VARCHAR(5) NOT NULL,
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
     FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
     FOREIGN KEY (role_id) REFERENCES role (role_id)
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
     FOREIGN KEY (doctor_id) REFERENCES user (user_id),
     FOREIGN KEY (patient_id) REFERENCES user (user_id),
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

INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
            VALUES (1, 'Ivan', 'Ivanov', 46, 'ivan@email.com', '12345678', 'M', 9379992);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
            VALUES (2, 'Petr', 'Petrov', 34, 'petr@email.com', '23456781', 'M', 9565941);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
            VALUES (3, 'Nikolay', 'Nikolaev', 34, 'nikolay@email.com', '28726781', 'M', 9125941);
INSERT INTO user (user_id, first_name, last_name, age, gender, phone_num)
            VALUES (4,'Marina', 'Mirikova', 36, 'F', 9735941);
INSERT INTO user (user_id, first_name, last_name, age, gender, phone_num)
            VALUES (5,'Galina', 'Sidorova', 62, 'F', 9735941);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
            VALUES (6,'Svetlana', 'Svetlova', 29, 'svetlana@email.com', '32726781', 'F', 9135941);

INSERT INTO role(role_id, name) VALUES (1, 'Doctor');
INSERT INTO role(role_id, name) VALUES (2, 'Patient');

INSERT INTO user_role (user_id, role_id) VALUES ('1', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('1', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('2', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('3', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('4', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('5', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('6', '2');

COMMIT;


