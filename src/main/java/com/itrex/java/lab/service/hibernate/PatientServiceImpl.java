package com.itrex.java.lab.service.hibernate;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.util.UserConversionUtils;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreatePatientDTO createPatient(CreatePatientDTO patientDTO) {
        try {
            User user = UserConversionUtils.toUser(patientDTO);

            user.setRoles(Set.of(Role.builder()
                    .name(RoleType.PATIENT)
                    .build()));

            userRepository.add(user);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create patient.\n" + ex);
        }

        return patientDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientViewDTO> getAllPatients() {
        try {
            List<User> patients = userRepository.getAllUsersByRole(RoleType.PATIENT);

            return patients.stream()
                    .map(UserConversionUtils::toPatientViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all patients.\n" + ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientViewDTO> getPatientById(int patientId) {
        PatientViewDTO patientDTO = null;
        try {
            Optional<User> patient = userRepository.getUserById(patientId);
            if (patient.isPresent()) {
                patientDTO = UserConversionUtils.toPatientViewDTO(patient.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get patient by id " + patientId + ".\n" + ex);
        }

        return Optional.ofNullable(patientDTO);
    }

    @Override
    @Transactional
    public boolean deletePatient(int patientId) {
        try {
            return userRepository.deleteUserById(patientId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete patient by id" + patientId + ".\n" + ex);
        }
    }

    @Override
    @Transactional
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

        return UserConversionUtils.toPatientDTO(patient);
    }

    private boolean isValidPatientDTO(CreatePatientDTO patientDTO) {
        return patientDTO != null && patientDTO.getFirstName() != null && patientDTO.getLastName() != null
                && patientDTO.getAge() != null && patientDTO.getGender() != null;
    }
}