package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.exception_handler.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateVisitRepositoryImpl implements VisitRepository {

    private final Session session;

    public HibernateVisitRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_VISIT_QUERY = "select v from Visit v";

    @Override
    public List<Visit> getAllVisits() throws RepositoryException {
        List<Visit> visits;
        try {
            visits = session.createQuery(FIND_ALL_VISIT_QUERY, Visit.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Request to get all visits failed" + ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(int visitId) throws RepositoryException {
        Visit visit;
        try {
            visit = session.find(Visit.class, visitId);
        } catch (Exception ex) {
            throw new RepositoryException("Request to get timeslot by id = " + visitId + " = failed");
        }

        return Optional.ofNullable(visit);
    }

    @Override
    public Visit add(Visit visit) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

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

    @Override
    public boolean deleteVisitById(int visitId) throws RepositoryException {
        Transaction transaction = null;
        boolean isDeleted = false;

        try {
            transaction = session.beginTransaction();

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
        return isDeleted;
    }

}
