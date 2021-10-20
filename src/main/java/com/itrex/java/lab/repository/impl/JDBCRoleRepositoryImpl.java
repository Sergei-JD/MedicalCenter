package com.itrex.java.lab.repository.impl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.RoleRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCRoleRepositoryImpl implements RoleRepository {

    private static final String ROLE_ID_COLUMN = "role_id";
    private static final String NAME_COLUMN = "name";

    private static final String SELECT_ALL_ROLE_QUERY = "SELECT * FROM role";

    private final DataSource dataSource;

    public JDBCRoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Role> getAllRole() throws RepositoryException {
        List<Role> roles = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL_ROLE_QUERY)) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setRoleId(resultSet.getInt("role_id"));
                role.setName(resultSet.getString("name"));

                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Role table is empty");
        }

        return roles;
    }

    @Override
    public User getRoleByName(String name) throws RepositoryException {
        return null;
    }

    @Override
    public void addRole(Role role) throws RepositoryException {

    }

    @Override
    public void deleteRole(Role role) throws RepositoryException {

    }
}
