package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateTimeslotRepositoryImpl implements TimeslotRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_TIMESLOT_QUERY = "select t from Timeslot t";

    @Autowired
    public HibernateTimeslotRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Timeslot> getAllTimeslots() throws RepositoryException {
        List<Timeslot> timeslots;
        try(Session session = sessionFactory.openSession()) {
            timeslots = session.createQuery(FIND_ALL_TIMESLOT_QUERY, Timeslot.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all timeslots failed" + ex);
        }

        return timeslots;
    }

    @Override
    public Optional<Timeslot> getTimeslotById(int timeslotId) throws RepositoryException {
        Timeslot timeslot;
        try(Session session = sessionFactory.openSession()) {
            timeslot = session.find(Timeslot.class, timeslotId);
        } catch (Exception ex) {
            throw new RepositoryException("Request to get timeslot by id = " + timeslotId + " = failed" + ex);
        }

        return Optional.ofNullable(timeslot);
    }

    @Override
    public Timeslot add(Timeslot timeslot) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                int newTimeslotId = (Integer) session.save("Timeslot", timeslot);
                Timeslot addedTimeslot = session.find(Timeslot.class, newTimeslotId);

                transaction.commit();

                return addedTimeslot;
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RepositoryException("Request to add timeslot failed" + ex);
            }
        }
    }

    @Override
    public boolean deleteTimeslotById(int timeslotId) throws RepositoryException {
        Transaction transaction = null;
        boolean isDeleted = false;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Timeslot timeslot = session.find(Timeslot.class, timeslotId);

            if (timeslot != null) {
                session.delete(timeslot);
                isDeleted = true;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException("Request to delete timeslot by id " + timeslotId + " failed" + ex);
        }

        return isDeleted;
    }

}
