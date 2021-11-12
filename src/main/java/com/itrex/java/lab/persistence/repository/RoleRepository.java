package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> getAllRoles();

    Optional<Role> getRoleByName(String name);

    Role add(Role role);

}
