package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.RoleDTO;
import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleDTO> getAllRoles() throws ServiceException;

    Optional<RoleDTO> getRoleByName(String name) throws ServiceException;

    RoleDTO add(Role role) throws ServiceException;

}
