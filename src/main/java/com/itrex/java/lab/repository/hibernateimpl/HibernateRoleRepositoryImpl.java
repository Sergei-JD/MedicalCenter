package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateRoleRepositoryImpl implements RoleRepository {

    @Autowired
    private final Session session;

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r where r.name = :name";

    public HibernateRoleRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Role> getAllRoles() throws RepositoryException {
        List<Role> roles;
        try {
            roles = session.createQuery(FIND_ALL_ROLES_QUERY, Role.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all roles failed" + ex);
        }
        return roles;
    }

    @Override
    public Optional<Role> getRoleByName(String name) throws RepositoryException {
        Role role;
        try {
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
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            int newRoleId = (Integer) session.save("Role", role);
            Role addedRole = session.find(Role.class, newRoleId);

            transaction.commit();

            return addedRole;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Request to add role failed " + ex);
        }
    }

}
