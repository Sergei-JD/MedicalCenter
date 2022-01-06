package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.DoctorService;
import com.itrex.java.lab.util.UserConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public Page<DoctorViewDTO> getAllDoctors(Pageable pageable) {
        Page<User> pageDoctors = userRepository.findAllByRolesName(RoleType.DOCTOR, pageable);

        List<DoctorViewDTO> doctors = pageDoctors.stream()
                .map(UserConversionUtils::toDoctorViewDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(doctors);
    }

    @Override
    public Optional<DoctorViewDTO> getDoctorById(int doctorId) {
        DoctorViewDTO doctorDTO = null;

        Optional<User> doctor = userRepository.findById(doctorId);
        if (doctor.isPresent()) {
            doctorDTO = UserConversionUtils.toDoctorViewDTO(doctor.get());
        }

        return Optional.ofNullable(doctorDTO);
    }

    @Override
    public Optional<DoctorDTO> getDoctorByEmail(String email) {
        DoctorDTO doctorDTO = null;

        Optional<User> doctor = userRepository.findUserByEmail(email);
        if (doctor.isPresent()) {
            doctorDTO = UserConversionUtils.toDoctorDTO(doctor.get());
        }

        return Optional.ofNullable(doctorDTO);
    }

    @Override
    @Transactional
    public CreateDoctorDTO createDoctor(CreateDoctorDTO doctorDTO) {
        Role roleDoctor = roleRepository.findRoleByName(RoleType.DOCTOR)
                .orElseThrow(() -> new ServiceException("Failed to create doctor no such role"));
        User newDoctor = User.builder()
                .firstName(doctorDTO.getFirstName())
                .lastName(doctorDTO.getLastName())
                .age(doctorDTO.getAge())
                .email(doctorDTO.getEmail())
                .password(doctorDTO.getPassword())
                .gender(doctorDTO.getGender())
                .phoneNum(doctorDTO.getPhoneNum())
                .roles(Set.of(roleDoctor))
                .build();

        return UserConversionUtils.toDoctorDTO(userRepository.save(newDoctor));
    }

    @Override
    @Transactional
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.getUserId() == null) {
            throw new ServiceException("Failed to update doctor. Not valid doctorDTO.");
        }
        User doctor = userRepository.findById(doctorDTO.getUserId())
                .orElseThrow(() -> new ServiceException("Failed to update doctor no such doctor"));

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setAge(doctorDTO.getAge());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setGender(doctorDTO.getGender());
        doctor.setPhoneNum(doctorDTO.getPhoneNum());

        userRepository.save(doctor);

        return UserConversionUtils.toDoctorDTO(doctor);
    }

    @Override
    @Transactional
    public boolean deleteDoctor(int doctorId) {
        userRepository.deleteById(doctorId);

        return userRepository.findById(doctorId).isEmpty();
    }

}
