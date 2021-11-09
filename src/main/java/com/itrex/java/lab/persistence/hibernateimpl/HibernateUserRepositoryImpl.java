package com.itrex.java.lab.persistence.hibernateimpl;

import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernateUserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_USERS_QUERY = "select u from User u";
    private static final String DELETE_USER_ID_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "FROM User u where u.email = :email";
    private static final String SELECT_USER_BY_ROLE_QUERY =
            "SELECT u FROM User u JOIN FETCH u.roles r WHERE r.name = :name";

    @Autowired
    public HibernateUserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try(Session session = sessionFactory.openSession()) {
            users = session.createQuery(FIND_ALL_USERS_QUERY, User.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users.\n" + ex);
        }

        return users;
    }

    @Override
    public List<User> getAllUsersByRole(RoleType role) {
        List<User> users;
        try(Session session = sessionFactory.openSession()) {
            users = session.createQuery(SELECT_USER_BY_ROLE_QUERY, User.class).setParameter("name", role.name()).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users by role.\n" + role);
        }

        return users;
    }

    @Override
    public Optional<User> getUserById(int userId) {
        User user;
        try(Session session = sessionFactory.openSession()) {
            user = session.find(User.class, userId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by id " + userId + ".\n" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        User user;
        try(Session session = sessionFactory.openSession()) {
            user = (User) session.createQuery(SELECT_USER_BY_EMAIL_QUERY)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by email " + email + ".\n" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public boolean deleteUserById(int userId) {
        boolean isDeleted = false;
        try(Session session = sessionFactory.openSession()) {

            try {
                session.createSQLQuery(DELETE_USER_ID_FROM_USER_ROLE_QUERY)
                        .setParameter(1, userId).executeUpdate();
                User user = session.find(User.class, userId);
                if (user != null) {
                    session.delete(user);
                    isDeleted = true;
                }
            } catch (Exception ex) {
                throw new RepositoryException("Failed to delete user by id " + userId + ".\n" + ex);
            }
        }

        return isDeleted;
    }

    @Override
    public User add(User user) {
        try(Session session = sessionFactory.openSession()) {

            try {
                int newUserId = (Integer) session.save("User", user);
                User addedUser = session.find(User.class, newUserId);

                return addedUser;
            } catch (Exception ex) {
                throw new RepositoryException("Failed to add user.\n" + ex);
            }
        }
    }

    @Override
    public boolean assignRole(User user, Role role) {
        try(Session session = sessionFactory.openSession()) {

            try {
                user = session.find(User.class, user.getUserId());
                role = session.find(Role.class, role.getRoleId());

                if (user.getRoles() != null) {
                    user.getRoles().add(role);
                } else {
                    user.setRoles(Set.of(role));
                }

                return true;
            } catch (Exception ex) {
                throw new RepositoryException("Failed to add role to user.\n" + ex);
            }
        }
    }

}
