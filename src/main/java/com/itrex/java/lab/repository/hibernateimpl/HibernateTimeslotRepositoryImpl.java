package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;

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
            throw new RepositoryException("Timeslot table is empty");
        }
        return timeslots;
    }

    @Override
    public Timeslot getTimeslotByID(int timeslotId) throws RepositoryException {
        return null;
    }

    @Override
    public void addTimeslot(Timeslot timeslot) throws RepositoryException {

    }

    @Override
    public void deleteTimeslot(int timeslotId) throws RepositoryException {

    }
}
