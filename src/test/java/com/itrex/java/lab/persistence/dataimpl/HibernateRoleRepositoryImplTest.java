package com.itrex.java.lab.persistence.dataimpl;

import org.junit.jupiter.api.Test;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class HibernateRoleRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier("hibernateRoleRepositoryImpl")
    private RoleRepository roleRepository;

    @Test
    void getAllRole_shouldReturnTheRolesByName() {
        //given
        int expectedRoleListSize = 2;
        Role firstRole = Role.builder().name(RoleType.DOCTOR).build();
        Role secondRole = Role.builder().name(RoleType.PATIENT).build();

        roleRepository.add(firstRole);
        roleRepository.add(secondRole);

        //when && then
        assertEquals( expectedRoleListSize, roleRepository.getAllRoles().size());
    }

        @Test
    void getRoleByName_shouldReturnTheRoleByName() {
        //given
        Role addRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        roleRepository.add(addRole);

        //when
        Optional<Role> result = roleRepository.getRoleByType(RoleType.DOCTOR);

        //then
        result.ifPresent(role -> assertEquals(addRole.getName(), result.get().getName()));
    }

    @Test
    void addRole_notValidData_NameNull_shouldThrowRepositoryException() {
        //given
        Role addRole = Role.builder().build();

        //when && then
        assertThrows(RepositoryException.class, () -> roleRepository.add(addRole));
    }

    @Test
    void addRole_ValidData_should() {
        //given
        Role addRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        //when
        Role result = roleRepository.add(addRole);

        //then
        assertEquals(addRole.getName(), result.getName());
    }

}
