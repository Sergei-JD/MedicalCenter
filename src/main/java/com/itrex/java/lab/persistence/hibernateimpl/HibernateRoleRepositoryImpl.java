package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r where r.name = :name";

    @Autowired
    public HibernateRoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles;
        try {
            Session session = sessionFactory.getCurrentSession();
            roles = session.createQuery(FIND_ALL_ROLES_QUERY, Role.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all roles.\n" + ex);
        }

        return roles;
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        Role role;
        try {
            Session session = sessionFactory.getCurrentSession();
            role = (Role) session.createQuery(FIND_ROLE_BY_NAME_QUERY)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get role named " + name + ".\n" + ex);
        }

        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) {
        try {
            Session session = sessionFactory.getCurrentSession();
            int newRoleId = (Integer) session.save("Role", role);

            return session.find(Role.class, newRoleId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add role.\n" + ex);
        }
    }

}
