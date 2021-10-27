package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateTimeslotRepositoryImpl implements TimeslotRepository {

    private final Session session;

    public HibernateTimeslotRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_TIMESLOT_QUERY = "select t from Timeslot t ";

    @Override
    public List<Timeslot> getAllTimeslot() throws RepositoryException {
        List<Timeslot> timeslots;
        try {
            timeslots = session.createQuery(FIND_ALL_TIMESLOT_QUERY, Timeslot.class).list();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Timeslot table is empty");
        }

        return timeslots;
    }

    @Override
    public Timeslot getTimeslotByID(int timeslotId) throws RepositoryException {
        Timeslot timeslot;
        try {
            timeslot = session.find(Timeslot.class, timeslotId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Timeslot does not exist by id = " + timeslotId);
        }

        return timeslot;
    }

    @Override
    public void addTimeslot(Timeslot timeslot) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int newTimeslotId = (Integer) session.save("Timeslot", timeslot);
            session.find(Timeslot.class, newTimeslotId);

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
    public void deleteTimeslot(int timeslotId) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Timeslot timeslot = session.find(Timeslot.class, timeslotId);

            if (timeslot != null) {
                session.delete(timeslot);
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

}
