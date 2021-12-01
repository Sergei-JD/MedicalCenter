package com.itrex.java.lab.persistence.dataimpl;

import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class HibernateVisitRepositoryImplTest extends BaseRepositoryTest {

    private static final int USER_AGE = "USER_AGE".hashCode();

    @Autowired
    @Qualifier("hibernateVisitRepositoryImpl")
    private VisitRepository visitRepository;

    @Autowired
    @Qualifier("hibernateUserRepositoryImpl")
    private UserRepository userRepository;

    @Test
    void getAllVisit_shouldReturnTheNumberOfAllVisits() throws RepositoryException {
        //given
        int expectedVisitListSize = 1;
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .doctor(firstUser)
                .patient(secondUser)
                .build();

        visitRepository.add(addVisit);

        //when && then
        assertEquals(expectedVisitListSize, visitRepository.getAllVisits().size());
    }

    @Test
    void getVisitById_shouldReturnTheUserById() throws RepositoryException {
        //given
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .doctor(firstUser)
                .patient(secondUser)
                .build();

        visitRepository.add(addVisit);

        //when
        Optional<Visit> result = visitRepository.getVisitById(addVisit.getVisitId());

        //then
        result.ifPresent(visit -> assertAll(
                () -> assertEquals(firstUser.getFirstName(), result.get().getDoctor().getFirstName()),
                () -> assertEquals(firstUser.getLastName(), result.get().getDoctor().getLastName()),
                () -> assertEquals(secondUser.getFirstName(), result.get().getPatient().getFirstName()),
                () -> assertEquals(secondUser.getLastName(), result.get().getPatient().getLastName())
        ));
    }

    @Test
    void addVisit_notValidData_doctorNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .patient(secondUser)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(addVisit));
    }

    @Test
    void addVisit_notValidData_patientNull_shouldThrowRepositoryException() throws RepositoryException {
        //given
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .doctor(firstUser)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(addVisit));
    }

    @Test
    void addVisit_ValidData_shouldAddVisit() throws RepositoryException {
        //given
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .doctor(firstUser)
                .patient(secondUser)
                .build();

        //when
        Visit result = visitRepository.add(addVisit);

        //then
        assertAll(
                () -> assertEquals(firstUser.getFirstName(), result.getDoctor().getFirstName()),
                () -> assertEquals(firstUser.getLastName(), result.getDoctor().getLastName()),
                () -> assertEquals(secondUser.getFirstName(), result.getPatient().getFirstName()),
                () -> assertEquals(secondUser.getLastName(), result.getPatient().getLastName())
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
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Visit addVisit = Visit.builder()
                .doctor(firstUser)
                .patient(secondUser)
                .build();

        Visit savedVisit = visitRepository.add(addVisit);

        //when
        boolean result = visitRepository.deleteVisitById(savedVisit.getVisitId());

        //then
        assertTrue(result);
    }

    private User createRandomUser() {
        return User.builder()
                .firstName(RandomStringUtils.random(4))
                .lastName(RandomStringUtils.random(4))
                .age(USER_AGE)
                .gender("M")
                .build();
    }

}
