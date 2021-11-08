package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.UserDTO;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers() throws ServiceException;

    List<UserDTO> getAllUsersByRole(String role) throws ServiceException;

    Optional<UserDTO> getUserById(int userId) throws ServiceException;

    Optional<UserDTO> getUserByEmail(String email) throws ServiceException;

    boolean deleteUserById(int userId) throws ServiceException;

    UserDTO add(User user) throws ServiceException;

//    void addAll(List<User> users) throws ServiceException;

    boolean assignRole(User user, Role role) throws ServiceException;

}
