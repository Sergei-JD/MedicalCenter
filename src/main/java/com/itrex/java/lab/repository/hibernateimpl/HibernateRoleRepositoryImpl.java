package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r where r.name = :name";

    @Autowired
    public HibernateRoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> getAllRoles() throws RepositoryException {
        List<Role> roles;
        try(Session session = sessionFactory.openSession()) {
            roles = session.createQuery(FIND_ALL_ROLES_QUERY, Role.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all roles failed" + ex);
        }

        return roles;
    }

    @Override
    public Optional<Role> getRoleByName(String name) throws RepositoryException {
        Role role;
        try(Session session = sessionFactory.openSession()) {
            role = (Role) session.createQuery(FIND_ROLE_BY_NAME_QUERY)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get role named = " + name + " = failed" + ex);
        }

        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {

            try {
                int newRoleId = (Integer) session.save("Role", role);
                Role addedRole = session.find(Role.class, newRoleId);

                return addedRole;
            } catch (Exception ex) {
                throw new RepositoryException("Request to add role failed " + ex);
            }
        }
    }

}
