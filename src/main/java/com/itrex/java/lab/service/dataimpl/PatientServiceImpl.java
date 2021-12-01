package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.util.UserConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User newPatient = User.builder()
                .firstName(patientDTO.getFirstName())
                .lastName(patientDTO.getLastName())
                .age(patientDTO.getAge())
                .email(patientDTO.getEmail())
                .password(patientDTO.getPassword())
                .gender(patientDTO.getGender())
                .phoneNum(patientDTO.getPhoneNum())
                .build();

        return UserConversionUtils.toPatientDTO(userRepository.save(newPatient));
    }

    @Override
    public List<PatientViewDTO> getAllPatients() {

        List<User> patients = userRepository.findAllByRolesName(RoleType.PATIENT);

        return patients.stream()
                .map(UserConversionUtils::toPatientViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PatientViewDTO> getPatientById(int patientId) {
        PatientViewDTO patientDTO = null;

        Optional<User> patient = userRepository.findById(patientId);
        if (patient.isPresent()) {
            patientDTO = UserConversionUtils.toPatientViewDTO(patient.get());
        }

        return Optional.ofNullable(patientDTO);
    }

    @Override
    @Transactional
    public boolean deletePatient(int patientId) {
        userRepository.deleteById(patientId);

        return userRepository.findById(patientId).isEmpty();
    }

    @Override
    @Transactional
    public PatientDTO updatePatient(PatientDTO patientDTO) {
        if (!isValidPatientDTO(patientDTO) || patientDTO.getUserId() == null) {
            throw new ServiceException("Failed to update patient. Not valid patientDTO.");
        }
        User patient = userRepository.findById(patientDTO.getUserId())
                .orElseThrow(() -> new ServiceException("Failed to update patient no such patient"));

        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setAge(patientDTO.getAge());
        patient.setEmail(patientDTO.getEmail());
        patient.setPassword(patientDTO.getPassword());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNum(patientDTO.getPhoneNum());

        userRepository.save(patient);

        return UserConversionUtils.toPatientDTO(patient);
    }

    private boolean isValidPatientDTO(CreatePatientDTO patientDTO) {
        return patientDTO != null &&
                patientDTO.getFirstName() != null &&
                patientDTO.getLastName() != null &&
                patientDTO.getAge() != null &&
                patientDTO.getGender() != null;
    }
}