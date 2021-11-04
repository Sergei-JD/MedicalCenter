package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.exception_handler.RepositoryException;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserRepositoryImplTest extends BaseRepositoryTest {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public HibernateUserRepositoryImplTest() {
        super();
        this.userRepository = getApplicationContext().getBean(HibernateUserRepositoryImpl.class);
        this.roleRepository = getApplicationContext().getBean(HibernateRoleRepositoryImpl.class);
    }

    @Test
    void deleteUserById_existUser_shouldDeleteUser() throws RepositoryException {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(43);
        newUser.setGender("M");
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
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(43);
        newUser.setGender("M");
        userRepository.add(newUser);
        List<User> allUsersBeforeDelete = userRepository.getAllUsers();
        assertTrue(allUsersBeforeDelete.stream().noneMatch(user -> user.getUserId().equals(userId)));

        //when
        boolean result = userRepository.deleteUserById(userId);

        //then
        assertFalse(result);
        List<User> allUsersAfterDelete = userRepository.getAllUsers();
        assertTrue(allUsersBeforeDelete.size() == allUsersAfterDelete.size()
            && allUsersBeforeDelete.containsAll(allUsersAfterDelete));
    }

    @Test
    void assignRole_shouldReturnTrueWhenTheUserGetsTheRole() throws RepositoryException {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(43);
        newUser.setGender("M");
        User savedUser = userRepository.add(newUser);

        Role newRole = new Role();
        newRole.setName("Doctor");
        Role savedRole = roleRepository.add(newRole);

        List<User> allUserByRole = userRepository.getAllUsersByRole("Doctor");
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
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(43);
        newUser.setGender("M");
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
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(43);
        newUser.setEmail("ivan@email.com");
        newUser.setPassword("563fh5vf");
        newUser.setGender("M");
        userRepository.add(newUser);

        //when
        Optional<User> actualUser = userRepository.getUserByEmail(newUser.getEmail());

        //then
        assertEquals(newUser, actualUser.get());
    }

    @Test
    void getAllUsers_shouldReturnTheNumberOfAllUsers() throws RepositoryException {
        //given
        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(43);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Naomi");
        newUser2.setLastName("Jafris");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        //when && then
        assertEquals(userRepository.getAllUsers().size(), 2);
    }

    @Test
    void getAllUserByRole_shouldReturnTheNumberAllUsersByRole() throws RepositoryException {
        //given
        int usersCountByRoleDoctor = 1;
        String roleName = "Doctor";

        User newUser1 = new User();
        newUser1.setFirstName("Kurt");
        newUser1.setLastName("Kobe");
        newUser1.setAge(55);
        newUser1.setGender("M");

        User newUser2 = new User();
        newUser2.setFirstName("Jule");
        newUser2.setLastName("Jackson");
        newUser2.setAge(33);
        newUser2.setGender("F");

        userRepository.add(newUser1);
        userRepository.add(newUser2);

        Role newRole = new Role();
        newRole.setName("Doctor");

        roleRepository.add(newRole);

        //when
        boolean result = userRepository.assignRole(newUser1, newRole);
        List<User> allUserByRole = userRepository.getAllUsersByRole(roleName);

        //then
        assertTrue(result);
        assertEquals(allUserByRole.size(), usersCountByRoleDoctor);
    }

    @Test
    void addUser_notValidData_genderNull_shouldThrowRepositoryException() {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(55);

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_ageNull_shouldThrowRepositoryException() {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setGender("M");

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_lastName_Null_shouldThrowRepositoryException() {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setAge(55);
        newUser.setGender("M");

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_notValidData_firstNameNull_shouldThrowRepositoryException() {
        //given
        User newUser = new User();
        newUser.setLastName("Kobe");
        newUser.setAge(55);
        newUser.setGender("M");

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

    @Test
    void addUser_ValidData_shouldAddUser() throws RepositoryException {
        //given
        User newUser = new User();
        newUser.setFirstName("Kurt");
        newUser.setLastName("Kobe");
        newUser.setAge(55);
        newUser.setGender("M");

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