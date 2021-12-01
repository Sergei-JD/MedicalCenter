package com.itrex.java.lab.persistence.dataimpl;

import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAllByRolesName(RoleType role, Pageable pageable);

    Optional<User> findUserByEmail(String email);

}
