package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRoleRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier("hibernateRoleRepositoryImpl")
    private RoleRepository roleRepository;

    @Test
    void getAllRole_shouldReturnTheRolesByName() throws RepositoryException {
        //given
        Role newRole1 = new Role();
        newRole1.setName("Doctor");

        Role newRole2 = new Role();
        newRole2.setName("Patient");

        roleRepository.add(newRole1);
        roleRepository.add(newRole2);

        //when && then
        assertEquals(roleRepository.getAllRoles().size(), 2);
    }

    @Test
    void getRoleByName_shouldReturnTheRoleByName() throws RepositoryException {
        //given
        Role newRole = new Role();
        newRole.setName("Doctor");
        roleRepository.add(newRole);

        //when
        Optional<Role> result = roleRepository.getRoleByName(newRole.getName());

        //then
        assertEquals(newRole.getName(), result.get().getName());
    }

    @Test
    void addRole_notValidData_NameNull_shouldThrowRepositoryException() {
        //given
        Role newRole = new Role();

        //when && then
        assertThrows(RepositoryException.class, () -> roleRepository.add(newRole));
    }

    @Test
    void addRole_ValidData_should() throws RepositoryException {
        //given
        Role newRole = new Role();
        newRole.setName("Doctor");
        roleRepository.add(newRole);

        //when
        Optional<Role> result = roleRepository.getRoleByName(newRole.getName());

        //then
        assertEquals(newRole.getName(), result.get().getName());

    }

}
