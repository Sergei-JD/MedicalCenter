package com.itrex.java.lab.persistence.hibernateimpl;

import org.junit.jupiter.api.Test;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class HibernateVisitRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier("hibernateVisitRepositoryImpl")
    private VisitRepository visitRepository;
    @Autowired
    @Qualifier("hibernateUserRepositoryImpl")
    private UserRepository userRepository;

    @Test
    void getAllVisit_shouldReturnTheNumberOfAllVisits() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .doctor(newUser1)
                .patient(newUser2)
                .build();

        visitRepository.add(newVisit);

        //when && then
        assertEquals( 1, visitRepository.getAllVisits().size());
    }

    @Test
    void getVisitById_shouldReturnTheUserById() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .doctor(newUser1)
                .patient(newUser2)
                .build();

        visitRepository.add(newVisit);

        //when
        Optional<Visit> result = visitRepository.getVisitById(newVisit.getVisitId());

        //then
        result.ifPresent(visit -> assertAll(
                () -> assertEquals("Kurt", result.get().getDoctor().getFirstName()),
                () -> assertEquals("Kobe", result.get().getDoctor().getLastName()),
                () -> assertEquals("Naomi", result.get().getPatient().getFirstName()),
                () -> assertEquals("Jafris", result.get().getPatient().getLastName())
        ));
    }

    @Test
    void addVisit_notValidData_doctorNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .patient(newUser2)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(newVisit));
    }

    @Test
    void addVisit_notValidData_patientNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .doctor(newUser1)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(newVisit));
    }

    @Test
    void addVisit_ValidData_shouldAddVisit() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .doctor(newUser1)
                .patient(newUser2)
                .build();

        //when
        Visit result = visitRepository.add(newVisit);

        //then
        assertAll(
                () -> assertEquals("Kurt", result.getDoctor().getFirstName()),
                () -> assertEquals("Kobe", result.getDoctor().getLastName()),
                () -> assertEquals("Naomi", result.getPatient().getFirstName()),
                () -> assertEquals("Jafris", result.getPatient().getLastName())
        );
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
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();

        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Visit newVisit = Visit.builder()
                .doctor(newUser1)
                .patient(newUser2)
                .build();

        Visit savedVisit = visitRepository.add(newVisit);

        //when
        boolean result = visitRepository.deleteVisitById(savedVisit.getVisitId());

        //then
        assertTrue(result);
    }
}
