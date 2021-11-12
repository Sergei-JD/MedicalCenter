package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.UserRepository;

import java.util.Set;
import java.util.List;
import java.util.Optional;

@Repository
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
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users;
        try {
            Session session = sessionFactory.getCurrentSession();
            users = session.createQuery(FIND_ALL_USERS_QUERY, User.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users.\n" + ex);
        }

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsersByRole(RoleType role) {
        List<User> users;
        try {
            Session session = sessionFactory.getCurrentSession();
            users = session.createQuery(SELECT_USER_BY_ROLE_QUERY, User.class).setParameter("name", role).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users by role.\n" + role);
        }

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(int userId) {
        User user;
        try {
            Session session = sessionFactory.getCurrentSession();
            user = session.find(User.class, userId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by id " + userId + ".\n" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        User user;
        try {
            Session session = sessionFactory.getCurrentSession();
            user = (User) session.createQuery(SELECT_USER_BY_EMAIL_QUERY)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by email " + email + ".\n" + ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    @Transactional
    public boolean deleteUserById(int userId) {
        boolean isDeleted = false;
        try {
            Session session = sessionFactory.getCurrentSession();
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

        return isDeleted;
    }

    @Override
    @Transactional
    public User add(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            int newUserId = (Integer) session.save("User", user);

            return session.find(User.class, newUserId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add user.\n" + ex);
        }
    }

    @Override
    @Transactional
    public boolean assignRole(User user, Role role) {
        try {
            Session session = sessionFactory.getCurrentSession();
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
