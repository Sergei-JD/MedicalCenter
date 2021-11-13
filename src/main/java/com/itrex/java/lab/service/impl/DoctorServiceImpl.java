package com.itrex.java.lab.service.impl;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void createDoctor(CreateDoctorDTO doctorDTO) {
        try {
            User user = userConverter.toUser(doctorDTO);

            user.setRoles(Set.of(Role.builder()
                    .name(RoleType.DOCTOR)
                    .build()));

            userRepository.add(user);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create doctor.\n" + ex);
        }
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        try {
            List<User> doctors = userRepository.getAllUsersByRole(RoleType.DOCTOR);

            return doctors.stream()
                    .map(userConverter::toDoctorDto)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all doctors.\n" + ex);
        }
    }

    @Override
    public DoctorDTO getDoctorById(int doctorId) {
        DoctorDTO doctorDTO = null;
        try {
            Optional<User> doctor = userRepository.getUserById(doctorId);
            if (doctor.isPresent()) {
                doctorDTO = userConverter.toDoctorDto(doctor.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get doctor by id " + doctorId + ".\n" + ex);
        }

        return doctorDTO;
    }

    @Override
    public boolean deleteDoctor(int doctorId) {
        try {
            return userRepository.deleteUserById(doctorId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete doctor by id" + doctorId + ".\n" + ex);
        }
    }

}
