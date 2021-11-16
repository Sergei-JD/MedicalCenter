package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;

import java.util.List;

public interface RoleRepository {

    List<Role> getAllRoles();

    Role getRoleByType(RoleType role);

    Role add(Role role);

}
