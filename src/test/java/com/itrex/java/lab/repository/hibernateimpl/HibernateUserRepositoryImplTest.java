package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserRepositoryImplTest extends BaseRepositoryTest {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public HibernateUserRepositoryImplTest() {
        super();
        userRepository = new HibernateUserRepositoryImpl(getSessionFactory().openSession());
        roleRepository = new HibernateRoleRepositoryImpl(getSessionFactory().openSession());
    }

//    @Test
//    void deleteUserByID_existUser_shouldDeleteUser() throws RepositoryException {
//        //given
//        Integer userId = 2;
//        List<User> allUsers = userRepository.getAllUsers();
//        assertTrue(allUsers.stream().anyMatch(user -> user.getUserId().equals(userId)));
//
//        //when
//        boolean result = userRepository.deleteUserById(userId);
//
//        //then
//        assertTrue(result);
//        allUsers = userRepository.getAllUsers();
//        assertTrue(allUsers.stream().noneMatch(user -> user.getUserId().equals(userId)));
//    }

    @Test
    void deleteUserByID_notExistUser_shouldNotDeleteUser() throws RepositoryException {
        //given
        Integer userId = -1;
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
        Integer userId = 4;
        String roleName = "Doctor";
        List<User> allUserByRole = userRepository.getAllUserByRole(roleName);
        assertTrue(allUserByRole.stream().noneMatch(user -> user.getUserId().equals(userId)));

        //when
        boolean result = userRepository.assignRole(userRepository.getUserById(userId), roleRepository.getRoleByName(roleName));

        //then
        assertTrue(result);
        allUserByRole = userRepository.getAllUserByRole(roleName);
        assertTrue(allUserByRole.stream().anyMatch(user -> user.getUserId().equals(userId)));
    }

    @Test
    void getUserById_shouldReturnTheUserById() throws RepositoryException {
        //given
        Integer userId = 6;

        //when
        User result = userRepository.getUserById(userId);

        //then
        assertAll(
                () -> assertEquals(6, result.getUserId()),
                () -> assertEquals("Svetlana", result.getFirstName()),
                () -> assertEquals("Svetlova", result.getLastName()),
                () -> assertEquals(29, result.getAge()),
                () -> assertEquals("svetlana@email.com", result.getEmail()),
                () -> assertEquals("32726781", result.getPassword()),
                () -> assertEquals("F", result.getGender()),
                () -> assertEquals(9135941, result.getPhoneNum())
        );
    }

    @Test
    void getUserByEmail_shouldReturnTheUserByEmail() throws RepositoryException {
        //given
        String userEmail = "ivan@email.com";
        List<User> userByEmail = userRepository.getAllUsers();

        //when
        User result = userRepository.getUserByEmail(userEmail);

        //then
        assertAll(
                () -> assertEquals(1, result.getUserId()),
                () -> assertEquals("Ivan", result.getFirstName()),
                () -> assertEquals("Ivanov", result.getLastName()),
                () -> assertEquals(46, result.getAge()),
                () -> assertEquals("ivan@email.com", result.getEmail()),
                () -> assertEquals("12345678", result.getPassword()),
                () -> assertEquals("M", result.getGender()),
                () -> assertEquals(9379992, result.getPhoneNum())
        );
    }

    @Test
    void getAllUser_shouldReturnTheNumberOfAllUsers() throws RepositoryException {
        //given
        Integer allUsersCount = 6;
        List<User> allUsers = userRepository.getAllUsers();

        //when && then
        assertEquals(allUsers.size(), allUsersCount);
    }

    @Test
    void getAllUserByRole_shouldReturnTheNumberAllUsersByRole() throws RepositoryException {
        //given
        Integer usersByRoleDoctor = 3;
        String roleName = "Doctor";
        List<User> allUserByRole = userRepository.getAllUserByRole(roleName);

        //when && then
        assertEquals(allUserByRole.size(), usersByRoleDoctor);
    }

    @Test
    void addUser_notValidData_shouldThrowRepositoryException() {
        //given
        User newUser = new User();
        newUser.setAge(55);
        newUser.setEmail("obama@gmail.com");
        newUser.setPassword("58tjgf4");
        newUser.setPhoneNum(333333333);

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(newUser));
    }

}