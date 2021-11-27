package com.itrex.java.lab.service.hibernate;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateDoctorDTO createDoctor(CreateDoctorDTO doctorDTO) {
        try {
            User user = UserConversionUtils.toUser(doctorDTO);

            user.setRoles(Set.of(Role.builder()
                    .name(RoleType.DOCTOR)
                    .build()));

            userRepository.add(user);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create doctor.\n" + ex);
        }

        return doctorDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorViewDTO> getAllDoctors() {
        try {
            List<User> doctors = userRepository.getAllUsersByRole(RoleType.DOCTOR);

            return doctors.stream()
                    .map(UserConversionUtils::toDoctorViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all doctors.\n" + ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorViewDTO> getDoctorById(int doctorId) {
        DoctorViewDTO doctorDTO = null;
        try {
            Optional<User> doctor = userRepository.getUserById(doctorId);
            if (doctor.isPresent()) {
                doctorDTO = UserConversionUtils.toDoctorViewDTO(doctor.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get doctor by id " + doctorId + ".\n" + ex);
        }

        return Optional.ofNullable(doctorDTO);
    }

    @Override
    @Transactional
    public boolean deleteDoctor(int doctorId) {
        try {
            return userRepository.deleteUserById(doctorId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete doctor by id" + doctorId + ".\n" + ex);
        }
    }

    @Override
    @Transactional
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        if (!isValidDoctorDTO(doctorDTO) || doctorDTO.getUserId() == null) {
            throw new ServiceException("Failed to update doctor. Not valid doctorDTO.");
        }
        User doctor = userRepository.getUserById(doctorDTO.getUserId())
                .orElseThrow(() -> new ServiceException("Failed to update doctor no such doctor"));

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setAge(doctorDTO.getAge());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setGender(doctorDTO.getGender());
        doctor.setPhoneNum(doctorDTO.getPhoneNum());

        try {
            userRepository.update(doctor);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update doctor.\n" + ex);
        }

        return UserConversionUtils.toDoctorDTO(doctor);
    }

    private boolean isValidDoctorDTO(CreateDoctorDTO doctorDTO) {
        return doctorDTO != null && doctorDTO.getFirstName() != null && doctorDTO.getLastName() != null
                && doctorDTO.getAge() != null && doctorDTO.getGender() != null;
    }

}
