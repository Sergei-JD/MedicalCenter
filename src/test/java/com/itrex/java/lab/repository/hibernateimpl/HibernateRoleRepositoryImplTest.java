package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.repository.RoleRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRoleRepositoryImplTest extends BaseRepositoryTest {

    private final RoleRepository roleRepository;

    HibernateRoleRepositoryImplTest() {
        super();
        roleRepository = new HibernateRoleRepositoryImpl(getSessionFactory().openSession());
    }

    @Test
    void getAllRole() throws RepositoryException {
        //given
        Integer allRolesCount = 2;
        List<Role> allRoles = roleRepository.getAllRole();

        //when && then
        assertEquals(allRoles.size(), allRolesCount);
    }

    @Test
    void getRoleByName() throws RepositoryException {
        //given
        String roleName = "Doctor";

        //when
        Role result = roleRepository.getRoleByName(roleName);

        //then
        assertEquals("Doctor", result.getName());
    }

    @Test
    void addRoleNameNull_notValidData_shouldThrowRepositoryException() {
        //given
        Role newRole = new Role();

        //when && then
        assertThrows(RepositoryException.class, () -> roleRepository.add(newRole));
    }
}