package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.impl.RepositoryException;

import java.util.List;

public interface UserRepository {

    List<User> getAllUser() throws RepositoryException;

    List<User> getAllUserByRole(String role) throws RepositoryException;

    User getUserById(int userId) throws RepositoryException;

    User getUserByEmail(String email) throws RepositoryException;

    void deleteUserByID(int userId) throws RepositoryException;

    void add(User user) throws RepositoryException;

    void addAll(List<User> users) throws RepositoryException;

    boolean assignRole(User user, Role role) throws RepositoryException;

}
