package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> getAllRoles();

    Optional<Role> getRoleByType(RoleType role);

    Role add(Role role);

}
