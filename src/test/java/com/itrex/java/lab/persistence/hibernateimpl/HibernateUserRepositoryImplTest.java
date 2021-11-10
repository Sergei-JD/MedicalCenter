package com.itrex.java.lab.persistence.hibernateimpl;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier(value = "hibernateUserRepositoryImpl")
    private UserRepository userRepository;
    @Autowired
    @Qualifier("hibernateRoleRepositoryImpl")
    private RoleRepository roleRepository;

    @Test
    void deleteUserById_existUser_shouldDeleteUser() throws RepositoryException {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();
        User savedUser = userRepository.add(newUser);

        //when
        boolean result = userRepository.deleteUserById(savedUser.getUserId());

        //then
        assertTrue(result);
    }

    @Test
    void deleteUserById_notExistUser_shouldNotDeleteUser() throws RepositoryException {
        //given
        int userId = -1;
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();
        userRepository.add(newUser);
        List<User> allUsersBeforeDelete = userRepository.getAllUsers();
        assertTrue(allUsersBeforeDelete.stream().noneMatch(user -> user.getUserId().equals(userId)));

        //when
        boolean result = userRepository.deleteUserById(userId);

        //then
        assertFalse(result);
        List<User> allUsersAfterDelete = userRepository.getAllUsers();
        assertTrue(allUsersBeforeDelete.size() == allUsersAfterDelete.size());
    }

    @Test
    void assignRole_shouldReturnTrueWhenTheUserGetsTheRole() throws RepositoryException {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();
        User savedUser = userRepository.add(newUser);

        Role newRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();
        Role savedRole = roleRepository.add(newRole);

        List<User> allUserByRole = userRepository.getAllUsersByRole(RoleType.DOCTOR);
        assertTrue(allUserByRole.stream().noneMatch(user -> user.getUserId().equals(newUser.getUserId())));

        //when
        boolean result = userRepository.assignRole(savedUser, savedRole);

        //then
        assertTrue(result);
        allUserByRole = userRepository.getAllUsersByRole(newRole.getName());
        assertTrue(allUserByRole.stream().anyMatch(user -> user.getUserId().equals(newUser.getUserId())));
    }

    @Test
    void getUserById_shouldReturnTheUserById() throws RepositoryException {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();
        userRepository.add(newUser);

        //when
        Optional<User> result = userRepository.getUserById(newUser.getUserId());

        //then
        assertAll(
                () -> assertEquals("Kurt", result.get().getFirstName()),
                () -> assertEquals("Kobe", result.get().getLastName()),
                () -> assertEquals(43, result.get().getAge()),
                () -> assertEquals("M", result.get().getGender())
        );
    }

    @Test
    void getUserByEmail_shouldReturnTheUserByEmail() throws RepositoryException {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .email("ivan@email.com")
                .password("563fh5vf")
                .gender("M")
                .build();
        userRepository.add(newUser);

        //when
        Optional<User> result = userRepository.getUserByEmail(newUser.getEmail());

        //then
        assertAll(
                () -> assertEquals("Kurt", result.get().getFirstName()),
                () -> assertEquals("Kobe", result.get().getLastName()),
                () -> assertEquals(43, result.get().getAge()),
                () -> assertEquals("M", result.get().getGender())
        );
    }

    @Test
    void getAllUsers_shouldReturnTheNumberOfAllUsers() throws RepositoryException {
        //given
        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(43)
                .gender("M")
                .build();
        User newUser2 = User.builder()
                .firstName("Naomi")
                .lastName("Jafris")
                .age(33)
                .gender("F")
                .build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        //when && then
        assertEquals(userRepository.getAllUsers().size(), 2);
    }

    @Test
    void getAllUserByRole_shouldReturnTheNumberAllUsersByRole() throws RepositoryException {
        //given
        int usersCountByRoleDoctor = 1;

        User newUser1 = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(55)
                .gender("M")
                .build();
        User newUser2 = User.builder()
                .firstName("Jule")
                .lastName("Jackson")
                .age(33)
                .gender("F").build();

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Role newRole = Role.builder()
                .name(RoleType.DOCTOR).build();

        roleRepository.add(newRole);

        //when
        boolean result = userRepository.assignRole(newUser1, newRole);
        List<User> allUserByRole = userRepository.getAllUsersByRole(RoleType.DOCTOR);

        //then
        assertTrue(result);
        assertEquals(allUserByRole.size(), usersCountByRoleDoctor);
    }

    @Test
    void addUser_notValidData_genderNull_shouldThrowRepositoryException() {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(55)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_ageNull_shouldThrowRepositoryException() {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_lastName_Null_shouldThrowRepositoryException() {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .age(55)
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_firstNameNull_shouldThrowRepositoryException() {
        //given
        User newUser = User.builder()
                .lastName("Kobe")
                .age(55)
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_ValidData_shouldAddUser() throws RepositoryException {
        //given
        User newUser = User.builder()
                .firstName("Kurt")
                .lastName("Kobe")
                .age(55)
                .gender("M")
                .build();

        //when
        User result = userRepository.add(newUser);

        //then
        assertAll(
                () -> assertEquals("Kurt", result.getFirstName()),
                () -> assertEquals("Kobe", result.getLastName()),
                () -> assertEquals(55, result.getAge()),
                () -> assertEquals("M", result.getGender())
        );
    }

}
