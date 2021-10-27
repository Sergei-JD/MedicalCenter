package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateUserRepositoryImpl implements UserRepository {

    private final Session session;

    public HibernateUserRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_USERS_QUERY = "select u from User u ";
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
            throw new RepositoryException("Failed to get all users!");
        }

        return users;
    }

    @Override
    public List<User> getAllUserByRole(String role) throws RepositoryException {
        List<User> users;
        try {
            users = session.createQuery(SELECT_USER_BY_ROLE_QUERY, User.class).setParameter("name", role).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all users by role!" + role);
        }

        return users;
    }

    @Override
    public User getUserById(int userId) throws RepositoryException {
        User user;
        try {
            user = session.find(User.class, userId);
            Role next = user.getRoles().iterator().next();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("User does not exist by id = " + userId);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws RepositoryException {
        User user;
        try {
            user = (User) session.createQuery(SELECT_USER_BY_EMAIL_QUERY)
                    .setParameter("email", email)
                    .uniqueResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("User does not exist by email = " + email);
        }

        return user;
//        try {
//            transaction = session.beginTransaction();
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//
//            CriteriaQuery<User> cr = cb.createQuery(User.class);
//            Root<User> root = cr.from(User.class);
//            cr.select(root).where(cb.equal(root.get("email"), email));
//
//            Query query = session.createQuery(cr);
//            query.setMaxResults(1);
//            List<User> result = query.getResultList();
//
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RepositoryException("Can't find user by email " + email);
//        }
//
//        return ;
    }

    @Override
    public boolean deleteUserById(int userId) throws RepositoryException {
        boolean flag;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.createSQLQuery(DELETE_USER_ID_FROM_USER_ROLE_QUERY).setParameter(1, userId).executeUpdate();
            session.createSQLQuery(DELETE_USER_ID_FROM_USER_QUERY).setParameter(1, userId).executeUpdate();

            User user = session.find(User.class, userId);
            if (user != null) {
                session.delete(user);
                flag = (null == session.find(User.class, userId));
            } else {
                flag = false;
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Can't delete user with id " + userId);
        }

        return flag;
    }

    @Override
    public void add(User user) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            int newUserId = (Integer) session.save("User", user);
            session.find(User.class, newUserId);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }
    //!!!!!!!!!
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
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
    }

    @Override
    public boolean assignRole(User user, Role role) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = session.find(User.class, user.getUserId());
            role = session.find(Role.class, role.getRoleId());
            user.getRoles().add(role);

            transaction.commit();

            return true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(ex.getMessage());
        }
    }

}
