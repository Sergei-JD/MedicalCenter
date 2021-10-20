package com.itrex.java.lab.repository;


import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.impl.RepositoryException;

import java.util.List;

public interface RoleRepository {

    List<Role> getAllRole() throws RepositoryException;

    User getRoleByName(String name) throws RepositoryException;

    void addRole(Role role) throws RepositoryException;

    void deleteRole(Role role) throws RepositoryException;

}
