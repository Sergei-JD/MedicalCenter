package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
public class HibernateTimeslotRepositoryImpl implements TimeslotRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_TIMESLOT_QUERY = "select t from Timeslot t";

    @Autowired
    public HibernateTimeslotRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Timeslot> getAllTimeslots() {
        List<Timeslot> timeslots;
        try {
            Session session = sessionFactory.getCurrentSession();
            timeslots = session.createQuery(FIND_ALL_TIMESLOT_QUERY, Timeslot.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all timeslots.\n" + ex);
        }

        return timeslots;
    }

    @Override
    public Optional<Timeslot> getTimeslotById(int timeslotId) {
        Timeslot timeslot;
        try {
            Session session = sessionFactory.getCurrentSession();
            timeslot = session.find(Timeslot.class, timeslotId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get timeslot by id " + timeslotId + ".\n" + ex);
        }

        return Optional.ofNullable(timeslot);
    }

    @Override
    public Timeslot add(Timeslot timeslot) {
        try {
            Session session = sessionFactory.getCurrentSession();
            int newTimeslotId = (Integer) session.save("Timeslot", timeslot);

            return session.find(Timeslot.class, newTimeslotId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add timeslot.\n" + ex);
        }
    }

    @Override
    public boolean deleteTimeslotById(int timeslotId) {
        boolean isDeleted = false;
        try {
            Session session = sessionFactory.getCurrentSession();
            Timeslot timeslot = session.find(Timeslot.class, timeslotId);

            if (timeslot != null) {
                session.delete(timeslot);
                isDeleted = true;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to delete timeslot by id " + timeslotId + ".\n" + ex);
        }

        return isDeleted;
    }

}
