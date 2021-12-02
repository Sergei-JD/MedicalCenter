package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.util.UserConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public CreatePatientDTO createPatient(CreatePatientDTO patientDTO) {
        Role rolePatient = roleRepository.findRoleByName(RoleType.PATIENT)
                .orElseThrow(() -> new ServiceException("Failed to create patient no such role"));
        User newPatient = User.builder()
                .firstName(patientDTO.getFirstName())
                .lastName(patientDTO.getLastName())
                .age(patientDTO.getAge())
                .email(patientDTO.getEmail())
                .password(patientDTO.getPassword())
                .gender(patientDTO.getGender())
                .phoneNum(patientDTO.getPhoneNum())
                .roles(Set.of(rolePatient))
                .build();

        return UserConversionUtils.toPatientDTO(userRepository.save(newPatient));
    }

    @Override
    public Page<PatientViewDTO> getAllPatients(Pageable pageable) {
        Page<User> pagePatients = userRepository.findAllByRolesName(RoleType.PATIENT, pageable);

        List<PatientViewDTO> patients = pagePatients.stream()
                .map(UserConversionUtils::toPatientViewDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(patients);
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
    public Optional<PatientDTO> getPatientByEmail(String email) {
        PatientDTO patientDTO = null;

        Optional<User> patient = userRepository.findUserByEmail(email);
        if (patient.isPresent()) {
            patientDTO = UserConversionUtils.toPatientDTO(patient.get());
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