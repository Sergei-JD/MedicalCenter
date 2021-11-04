package com.itrex.java.lab.repository.jdbcimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.exception_handler.RepositoryException;
import com.itrex.java.lab.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/*@Repository
public class JDBCRoleRepositoryImpl implements RoleRepository {

    private static final String ROLE_ID_COLUMN = "role_id";
    private static final String NAME_COLUMN = "name";

    private static final String SELECT_ALL_ROLE_QUERY = "SELECT * FROM role";
    private static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM role WHERE name = ?";
    private static final String INSERT_ROLE_QUERY = "INSERT INTO role(name) VALUES (?)";

    private final DataSource dataSource;

    public JDBCRoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Role> getAllRoles() throws RepositoryException {
        List<Role> roles = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL_ROLE_QUERY)) {
            while (resultSet.next()) {
                Role role = buildRole(resultSet);

                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Request to get all roles failed" + ex);
        }

        return roles;
    }

    @Override
    public Optional<Role> getRoleByName(String name) throws RepositoryException {
        Role role = null;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_ROLE_BY_NAME_QUERY)) {
            pstm.setString(1, name);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    role = buildRole(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("Request to get role named = " + name + " = failed" + ex);
        }

        return Optional.ofNullable(role);
    }

    @Override
    public Role add(Role role) throws RepositoryException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(INSERT_ROLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, role.getName());

            int effectiveRows = pstm.executeUpdate();

            Role addedRole = null;
            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addedRole.setRoleId(generatedKeys.getInt(ROLE_ID_COLUMN));
                    }
                }
            }
            return addedRole;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("Request to add role failed " + ex);
        }
    }

    private Role buildRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setRoleId(resultSet.getInt(ROLE_ID_COLUMN));
        role.setName(resultSet.getString(NAME_COLUMN));

        return role;
    }

}*/
