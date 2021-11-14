package com.itrex.java.lab.persistence.hibernateimpl;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRoleRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier("hibernateRoleRepositoryImpl")
    private RoleRepository roleRepository;

    @Test
    void getAllRole_shouldReturnTheRolesByName() {
        //given
        Role newRole1 = Role.builder().name(RoleType.DOCTOR).build();
        Role newRole2 = Role.builder().name(RoleType.PATIENT).build();

        roleRepository.add(newRole1);
        roleRepository.add(newRole2);

        //when && then
        assertEquals(roleRepository.getAllRoles().size(), 2);
    }

        @Test
    void getRoleByName_shouldReturnTheRoleByName() {
        //given
        Role newRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        roleRepository.add(newRole);

        //when
        Role result = roleRepository.getRoleByType(RoleType.DOCTOR);

        //then
        assertEquals(newRole.getName(), result.getName());
    }

    @Test
    void addRole_notValidData_NameNull_shouldThrowRepositoryException() {
        //given
        Role newRole = Role.builder().build();

        //when && then
        assertThrows(RepositoryException.class, () -> roleRepository.add(newRole));
    }

    @Test
    void addRole_ValidData_should() {
        //given
        Role newRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        //when
        Role result = roleRepository.add(newRole);

        //then
        assertEquals(newRole.getName(), result.getName());

    }
}
