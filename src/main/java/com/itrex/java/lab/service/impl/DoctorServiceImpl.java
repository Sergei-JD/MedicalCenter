package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void createDoctor(CreateDoctorDTO doctorDTO) {
        User user = userConverter.toUser(doctorDTO);
        user.setRoles(Set.of(Role.builder()
                .name(RoleType.DOCTOR)
                .build()));
        userRepository.add(user);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<User> doctors = userRepository.getAllUsersByRole(RoleType.DOCTOR);
        return doctors.stream()
                .map(userConverter::toDoctorDto)
                .collect(Collectors.toList());
    }

}
