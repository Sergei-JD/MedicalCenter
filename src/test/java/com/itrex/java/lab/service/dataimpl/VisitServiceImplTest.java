package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.VisitRepository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;
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

    @Mock
    private VisitRepository visitRepository;

    @Test
    void createVisit_validData_shouldCreateVisit() {
        //given
        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
                .doctorId(1)
                .patientId(1)
                .timeslotId(1)
                .build();
        Visit visit = initVisit(1);

        //when
        when(visitRepository.save(visit)).thenReturn(visit);
        CreateVisitDTO actualVisitDTO = visitService.createVisit(visitDTO);

        //then
        assertAll(
                () -> assertEquals(visit.getDoctor().getUserId(), actualVisitDTO.getDoctorId()),
                () -> assertEquals(visit.getPatient().getUserId(), actualVisitDTO.getPatientId()),
                () -> assertEquals(visit.getTimeslot().getTimeslotId(), actualVisitDTO.getTimeslotId())
        );
    }

    @Test
    void createVisit_repositoryThrowError_shouldThrowServiceException() {
        //given
        CreateVisitDTO visitDTO = CreateVisitDTO.builder()
                .doctorId(DoctorDTO.builder()
                        .firstName("M")
                        .lastName("M")
                        .age(25)
                        .gender("M")
                        .build()
                        .getUserId())
                .patientId(PatientDTO.builder()
                        .firstName("F")
                        .lastName("F")
                        .age(28)
                        .gender("M")
                        .build().getUserId())
                .timeslotId(TimeslotDTO.builder()
                        .startTime(Instant.now())
                        .date(Instant.now())
                        .office(505)
                        .build()
                        .getTimeslotId())
                .build();

        //when
        when(visitService.createVisit(visitDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(ServiceException.class, () -> visitService.createVisit(visitDTO));
    }

    @Test
    void getAllVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 2;
        Visit firstVisit = initVisit(1);
        Visit secondVisit = initVisit(2);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("visitId").descending());

        when(visitRepository.findAll())
                .thenReturn(Arrays.asList(firstVisit, secondVisit));

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

        when(visitRepository.findAll())
                .thenReturn(Arrays.asList(firstVisit, secondVisit));

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
    void deleteVisitById_existVisit_shouldDeleteVisit() {
        //given
        Integer visitId = 1;

        //when
        boolean result = visitService.deleteVisit(visitId);

        //then
        assertTrue(result);
        verify(visitRepository, times(visitId)).deleteById(eq(1));
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
