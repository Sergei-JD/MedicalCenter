package com.itrex.java.lab.repository.jdbcimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JDBCUserRepositoryImpl implements UserRepository {

    private static final String USER_ID_COLUMN = "user_id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String AGE_COLUMN = "age";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String GENDER_COLUMN = "gender";
    private static final String PHONE_NUM_COLUMN = "phone_num";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO user(first_name, last_name, age, email, password, gender, phone_num) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_ROLE_QUERY = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";
    private static final String DELETE_USER_ID_FROM_USER_QUERY = "DELETE FROM user WHERE user_id = ?";
    private static final String DELETE_USER_ID_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id = ?";
    private static final String SELECT_USER_BY_ROLE_QUERY =
            "SELECT u.* FROM USER AS u " +
                        "JOIN user_role AS ur ON u.user_id = ur.user_id " +
                        "JOIN role r on ur.role_id = r.role_id WHERE r.name = ?";

    private final DataSource dataSource;

    public JDBCUserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean assignRole(User user, Role role) throws RepositoryException {
        int effectiveRows = 0;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(INSERT_USER_ROLE_QUERY)) {
            pstm.setInt(1, user.getUserId());
            pstm.setInt(2, role.getRoleId());
            effectiveRows = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }

        return effectiveRows > 0;
    }

    @Override
    public boolean deleteUserById(int userId) throws RepositoryException {
        boolean success;
        try(Connection con = dataSource.getConnection()) {
            try {
                con.setAutoCommit(false);
                PreparedStatement pstm1 = con.prepareStatement(DELETE_USER_ID_FROM_USER_ROLE_QUERY);
                PreparedStatement pstm2 = con.prepareStatement(DELETE_USER_ID_FROM_USER_QUERY);

                pstm1.setInt(1, userId);
                pstm2.setInt(1, userId);

                success = pstm1.executeUpdate() == 1;
                success = pstm2.executeUpdate() == 1;

                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
                throw new RepositoryException("Failed to delete user with id " + userId);
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("Can't delete user with id " + userId);
        }

        return success;
    }

    @Override
    public User getUserById(int userId) throws RepositoryException {
        User user = null;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_USER_BY_ID_QUERY)) {
            pstm.setInt(1, userId);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    user = buildUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("User does not exist by id = " + userId);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws RepositoryException {
        User user = null;
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_USER_BY_EMAIL_QUERY)) {
            pstm.setString(1, email);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    user = buildUser(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException("User does not exist by email = " + email);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() throws RepositoryException {
        List<User> users = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            Statement stm = con.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                User user = buildUser(resultSet);

                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Failed to get all users!");
        }

        return users;
    }

    @Override
    public List<User> getAllUserByRole(String role) throws RepositoryException {
        List<User> users = new ArrayList<>();
        try(Connection con = dataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_USER_BY_ROLE_QUERY)) {
            pstm.setString(1, role);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    User user = buildUser(resultSet);

                    users.add(user);
                }
            }

        } catch (SQLException ex) {
            throw new RepositoryException(ex.getMessage());
        }

        return users;
    }

    @Override
    public void add(User user) throws RepositoryException {
        try (Connection con = dataSource.getConnection()) {
            insertUser(user, con);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public void addAll(List<User> users) throws RepositoryException {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (User user : users) {
                    insertUser(user, con);
                }

                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
                throw new RepositoryException("Users were not added to the table");
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }

    private void insertUser(User user, Connection con) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, user.getFirstName());
            pstm.setString(2, user.getLastName());
            pstm.setInt(3, user.getAge());
            pstm.setString(4, user.getEmail());
            pstm.setString(5, user.getPassword());
            pstm.setString(6, user.getGender());
            pstm.setInt(7, user.getPhoneNum());

            int effectiveRows = pstm.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(USER_ID_COLUMN));
                    }
                }
            }
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(USER_ID_COLUMN));
        user.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
        user.setLastName(resultSet.getString(LAST_NAME_COLUMN));
        user.setAge(resultSet.getInt(AGE_COLUMN));
        user.setEmail(resultSet.getString(EMAIL_COLUMN));
        user.setPassword(resultSet.getString(PASSWORD_COLUMN));
        user.setGender(resultSet.getString(GENDER_COLUMN));
        user.setPhoneNum(resultSet.getInt(PHONE_NUM_COLUMN));

        return user;
    }

}
