package com.itrex.java.lab.service.impl;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.PatientDTO;
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

import java.util.Optional;
import java.util.Set;
import java.util.List;
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
    public List<PatientDTO> getAllPatients() {
        try {
            List<User> patients = userRepository.getAllUsersByRole(RoleType.PATIENT);

            return patients.stream()
                    .map(userConverter::toPatientDto)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all patients.\n" + ex);
        }
    }

    @Override
    public PatientDTO getPatientById(int patientId) {
        PatientDTO patientDTO = null;
        try {
            Optional<User> patient = userRepository.getUserById(patientId);
            if (patient.isPresent()) {
                patientDTO = userConverter.toPatientDto(patient.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get patient by id " + patientId + ".\n" + ex);
        }

        return patientDTO;
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
    public void updateHistory(int patientId) {

    }
}
