-- -----------------------------------------------------
-- Insert table user
-- -----------------------------------------------------
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
VALUES (1, 'Ivan', 'Ivanov', 46, 'ivan@email.com', '$2a$12$avXI/5Pu8ppdItkRe8bwJ.Fze8LnTDYtItnKBm0vrrrvo5VIIxCVu', 'M', 9379992);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
VALUES (2, 'Petr', 'Petrov', 34, 'petr@email.com', '$2a$12$VfZKT01Rhwn1Mjlgv.tEm.xijPyiE5YBazz/PcZmGVrmEQIsYGciC', 'M', 9565941);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
VALUES (3, 'Nikolay', 'Nikolaev', 34, 'nikolay@email.com', '$2a$12$UcDPhatxL6ewCu/Pm.bB8.sqGWhnJX3sHG18w947gRnJvwtXYkE4G', 'M', 9125941);
INSERT INTO user (user_id, first_name, last_name, age, gender, phone_num)
VALUES (4,'Marina', 'Mirikova', 36, 'F', 9735941);
INSERT INTO user (user_id, first_name, last_name, age, gender, phone_num)
VALUES (5,'Galina', 'Sidorova', 62, 'F', 9735941);
INSERT INTO user (user_id, first_name, last_name, age, email, password, gender, phone_num)
VALUES (6,'Svetlana', 'Svetlova', 29, 'svetlana@email.com', '$2a$12$MHhpZyVRbWmvoOjruOs3OemKpk4W3EVwyiqYNat0DeMyPp3EUsQEq', 'F', 9135941);

-- -----------------------------------------------------
-- Insert table role
-- -----------------------------------------------------
INSERT INTO role(role_id, name) VALUES (1, 'DOCTOR');
INSERT INTO role(role_id, name) VALUES (2, 'PATIENT');
INSERT INTO role(role_id, name) VALUES (3, 'ADMIN');

-- -----------------------------------------------------
-- Insert table user_role
-- -----------------------------------------------------
INSERT INTO user_role (user_id, role_id) VALUES ('1', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('1', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('1', '3');
INSERT INTO user_role (user_id, role_id) VALUES ('2', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('3', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('4', '2');
INSERT INTO user_role (user_id, role_id) VALUES ('5', '1');
INSERT INTO user_role (user_id, role_id) VALUES ('6', '2');

-- -----------------------------------------------------
-- Insert table timeslot
-- -----------------------------------------------------
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '308');
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('2', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '208');
INSERT INTO timeslot(timeslot_id, start_time, date, office) VALUES ('3', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '108');

-- -----------------------------------------------------
-- Insert table visit
-- -----------------------------------------------------
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('1', '2', '3', '1', 'the patient is recovering');
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('2', '5', '3', '2', 'the patient is healthy');
INSERT INTO visit (visit_id, doctor_id, patient_id, timeslot_id, comment)
VALUES ('3', '5', '6', '3', 'the patient is prescribed procedures');
INSERT INTO visit (visit_id, doctor_id)
VALUES ('4', '5');
INSERT INTO visit (visit_id)
VALUES ('5');

COMMIT;