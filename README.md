# MedicalCenter
##### This application of the medical center is designed to present the possibilities of online interaction of patients with the clinic through a web service, resulting in a balanced work schedule of doctors regulating the work of the medical institution, remote access to patient data, remote registration of patients for an appointment with a certain doctor, etc.
---

## Features
##### Full functionality of this application:
- _Add_: doctor, patient, timeslot and visit;
- _Delete_: doctor, patient, timeslot and visit;
- _Update_: doctor, patient, timeslot, visit and separate history in the visit;
- _Get all_: roles, doctors, patients, timeslots, visits, free visits;
- _Get by id_: doctor, patient, timelot, visit, free visits to the doctor, all patient visits;
- _Get by email_: doctor, patient;

##### All logic is delimited by the appropriate permissions to perform certain actions by roles:
- _The administrator_  has the opportunity to use the full functionality of this application: managing the full cycle from creating to deleting visits, doctors, patients and managing roles.
- _The doctor_ do not have the following options: create, delete and change doctors.
- _The patient_ have the following options: get all doctors, get all timeslots, get a timeslot by id, get free visits.
---

## Tech & Tools
used technologies & tools and necessary topics
- Building:
    - gradle
- SQL + Flyway:
    - setup/config + SQL Joins
- JPA/ORM (Hibernate):
    - JDBC
    - Hibernate entity states
    - Transaction management
    - Hibernate cache levels
    - Spring Data (Pageble, sort, Specifications)
- Spring Core (IoC, DI, Aspect):
    - Definition of patterns IoC & DI
    - Context, Initialization, Scopes of beans, BeanPostProcessors
    - Autowiring + Code generation(Lombok)
    - AspectJ vs CGLIB
    - Args/Properties/Profiles
- Spring Boot (MVC, Security, Data):
    - Spring vs. Spring Boot
    - Starter and Conditions
    - Spring MVC
    - Spring security
---

## Launch
##### To run this application, you need to run Spring Boot.