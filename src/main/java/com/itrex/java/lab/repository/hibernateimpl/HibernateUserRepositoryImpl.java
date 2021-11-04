package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.exception_handler.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class HibernateUserRepositoryImpl implements UserRepository {

    private final Session session;

    public HibernateUserRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_USERS_QUERY = "select u from User u";
    private static final String DELETE_USER_ID_FROM_USER_QUERY = "DELETE FROM user WHERE user_id = ?";
    private static final String DELETE_USER_ID_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "FROM User u where u.email = :email";
    private static final String SELECT_USER_BY_ROLE_QUERY =
            "SELECT u FROM User u JOIN FETCH u.roles r WHERE r.name = :name";

    @Override
    public List<User> getAllUsers() throws RepositoryException {
        List<User> users;
        try {
            users = session.createQuery(FIND_ALL_USERS_QUERY, User.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all users failed" + ex);
        }

        return users;
    }

    @Override
    public List<User> getAllUsersByRole(String role) throws RepositoryException {
        List<User> users;
        try {
            users = session.createQuery(SELECT_USER_BY_ROLE_QUERY, User.class).setParameter("name", role).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users by role!" + role);
        }

        return users;
    }

    @Override
    public Optional<User> getUserById(int userId) throws RepositoryException {
        User user;
        try {
            user = session.find(User.class, userId);
        } catch (Exception ex) {
            throw new RepositoryException("Request to get user by id = " + userId + " = failed" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws RepositoryException {
        User user;
        try {
            user = (User) session.createQuery(SELECT_USER_BY_EMAIL_QUERY)
                    .setParameter("email", email)
                    .uniqueResult();

        } catch (Exception ex) {
            throw new RepositoryException("Request to get user by email = " + email + " = failed" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public boolean deleteUserById(int userId) throws RepositoryException {
        Transaction transaction = null;
        boolean isDeleted = false;
        try {
            transaction = session.beginTransaction();

            session.createSQLQuery(DELETE_USER_ID_FROM_USER_ROLE_QUERY).setParameter(1, userId).executeUpdate();

            User user = session.find(User.class, userId);
            if (user != null) {
                session.delete(user);
                isDeleted = true;
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Request to delete user by id " + userId + " failed" + ex);
        }

        return isDeleted;
    }

    @Override
    public User add(User user) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            int newUserId = (Integer) session.save("User", user);
            User addedUser = session.find(User.class, newUserId);

            transaction.commit();

            return addedUser;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Request to add user failed" + ex);
        }
    }

    @Override
    public void addAll(List<User> users) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            for (User user : users) {
                int newUserId = (Integer) session.save("User", user);
                session.find(User.class, newUserId);
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Request to add all users failed" + ex);
        }
    }

    @Override
    public boolean assignRole(User user, Role role) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            user = session.find(User.class, user.getUserId());
            role = session.find(Role.class, role.getRoleId());

            if (user.getRoles() != null) {
                user.getRoles().add(role);
            } else {
                user.setRoles(Set.of(role));
            }

            transaction.commit();

            return true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("request to add role to user failed" + ex);
        }
    }

}
