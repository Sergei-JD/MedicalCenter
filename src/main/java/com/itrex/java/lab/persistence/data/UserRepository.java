package com.itrex.java.lab.persistence.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> getAllByRolesName(RoleType role);

    Optional<User> getUserByEmail(String email);

}
