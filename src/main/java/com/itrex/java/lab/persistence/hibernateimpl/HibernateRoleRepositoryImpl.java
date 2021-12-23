package com.itrex.java.lab.persistence.hibernateimpl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.RoleRepository;

@Deprecated
@Repository
@RequiredArgsConstructor
public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_ROLES_QUERY = "SELECT r FROM Role r";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r WHERE r.name = :name";


    @Override
    public List<Role> getAllRoles() {
        List<Role> roles;
        try {
            roles = entityManager.createQuery(FIND_ALL_ROLES_QUERY, Role.class).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all roles!", ex);
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
            throw new RepositoryException(String.format("Failed to get role named: %s!", roleType.name()), ex);
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
            throw new RepositoryException("Failed to add role!", ex);
        }
    }

}
