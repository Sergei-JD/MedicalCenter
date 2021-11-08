package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> getAllRoles() throws RepositoryException;

    Optional<Role> getRoleByName(String name) throws RepositoryException;

    Role add(Role role) throws RepositoryException;

}
