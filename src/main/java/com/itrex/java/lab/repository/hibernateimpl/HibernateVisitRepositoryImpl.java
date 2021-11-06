package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateVisitRepositoryImpl implements VisitRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_VISIT_QUERY = "select v from Visit v";

    @Autowired
    public HibernateVisitRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Visit> getAllVisits() throws RepositoryException {
        List<Visit> visits;
        try(Session session = sessionFactory.openSession()) {
            visits = session.createQuery(FIND_ALL_VISIT_QUERY, Visit.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all visits failed" + ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(int visitId) throws RepositoryException {
        Visit visit;
        try(Session session = sessionFactory.openSession()) {
            visit = session.find(Visit.class, visitId);
        } catch (Exception ex) {
            throw new RepositoryException("Request to get timeslot by id = " + visitId + " = failed");
        }

        return Optional.ofNullable(visit);
    }

    @Override
    public Visit add(Visit visit) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                int newVisitId = (Integer) session.save("Visit", visit);
                Visit addedVisit = session.find(Visit.class, newVisitId);

                transaction.commit();

                return addedVisit;
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RepositoryException("Request to add visit failed" + ex);
            }
        }
    }

    @Override
    public boolean deleteVisitById(int visitId) throws RepositoryException {
        boolean isDeleted = false;

        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Visit visit = session.find(Visit.class, visitId);

                if (visit != null) {
                    session.delete(visit);
                    isDeleted = true;
                }
                transaction.commit();
            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RepositoryException("Request to delete timeslot by id " + visitId + " failed" + ex);
            }
        }

        return isDeleted;
    }

}
