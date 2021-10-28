package com.itrex.java.lab.repository;


import com.itrex.java.lab.entity.Role;

import java.util.List;

public interface RoleRepository {

    List<Role> getAllRole() throws RepositoryException;

    Role getRoleByName(String name) throws RepositoryException;

    void add(Role role) throws RepositoryException;


}
