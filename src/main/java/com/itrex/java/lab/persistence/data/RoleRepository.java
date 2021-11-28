package com.itrex.java.lab.persistence.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> getRoleByName(RoleType role);

}
