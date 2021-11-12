package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAllUsers();

    List<User> getAllUsersByRole(RoleType role);

    Optional<User> getUserById(int userId);

    Optional<User> getUserByEmail(String email);

    boolean deleteUserById(int userId);

    User add(User user);

    boolean assignRole(User user, Role role);

}
