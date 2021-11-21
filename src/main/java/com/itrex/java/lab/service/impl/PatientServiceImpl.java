package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.PatientViewDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.UserRepository;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void createPatient(CreatePatientDTO patientDTO) {
        try {
            User user = userConverter.toUser(patientDTO);

            user.setRoles(Set.of(Role.builder()
                    .name(RoleType.PATIENT)
                    .build()));

            userRepository.add(user);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create patient.\n" + ex);
        }
    }

    @Override
    public List<PatientViewDTO> getAllPatients() {
        try {
            List<User> patients = userRepository.getAllUsersByRole(RoleType.PATIENT);

            return patients.stream()
                    .map(userConverter::toPatientViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all patients.\n" + ex);
        }
    }

    @Override
    public Optional<PatientViewDTO> getPatientById(int patientId) {
        PatientViewDTO patientDTO = null;
        try {
            Optional<User> patient = userRepository.getUserById(patientId);
            if (patient.isPresent()) {
                patientDTO = userConverter.toPatientViewDTO(patient.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get patient by id " + patientId + ".\n" + ex);
        }

        return Optional.ofNullable(patientDTO);
    }

    @Override
    public boolean deletePatient(int patientId) {
        try {
            return userRepository.deleteUserById(patientId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete patient by id" + patientId + ".\n" + ex);
        }
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) {
        if (!isValidPatientDTO(patientDTO) || patientDTO.getUserId() == null) {
            throw new ServiceException("Failed to update patient. Not valid patientDTO.");
        }
        User patient = userRepository.getUserById(patientDTO.getUserId())
                .orElseThrow(() -> new ServiceException("Failed to update patient no such patient"));

        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setAge(patientDTO.getAge());
        patient.setEmail(patientDTO.getEmail());
        patient.setPassword(patientDTO.getPassword());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNum(patientDTO.getPhoneNum());

        try {
            userRepository.update(patient);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update patient.\n" + ex);
        }

        return userConverter.toPatientDTO(patient);
    }

    private boolean isValidPatientDTO(CreatePatientDTO patientDTO) {
        return patientDTO != null && patientDTO.getFirstName() != null && patientDTO.getLastName() != null
                && patientDTO.getAge() != null && patientDTO.getGender() != null;
    }
}
