package com.itrex.java.lab.service.services;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.persistence.entity.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.hibernate.VisitServiceImpl;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.VisitRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class VisitServiceImplTest {

    private final static Integer TEST_VISIT_DOCTOR_ID = 1;
    private final static Integer TEST_VISIT_PATIENT_ID = 2;

    private final static String TEST_USER_FIRST_NAME = "test name";
    private final static String TEST_USER_LAST_NAME = "test last name";
    private final static Integer TEST_USER_AGE = 25;
    private final static String TEST_USER_EMAIL = "test@email.test";
    private final static String TEST_USER_PASSWORD = "password";
    private final static String TEST_USER_GENDER = "test gender";
    private final static Integer TEST_USER_NUMBER_PHONE = 34546674;

    private final static Instant TEST_START_TIME = Instant.parse("2021-04-09T15:30:45.123Z");
    private final static Instant TEST_DATE = Instant.parse("2021-04-09T15:30:45.123Z");
    private final static Integer TEST_OFFICE = 505;

    @InjectMocks
    private VisitServiceImpl visitService;
    @Mock
    private VisitRepository visitRepository;

    @Test
    void createVisit_validData_shouldCreateVisit() {
        //given
        VisitDTO visitDTO = VisitDTO.builder()
                .doctorId(User.builder()
                        .firstName(TEST_USER_FIRST_NAME)
                        .lastName(TEST_USER_LAST_NAME)
                        .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
                .patientId(User.builder()
                        .firstName(TEST_USER_FIRST_NAME)
                        .lastName(TEST_USER_LAST_NAME)
                        .roles(Set.of(Role.builder().name(RoleType.PATIENT).build())).build())
                .timeslotId(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE)
                        .build())
                .build();
        Visit visit = initVisit(1);

        //when
        when(visitRepository.add(visit)).thenReturn(visit);
        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);

        //then
        assertAll(
                () -> assertEquals(visit.getDoctor().getFirstName(), actualVisitDTO.getDoctorId().getFirstName()),
                () -> assertEquals(visit.getDoctor().getLastName(), actualVisitDTO.getDoctorId().getLastName()),
                () -> assertEquals(visit.getPatient().getFirstName(), actualVisitDTO.getPatientId().getFirstName()),
                () -> assertEquals(visit.getPatient().getLastName(), actualVisitDTO.getPatientId().getLastName()),
                () -> assertEquals(visit.getTimeslot().getStartTime(), actualVisitDTO.getTimeslotId().getStartTime()),
                () -> assertEquals(visit.getTimeslot().getDate(), actualVisitDTO.getTimeslotId().getDate()),
                () -> assertEquals(visit.getTimeslot().getOffice(), actualVisitDTO.getTimeslotId().getOffice())
        );
    }

    @Test
    void createVisit_repositoryThrowError_shouldThrowServiceException() {
        //given
        VisitDTO visitDTO = VisitDTO.builder().build();

        //when
        when(visitService.createVisit(visitDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(ServiceException.class, () -> visitService.createVisit(visitDTO));
    }

    @Test
    void getAllVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 2;
        Visit visit1 = initVisit(1);
        Visit visit2 = initVisit(2);

        when(visitRepository.getAllVisits())
                .thenReturn(Arrays.asList(visit1, visit2));

        //when
        List<VisitViewDTO> result = visitService.getAllVisit();

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(TEST_VISIT_DOCTOR_ID)
                && visit.getDoctor().getFirstName().equals(TEST_USER_FIRST_NAME)
                && visit.getDoctor().getLastName().equals(TEST_USER_LAST_NAME)
                && visit.getTimeslot().getOffice().equals(TEST_OFFICE)));
    }

    @Test
    void getAllVisits_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.getAllVisits()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.getAllVisit());
    }

    @Test
    void getAllFreeVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit visit1 = initVisit(1);
        Visit visit2 = Visit.builder()
                .visitId(2)
                .timeslot(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE).build())
                .build();

        when(visitRepository.getAllVisits())
                .thenReturn(Arrays.asList(visit1, visit2));

        //when
        List<VisitViewDTO> result = visitService.getAllFreeVisits();

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor() == null
                && visit.getPatient() == null));
    }

    @Test
    void getAllFreeVisits_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.getAllVisits()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.getAllFreeVisits());
    }

    @Test
    void getAllFreeVisitsForDoctorId_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit visit1 = initVisit(1);
        Visit visit2 = Visit.builder()
                .visitId(2)
                .doctor(User.builder()
                        .userId(TEST_VISIT_DOCTOR_ID)
                        .firstName(TEST_USER_FIRST_NAME)
                        .lastName(TEST_USER_LAST_NAME)
                        .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
                .timeslot(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE).build())
                .build();

        when(visitRepository.getAllVisits())
                .thenReturn(Arrays.asList(visit1, visit2));

        //when
        List<VisitViewDTO> result = visitService.getAllFreeVisitsForDoctorById(TEST_VISIT_DOCTOR_ID);

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor() != null
                && visit.getDoctor().getUserId().equals(TEST_VISIT_DOCTOR_ID)));
    }

    @Test
    void getAllFreeVisitsForDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.getAllVisits()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.getAllFreeVisitsForDoctorById(1));
    }

    @Test
    void getAllVisitsForPatientById_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit visit1 = initVisit(1);
        Visit visit2 = Visit.builder()
                .visitId(2)
                .timeslot(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE).build())
                .build();

        when(visitRepository.getAllVisits())
                .thenReturn(Arrays.asList(visit1, visit2));

        //when
        List<VisitViewDTO> result = visitService.getAllVisitsForPatientDyId(TEST_VISIT_PATIENT_ID);

        //then
        assertEquals(expectedListSize, result.size());
    }

    @Test
    void getAllVisitsForPatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.getAllVisits()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.getAllVisitsForPatientDyId(1));
    }

    @Test
    void deleteVisitById_existVisit_shouldDeleteVisit() {
        //given
        when(visitRepository.deleteVisitById(1)).thenReturn(true);
        when(visitRepository.deleteVisitById(not(eq(1)))).thenReturn(false);

        //when
        boolean result = visitService.deleteVisit(1);

        //then
        assertTrue(result);
        verify(visitRepository, times(1)).deleteVisitById(eq(1));
    }

    @Test
    void deleteVisitById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.deleteVisitById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.deleteVisit(1));
    }

    @Test
    void getVisitById_validData_shouldReturnTheVisitById() {
        //given
        Visit addedVisit = initVisit(1);

        when(visitRepository.getVisitById(1))
                .thenReturn(Optional.of(addedVisit));

        //when
        Optional<VisitViewDTO> result = visitService.getVisitById(1);

        //then
        verify(visitRepository, times(1)).getVisitById(eq(1));
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getFirstName().equals(TEST_USER_FIRST_NAME)
                && visit.getDoctor().getLastName().equals(TEST_USER_LAST_NAME)
                && visit.getTimeslot().getStartTime().equals(TEST_START_TIME)
                && visit.getTimeslot().getDate().equals(TEST_DATE)
                && visit.getTimeslot().getOffice().equals(TEST_OFFICE)
        ));
    }

    @Test
    void getVisitById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.getVisitById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.getVisitById(1)) ;
    }

    @Test
    void updateVisitById_repositoryThrowError_shouldThrowServiceException() {
        //given
        VisitDTO visitDTO = VisitDTO.builder().build();
        when(visitRepository.getVisitById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.updateVisit(visitDTO)) ;
    }

    @Test
    void updateVisitHistory_repositoryThrowError_shouldThrowServiceException() {
        //given
        VisitDTO visitDTO = VisitDTO.builder().build();
        when(visitRepository.getVisitById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.updateVisitHistory(visitDTO)) ;
    }

    private Visit initVisit(Integer id) {
    return Visit.builder()
        .visitId(id)
        .doctor(User.builder()
                .userId(TEST_VISIT_DOCTOR_ID)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
        .patient(User.builder()
                .userId(TEST_VISIT_PATIENT_ID)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build())).build())
        .timeslot(Timeslot.builder()
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE).build())
        .build();
    }
}
