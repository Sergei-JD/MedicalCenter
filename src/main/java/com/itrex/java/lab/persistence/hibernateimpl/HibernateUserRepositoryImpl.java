package com.itrex.java.lab.persistence.hibernateimpl;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import org.hibernate.Session;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.UserRepository;

@Deprecated
@Repository
@RequiredArgsConstructor
public class HibernateUserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_USERS_QUERY = "SELECT u FROM User u";
    private static final String DELETE_USER_ID_FROM_USER_ROLE_QUERY = "DELETE FROM user_role WHERE user_id = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "FROM User u WHERE u.email = :email";
    private static final String SELECT_USER_BY_ROLE_QUERY = "SELECT u FROM User u JOIN FETCH u.roles r WHERE r.name = :name";

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try {
            users = entityManager.createQuery(FIND_ALL_USERS_QUERY, User.class).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users!", ex);
        }

        return users;
    }

    @Override
    public List<User> getAllUsersByRole(RoleType role) {
        List<User> users;
        try {
            users = entityManager.createQuery(SELECT_USER_BY_ROLE_QUERY, User.class).setParameter("name", role).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users by role! " + role, ex);
        }

        return users;
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        User user;
        try {
            user = entityManager.find(User.class, userId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by id! " + userId, ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        User user = null;
        try {
            List<User> users = entityManager.createQuery(SELECT_USER_BY_EMAIL_QUERY, User.class)
              .setParameter("email", email)
              .getResultList();
            if (!CollectionUtils.isEmpty(users)) {
                user = users.get(0);
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get user by email! " + email, ex);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public User add(User user) {
        try {
            Session session = entityManager.unwrap(Session.class);
            int newUserId = (Integer) session.save("User", user);

            return session.find(User.class, newUserId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add user!", ex);
        }
    }

    @Override
    public boolean assignRole(User user, Role role) {
        try {
            user = entityManager.find(User.class, user.getUserId());
            role = entityManager.find(Role.class, role.getRoleId());

            if (user.getRoles() != null) {
                user.getRoles().add(role);
            } else {
                user.setRoles(Set.of(role));
            }

            return true;
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add role to user!", ex);
        }
    }

    @Override
    public User update(User user) {
        User updateUser;
        try {

            updateUser = entityManager.merge(user);

            return updateUser;
        } catch (Exception ex) {
            throw new RepositoryException("Failed to update user", ex);
        }
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        boolean isDeleted = false;
        try {
            Session session = entityManager.unwrap(Session.class);
            session.createSQLQuery(DELETE_USER_ID_FROM_USER_ROLE_QUERY)
              .setParameter(1, userId).executeUpdate();

            User user = session.find(User.class, userId);
            if (user != null) {
                session.delete(user);
                isDeleted = true;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to delete user by id! " + userId, ex);
        }

        return isDeleted;
    }

}
