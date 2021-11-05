package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.repository.config.TestRepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
class HibernateVisitRepositoryImplTest extends BaseRepositoryTest {

    @Qualifier("hibernateVisitRepositoryImpl")
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllVisit_shouldReturnTheNumberOfAllVisits() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setDoctor(newUser1);
        newVisit.setPatient(newUser2);

        visitRepository.add(newVisit);

        //when && then
        assertEquals(visitRepository.getAllVisits().size(), 1);
    }

    @Test
    void getVisitById_shouldReturnTheUserById() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setDoctor(newUser1);
        newVisit.setPatient(newUser2);

        visitRepository.add(newVisit);

        //when
        Optional<Visit> result = visitRepository.getVisitById(newVisit.getVisitId());

        //then
        assertAll(
                () -> assertEquals("Kurt", result.get().getDoctor().getFirstName()),
                () -> assertEquals("Kobe", result.get().getDoctor().getLastName()),
                () -> assertEquals("Naomi", result.get().getPatient().getFirstName()),
                () -> assertEquals("Jafris", result.get().getPatient().getLastName())
        );
    }

    @Test
    void addVisit_notValidData_doctorNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setPatient(newUser2);

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(newVisit));
    }

    @Test
    void addVisit_notValidData_patientNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setDoctor(newUser1);

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(newVisit));
    }

    @Test
    void addVisit_ValidData_shouldAddVisit() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setDoctor(newUser1);
        newVisit.setPatient(newUser2);

        //when
        Visit result = visitRepository.add(newVisit);

        //then
        assertAll(
                () -> assertEquals("Kurt", result.getDoctor().getFirstName()),
                () -> assertEquals("Kobe", result.getDoctor().getLastName()),
                () -> assertEquals("Naomi", result.getPatient().getFirstName()),
                () -> assertEquals("Jafris", result.getPatient().getLastName())
        );
//        assertEquals(newVisit, result);
    }

    @Test
    void deleteVisitById_notExistVisit_shouldNotDeleteVisit() throws RepositoryException {
        //given
        int visitId = -1;
        List<Visit> allVisitsBeforeDelete = visitRepository.getAllVisits();
        assertTrue(allVisitsBeforeDelete.stream().noneMatch(visit -> visit.getVisitId().equals(visitId)));

        //when
        boolean result = visitRepository.deleteVisitById(visitId);

        //then
        assertFalse(result);
        List<Visit> allVisitsAfterDelete = visitRepository.getAllVisits();
        assertTrue(allVisitsBeforeDelete.size() == allVisitsAfterDelete.size()
                && allVisitsBeforeDelete.containsAll(allVisitsAfterDelete));
    }

    @Test
    void deleteVisitById_existVisit_shouldDeleteVisit() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        User doctor = userRepository.add(newUser1);
        User patient = userRepository.add(newUser2);

        Visit newVisit = new Visit();
        newVisit.setDoctor(doctor);
        newVisit.setPatient(patient);
        Visit savedVisit = visitRepository.add(newVisit);

        //when
        boolean result = visitRepository.deleteVisitById(savedVisit.getVisitId());

        //then
        assertTrue(result);
    }

}