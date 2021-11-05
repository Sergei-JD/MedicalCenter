package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAllUsers() throws RepositoryException;

    List<User> getAllUsersByRole(String role) throws RepositoryException;

    Optional<User> getUserById(int userId) throws RepositoryException;

    Optional<User> getUserByEmail(String email) throws RepositoryException;

    boolean deleteUserById(int userId) throws RepositoryException;

    User add(User user) throws RepositoryException;

    void addAll(List<User> users) throws RepositoryException;

    boolean assignRole(User user, Role role) throws RepositoryException;

}
