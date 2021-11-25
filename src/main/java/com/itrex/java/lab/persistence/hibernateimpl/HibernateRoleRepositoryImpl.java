package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r where r.name = :name";


    @Override
    public List<Role> getAllRoles() {
        List<Role> roles;
        try {
            roles = entityManager.createQuery(FIND_ALL_ROLES_QUERY, Role.class).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all roles.\n" + ex);
        }

        return roles;
    }

    @Override
    public Optional<Role> getRoleByType(RoleType roleType) {
        Role role = null;
        try {
            List<Role> roles = entityManager.createQuery(FIND_ROLE_BY_NAME_QUERY, Role.class)
                    .setParameter("name", roleType)
                    .getResultList();
            if (!CollectionUtils.isEmpty(roles)) {
                role = roles.get(0);
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get role named " + roleType.name() + ".\n" + ex);
        }
        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) {
        try {
            Session session = entityManager.unwrap(Session.class);
            int newRoleId = (Integer) session.save("Role", role);

            return session.find(Role.class, newRoleId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add role.\n" + ex);
        }
    }

}
