-- -----------------------------------------------------
-- Insert table user
-- -----------------------------------------------------
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

-- -----------------------------------------------------
-- Insert table role
-- -----------------------------------------------------
INSERT INTO role(role_id, name) VALUES (1, 'Doctor');
INSERT INTO role(role_id, name) VALUES (2, 'Patient');

-- -----------------------------------------------------
-- Insert table user_role
-- -----------------------------------------------------
INSERT INTO user_role (user_id, role_id) VALUES ('1', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('1', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('2', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('3', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('4', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('5', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('6', '2');

-- -----------------------------------------------------
-- Insert table timeslot
-- -----------------------------------------------------
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('1', '12:20', '2021-10-10', '308');
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('2', '13:40', '2021-10-11', '208');
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('3', '14:00', '2021-10-12', '108');

-- -----------------------------------------------------
-- Insert table visit
-- -----------------------------------------------------
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('1', '2', '3', '1', 'the patient is recovering');
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('2', '5', '3', '2', 'the patient is healthy');
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('3', '5', '6', '3', 'the patient is prescribed procedures');

COMMIT;