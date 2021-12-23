package com.itrex.java.lab.persistence.dataimpl;

import org.junit.jupiter.api.Test;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class HibernateUserRepositoryImplTest extends BaseRepositoryTest {

    private static final int USER_AGE = "USER_AGE".hashCode();

    @Autowired
    @Qualifier(value = "hibernateUserRepositoryImpl")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("hibernateRoleRepositoryImpl")
    private RoleRepository roleRepository;

    @Test
    void deleteUserById_existUser_shouldDeleteUser() throws RepositoryException {
        //given
        User addUser = createRandomUser();

        User savedUser = userRepository.add(addUser);

        //when
        boolean result = userRepository.deleteUserById(savedUser.getUserId());

        //then
        assertTrue(result);
    }

    @Test
    void deleteUserById_notExistUser_shouldNotDeleteUser() throws RepositoryException {
        //given
        int userId = -1;
        User addUser = createRandomUser();

        userRepository.add(addUser);

        List<User> allUsersBeforeDelete = userRepository.getAllUsers();
        assertTrue(allUsersBeforeDelete.stream().noneMatch(user -> user.getUserId().equals(userId)));

        //when
        boolean result = userRepository.deleteUserById(userId);

        //then
        assertFalse(result);
        List<User> allUsersAfterDelete = userRepository.getAllUsers();
        assertEquals(allUsersBeforeDelete.size(), allUsersAfterDelete.size());
    }

    @Test
    void assignRole_shouldReturnTrueWhenTheUserGetsTheRole() throws RepositoryException {
        //given
        User addUser = createRandomUser();

        User savedUser = userRepository.add(addUser);

        Role addRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        Role savedRole = roleRepository.add(addRole);

        List<User> allUserByRole = userRepository.getAllUsersByRole(RoleType.DOCTOR);
        assertTrue(allUserByRole.stream().noneMatch(user -> user.getUserId().equals(addUser.getUserId())));

        //when
        boolean result = userRepository.assignRole(savedUser, savedRole);

        //then
        assertTrue(result);

        allUserByRole = userRepository.getAllUsersByRole(addRole.getName());
        assertTrue(allUserByRole.stream().anyMatch(user -> user.getUserId().equals(addUser.getUserId())));
    }

    @Test
    void getUserById_shouldReturnTheUserById() throws RepositoryException {
        //given
        User addUser = createRandomUser();

        userRepository.add(addUser);

        //when
        Optional<User> result = userRepository.getUserById(addUser.getUserId());

        //then
        result.ifPresent(user -> assertAll(
                () -> assertEquals(addUser.getFirstName(), result.get().getFirstName()),
                () -> assertEquals(addUser.getLastName(), result.get().getLastName()),
                () -> assertEquals(addUser.getAge(), result.get().getAge()),
                () -> assertEquals(addUser.getGender(), result.get().getGender())
        ));
    }

    @Test
    void getUserByEmail_shouldReturnTheUserByEmail() throws RepositoryException {
        //given
        User addUser = createRandomUser();

        userRepository.add(addUser);

        //when
        Optional<User> result = userRepository.getUserByEmail(addUser.getEmail());

        //then
        result.ifPresent(user -> assertAll(
                () -> assertEquals(addUser.getFirstName(), result.get().getFirstName()),
                () -> assertEquals(addUser.getLastName(), result.get().getLastName()),
                () -> assertEquals(addUser.getAge(), result.get().getAge()),
                () -> assertEquals(addUser.getGender(), result.get().getGender())
        ));
    }

    @Test
    void getAllUsers_shouldReturnTheNumberOfAllUsers() throws RepositoryException {
        //given
        int expectedUserListSize = 2;
        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        //when && then
        assertEquals(expectedUserListSize, userRepository.getAllUsers().size());
    }

    @Test
    void getAllUserByRole_shouldReturnTheNumberAllUsersByRole() throws RepositoryException {
        //given
        int expectedUserByRoleListSize = 1;

        User firstUser = createRandomUser();
        User secondUser = createRandomUser();

        userRepository.add(firstUser);
        userRepository.add(secondUser);

        Role addRole = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        roleRepository.add(addRole);

        //when
        boolean result = userRepository.assignRole(firstUser, addRole);
        List<User> allUserByRole = userRepository.getAllUsersByRole(RoleType.DOCTOR);

        //then
        assertTrue(result);
        assertEquals(expectedUserByRoleListSize, allUserByRole.size());
    }

    @Test
    void addUser_notValidData_genderNull_shouldThrowRepositoryException() {
        //given
        User addUser = User.builder()
                .firstName(RandomStringUtils.random(4))
                .lastName(RandomStringUtils.random(4))
                .age(USER_AGE)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(addUser));
    }

    @Test
    void addUser_notValidData_ageNull_shouldThrowRepositoryException() {
        //given
        User addUser = User.builder()
                .firstName(RandomStringUtils.random(4))
                .lastName(RandomStringUtils.random(4))
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(addUser));
    }

    @Test
    void addUser_notValidData_lastName_Null_shouldThrowRepositoryException() {
        //given
        User addUser = User.builder()
                .firstName(RandomStringUtils.random(4))
                .age(USER_AGE)
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(addUser));
    }

    @Test
    void addUser_notValidData_firstNameNull_shouldThrowRepositoryException() {
        //given
        User addUser = User.builder()
                .lastName(RandomStringUtils.random(4))
                .age(USER_AGE)
                .gender("M")
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> userRepository.add(addUser));
    }

    @Test
    void addUser_ValidData_shouldAddUser() throws RepositoryException {
        //given
        User addUser = createRandomUser();

        //when
        User result = userRepository.add(addUser);

        //then
        assertAll(
                () -> assertEquals(addUser.getFirstName(), result.getFirstName()),
                () -> assertEquals(addUser.getLastName(), result.getLastName()),
                () -> assertEquals(addUser.getAge(), result.getAge()),
                () -> assertEquals(addUser.getGender(), result.getGender())
        );
    }

    private User createRandomUser() {
        return User.builder()
                .firstName(RandomStringUtils.random(4))
                .lastName(RandomStringUtils.random(4))
                .age(USER_AGE)
                .gender("M")
                .build();
    }

}
