package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;


import java.util.List;

public class HibernateRoleRepositoryImpl implements RoleRepository {

    private final Session session;

    public HibernateRoleRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_ROLES_QUERY = "select r from Role r ";


    @Override
    public List<Role> getAllRole() throws RepositoryException {
        List<Role> roles;
        try {
            roles = session.createQuery(FIND_ALL_ROLES_QUERY, Role.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Role table is empty");
        }
        return roles;
    }

    @Override
    public Role getRoleByName(String name) throws RepositoryException {
        return null;
    }

    @Override
    public void addRole(Role role) throws RepositoryException {

    }

    @Override
    public void deleteRole(int roleId) throws RepositoryException {

    }
}
