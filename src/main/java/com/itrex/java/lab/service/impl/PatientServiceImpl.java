package com.itrex.java.lab.service.impl;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.PatientDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.service.PatientService;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.repository.UserRepository;

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
        User user = userConverter.toUser(patientDTO);
        user.setRoles(Set.of(Role.builder()
                .name(RoleType.PATIENT)
                .build()));
        userRepository.add(user);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<User> patients = userRepository.getAllUsersByRole(RoleType.PATIENT);
        return patients.stream()
                .map(userConverter::toPatientDto)
                .collect(Collectors.toList());
    }

}
