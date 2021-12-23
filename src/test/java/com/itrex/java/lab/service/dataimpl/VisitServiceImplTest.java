package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.persistence.dataimpl.TimeslotRepository;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.dataimpl.VisitRepository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.util.UserConversionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TimeslotRepository timeslotRepository;

//    @Test
//    void createVisit_validData_shouldCreateVisit() {
//        //given
//        CreateDoctorDTO doctorDTO = CreateDoctorDTO.builder()
//                .firstName(TEST_USER_FIRST_NAME)
//                .lastName(TEST_USER_LAST_NAME)
//                .age(TEST_USER_AGE)
//                .email(TEST_USER_EMAIL)
//                .password(TEST_USER_PASSWORD)
//                .gender(TEST_USER_GENDER)
//                .phoneNum(TEST_USER_NUMBER_PHONE)
//                .build();
//
//        Role roleDoctor = Role.builder()
//                .name(RoleType.DOCTOR)
//                .build();
//
//        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
//        when(doctorService.createDoctor(doctorDTO)).thenReturn(doctorDTO);
//
//        CreatePatientDTO patientDTO = CreatePatientDTO.builder()
//                .firstName(TEST_USER_FIRST_NAME)
//                .lastName(TEST_USER_LAST_NAME)
//                .age(TEST_USER_AGE)
//                .email(TEST_USER_EMAIL)
//                .password(TEST_USER_PASSWORD)
//                .gender(TEST_USER_GENDER)
//                .phoneNum(TEST_USER_NUMBER_PHONE)
//                .build();
//
//        Role rolePatient = Role.builder()
//                .name(RoleType.PATIENT)
//                .build();
//
//        when(roleRepository.findRoleByName(eq(RoleType.PATIENT))).thenReturn(Optional.of(rolePatient));
//        when(patientService.createPatient(patientDTO)).thenReturn(patientDTO);


//        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
//                .doctorId(userRepository.findById(1).get().getUserId())
//                .patientId(userRepository.findById(2).get().getUserId())
//                .timeslotId(1)
//                .build();

//        //when
//        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);
//
//        Optional<VisitViewDTO> result = visitService.getVisitById(1);
//
//        //then
//        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
//                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
//                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
//        ));
//    }

//    @Test
//    void createVisit_validData_shouldCreateVisit() {
//        //given
//        User doctor = initDoctor(1);
//        DoctorDTO doctorDTO = UserConversionUtils.toDoctorDTO(doctor);
//        Role roleDoctor = Role.builder()
//                .name(RoleType.DOCTOR)
//                .build();
//        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
//        doctorService.createDoctor(doctorDTO);
//
//        User patient = initPatient(2);
//        PatientDTO patientDTO = UserConversionUtils.toPatientDTO(patient);
//        Role rolePatient = Role.builder()
//                .name(RoleType.PATIENT)
//                .build();
//        when(roleRepository.findRoleByName(eq(RoleType.PATIENT))).thenReturn(Optional.of(rolePatient));
//        patientService.createPatient(patientDTO);
//
//        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
//                .doctorId(1)
//                .patientId(2)
//                .timeslotId(1)
//                .build();
//
//        //when
//        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);
//
//        Optional<VisitViewDTO> result = visitService.getVisitById(1);
//
//        //then
//        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
//                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
//                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
//        ));
//    }

//    @Test
//    void createVisit_validData_shouldCreateVisit() {
//        //given
//
//        User doctor = initDoctor(1);
//
//        userRepository.save(doctor);
//
//        User patient = initPatient(2);
//
//        userRepository.save(patient);
//
//        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
//                .doctorId(1)
//                .patientId(2)
//                .timeslotId(1)
//                .build();
//
//        //when
//        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);
//
//        Optional<VisitViewDTO> result = visitService.getVisitById(1);
//
//        //then
//        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
//                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
//                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
//        ));
//    }

    @Test
    void createVisit_validData_shouldCreateVisit() {
        //given
        Role roleDoctor = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        roleRepository.save(roleDoctor);

        Role rolePatient = Role.builder()
                .name(RoleType.PATIENT)
                .build();

        roleRepository.save(rolePatient);

        User doctor = User.builder()
                .userId(1)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
                .build();

        userRepository.save(doctor);

        userRepository.getById(1);

        User patient = User.builder()
                .userId(2)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        userRepository.save(patient);

        userRepository.getById(2);

        Timeslot timeslot = Timeslot.builder()
                .timeslotId(1)
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE)
                .build();

        timeslotRepository.save(timeslot);

        timeslotRepository.getById(1);



        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
                .doctorId(userRepository.findById(1).get().getUserId())
                .patientId(userRepository.findById(2).get().getUserId())
                .timeslotId(timeslotRepository.findById(1).get().getTimeslotId())
                .build();

        //when
        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);

        Optional<VisitViewDTO> result = visitService.getVisitById(1);

        //then
        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
        ));
    }

//    @Test
//    void createVisit_validData_shouldCreateVisit() {
//        //given
//        Role roleDoctor = Role.builder()
//                .name(RoleType.DOCTOR)
//                .build();
//
//        roleRepository.save(roleDoctor);
//
//        Role rolePatient = Role.builder()
//                .name(RoleType.PATIENT)
//                .build();
//
//        roleRepository.save(rolePatient);
//
//        User doctor = User.builder()
//                .userId(1)
//                .firstName(TEST_USER_FIRST_NAME)
//                .lastName(TEST_USER_LAST_NAME)
//                .age(TEST_USER_AGE)
//                .email(TEST_USER_EMAIL)
//                .password(TEST_USER_PASSWORD)
//                .gender(TEST_USER_GENDER)
//                .phoneNum(TEST_USER_NUMBER_PHONE)
//                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
//                .build();
//
//        userRepository.save(doctor);
//
//        userRepository.getById(1);
//
//        User patient = User.builder()
//                .userId(2)
//                .firstName(TEST_USER_FIRST_NAME)
//                .lastName(TEST_USER_LAST_NAME)
//                .age(TEST_USER_AGE)
//                .email(TEST_USER_EMAIL)
//                .password(TEST_USER_PASSWORD)
//                .gender(TEST_USER_GENDER)
//                .phoneNum(TEST_USER_NUMBER_PHONE)
//                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
//                .build();
//
//        userRepository.save(patient);
//
//        userRepository.getById(2);
//
//        Timeslot timeslot = Timeslot.builder()
//                .timeslotId(1)
//                .startTime(TEST_START_TIME)
//                .date(TEST_DATE)
//                .office(TEST_OFFICE)
//                .build();
//
//        timeslotRepository.save(timeslot);
//
//        timeslotRepository.getById(1);
//
//
//
//        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
//                .doctorId(userRepository.findById(1).get().getUserId())
//                .patientId(userRepository.findById(2).get().getUserId())
//                .timeslotId(timeslotRepository.findById(1).get().getTimeslotId())
//                .build();
//
//        //when
//        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);
//
//        Optional<VisitViewDTO> result = visitService.getVisitById(1);
//
//        //then
//        assertTrue(result.stream().allMatch(visit -> visit.getDoctor().getUserId().equals(actualVisitDTO.getDoctorId())
//                && visit.getPatient().getUserId().equals(actualVisitDTO.getPatientId())
//                && visit.getTimeslot().getTimeslotId().equals(actualVisitDTO.getTimeslotId())
//        ));
//    }

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

}
