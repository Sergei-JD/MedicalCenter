package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;



import java.util.List;


public class HibernateUserRepositoryImpl implements UserRepository {

    private final Session session;

    public HibernateUserRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_USERS_QUERY = "select u from User u ";
    private static final String DELETE_USER_ID_FROM_USER_QUERY = "DELETE FROM user WHERE user_id = ?";

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
        return null;
    }

    @Override
    public User getUserById(int userId) throws RepositoryException {
        User user;
        try {
            user = session.find(User.class, userId);
        } catch (Exception ex) {
            throw new RepositoryException("User does not exist by id = " + userId);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws RepositoryException {
        return null;
    }

//    @Override
//    public boolean deleteUserById(int userId) throws RepositoryException {
//        boolean flag;
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            session.createQuery(DELETE_USER_ID_FROM_USER_QUERY).setParameter(1, userId).executeUpdate();
//            User user = session.find(User.class, userId);
//            if (user != null) {
//                session.delete(user);
//                flag = (null == session.find(User.class, userId));
//            } else {
//                flag = false;
//            }
//
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RepositoryException("Can't delete user with id " + userId);
//        }
//
//        return flag;
//    }

    @Override
    public void add(User user) throws RepositoryException {


    }

    @Override
    public void addAll(List<User> users) throws RepositoryException {

    }

//    @Override
//    public boolean assignRole(User user, Role role) throws RepositoryException {
//        User userAssignRole;
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            user = session.find(User.class, user.getUserId());
//            role = session.find(Role.class, role.getRoleId());
//
//            userAssignRole = role.getUsers().stream().filter((x) -> x.getUserId() == role.getRoleId()).collect(Collectors.toList()).get(0);
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RepositoryException(ex.getMessage());
//        }
//
//        return userAssignRole.getUserId() != 0;
//    }


    @Override
    public boolean deleteUserById(int userId) throws RepositoryException {
        return false;
    }

    @Override
    public boolean assignRole(User user, Role role) throws RepositoryException {
        return false;
    }
}
