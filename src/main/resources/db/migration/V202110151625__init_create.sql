-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user
(
    user_id    INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    age        INT          NOT NULL,
    email      VARCHAR(64),
    password   VARCHAR(256),
    gender     VARCHAR(5)   NOT NULL,
    phone_num  INT(12)
);

-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS role
(
    role_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

-- -----------------------------------------------------
-- Table user_role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- -----------------------------------------------------
-- Table timeslot
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS timeslot
(
    timeslot_id INT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    start_time  TIMESTAMP NOT NULL,
    date        TIMESTAMP NOT NULL,
    office      INT       NOT NULL,
    CONSTRAINT UNIQUE_TIMESLOT UNIQUE (start_time, date, office)
);

-- -----------------------------------------------------
-- Table visit
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS visit
(
    visit_id    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    doctor_id   INT,
    patient_id  INT,
    timeslot_id INT,
    comment     VARCHAR(255)
);

COMMIT;


