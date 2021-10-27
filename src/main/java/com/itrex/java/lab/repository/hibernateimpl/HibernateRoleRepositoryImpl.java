package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final Session session;

    public HibernateRoleRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";
    private static final String FIND_ROLE_BY_NAME_QUERY = "FROM Role r where r.name = :name";


    @Override
    public List<Role> getAllRole() throws RepositoryException {
        List<Role> roles;
        try {
            roles = session.createQuery(FIND_ALL_ROLES_QUERY, Role.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Role table is empty");
        }
        return roles;
    }

    @Override
    public Role getRoleByName(String name) throws RepositoryException {
        Role role;
        try {
            role = (Role) session.createQuery(FIND_ROLE_BY_NAME_QUERY)
                    .setParameter("name", name)
                    .uniqueResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Role does not exist by name = " + name);
        }

        return role;
    }

    @Override
    public void addRole(Role role) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int newRoleId = (Integer) session.save("Role", role);
            session.find(Role.class, newRoleId);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }


}
