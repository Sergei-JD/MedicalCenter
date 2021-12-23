package com.itrex.java.lab.service.dataimpl;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import com.itrex.java.lab.dto.CreateVisitDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoSettings;
import com.itrex.java.lab.persistence.entity.Role;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.dataimpl.VisitRepository;
import com.itrex.java.lab.persistence.dataimpl.TimeslotRepository;

import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class VisitServiceImplTest {

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

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TimeslotRepository timeslotRepository;

    @Test
    void getAllVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 2;
        Visit firstVisit = initVisit(1);
        Visit secondVisit = initVisit(2);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("visitId").descending());

        when(visitRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(firstVisit, secondVisit)));

        //when
        Page<VisitViewDTO> result = visitService.getAllVisit(pageable);

        //then
        assertEquals(expectedListSize, result.getSize());
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(TEST_VISIT_DOCTOR_ID)
                && visit.getDoctor().getFirstName().equals(TEST_USER_FIRST_NAME)
                && visit.getDoctor().getLastName().equals(TEST_USER_LAST_NAME)
                && visit.getTimeslot().getOffice().equals(TEST_OFFICE)));
    }

    @Test
    void getAllVisits_repositoryThrowError_shouldThrowServiceException() {
        //given
        Pageable pageable = PageRequest.of(1, 2, Sort.by("visitId").descending());
        when(visitRepository.findAll(pageable)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> visitService.getAllVisit(pageable));
    }

    @Test
    void getAllFreeVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit firstVisit = initVisit(1);
        Visit secondVisit = Visit.builder()
                .visitId(2)
                .timeslot(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE).build())
                .build();

        Pageable pageable = PageRequest.of(1, 2, Sort.by("visitId").descending());

        when(visitRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(firstVisit, secondVisit)));

        //when
        Page<VisitViewDTO> result = visitService.getAllFreeVisits(pageable);

        //then
        assertEquals(expectedListSize, result.getSize());
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor() == null
                && visit.getPatient() == null));
    }

    @Test
    void getAllFreeVisits_repositoryThrowError_shouldThrowServiceException() {
        //given
        Pageable pageable = PageRequest.of(1, 2, Sort.by("visitId").descending());
        when(visitRepository.findAll(pageable)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> visitService.getAllFreeVisits(pageable));
    }

    @Test
    void getAllFreeVisitsForDoctorId_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit firstVisit = initVisit(1);
        Visit secondVisit = Visit.builder()
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

        when(visitRepository.findAll())
                .thenReturn(Arrays.asList(firstVisit, secondVisit));

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
        when(visitRepository.findAll()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> visitService.getAllFreeVisitsForDoctorById(1));
    }

    @Test
    void getAllVisitsForPatientById_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 1;
        Visit firstVisit = initVisit(1);
        Visit secondVisit = Visit.builder()
                .visitId(2)
                .timeslot(Timeslot.builder()
                        .startTime(TEST_START_TIME)
                        .date(TEST_DATE)
                        .office(TEST_OFFICE).build())
                .build();

        when(visitRepository.findAll())
                .thenReturn(Arrays.asList(firstVisit, secondVisit));

        //when
        List<VisitViewDTO> result = visitService.getAllVisitsForPatientDyId(TEST_VISIT_PATIENT_ID);

        //then
        assertEquals(expectedListSize, result.size());
    }

    @Test
    void getAllVisitsForPatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.findAll()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> visitService.getAllVisitsForPatientDyId(1));
    }

    @Test
    void getVisitById_validData_shouldReturnTheVisitById() {
        //given
        Visit addedVisit = initVisit(1);

        when(visitRepository.findById(1))
                .thenReturn(Optional.of(addedVisit));

        //when
        Optional<VisitViewDTO> result = visitService.getVisitById(1);

        //then
        verify(visitRepository, times(1)).findById(eq(1));
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getFirstName().equals(TEST_USER_FIRST_NAME) &&
                visit.getDoctor().getLastName().equals(TEST_USER_LAST_NAME) &&
                visit.getTimeslot().getStartTime().equals(TEST_START_TIME) &&
                visit.getTimeslot().getDate().equals(TEST_DATE) &&
                visit.getTimeslot().getOffice().equals(TEST_OFFICE)
        ));
    }

    @Test
    void getVisitById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(visitRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> visitService.getVisitById(1)) ;
    }

    @Test
    void createVisit_validData_shouldCreateVisit() {
        //given
        Role roleDoctor = Role.builder().name(RoleType.DOCTOR).build();
        Role rolePatient = Role.builder().name(RoleType.PATIENT).build();
        User doctor = initDoctor(1);
        User patient = initPatient(2);
        Timeslot timeslot = initTimeslot(1);

        //when
        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
        when(roleRepository.findRoleByName(eq(RoleType.PATIENT))).thenReturn(Optional.of(rolePatient));
        when(userRepository.findById(doctor.getUserId())).thenReturn(Optional.of(doctor));
        when(userRepository.findById(patient.getUserId())).thenReturn(Optional.of(patient));
        when(timeslotRepository.findById(timeslot.getTimeslotId())).thenReturn(Optional.of(timeslot));

        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
                .doctorId(1)
                .patientId(2)
                .timeslotId(1)
                .build();

        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);

        Optional<VisitViewDTO> result = visitService.getVisitById(1);

        //then
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
        ));
    }

    @Test
    void updateVisitById_repositoryThrowError_shouldThrowServiceException() {
        //given
        VisitDTO visitDTO = VisitDTO.builder().build();
        when(visitRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.updateVisit(visitDTO)) ;
    }

    @Test
    void updateVisitHistory_repositoryThrowError_shouldThrowServiceException() {
        //given
        VisitDTO visitDTO = VisitDTO.builder().build();
        when(visitRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> visitService.updateVisitHistory(visitDTO)) ;
    }

    @Test
    void deleteVisitById_existVisit_shouldDeleteVisit() {
        //given
        Integer visitId = 1;

        //when
        boolean result = visitService.deleteVisit(visitId);

        //then
        assertTrue(result);
        verify(visitRepository, times(visitId)).deleteById(eq(1));
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

    private User initDoctor(Integer id) {
        return User.builder()
                .userId(id)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
                .build();
    }

    private User initPatient(Integer id) {
        return User.builder()
                .userId(id)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();
    }

    private Timeslot initTimeslot(Integer id) {
        return Timeslot.builder()
                .timeslotId(id)
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE)
                .build();
    }

}
