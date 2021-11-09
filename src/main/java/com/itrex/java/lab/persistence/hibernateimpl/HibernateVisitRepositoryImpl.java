package com.itrex.java.lab.persistence.hibernateimpl;

import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RepositoryException.class)
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
            throw new RepositoryException("Failed to get all visits.\n" + ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(int visitId) {
        Visit visit;
        try(Session session = sessionFactory.openSession()) {
            visit = session.find(Visit.class, visitId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get timeslot by id " + visitId + ".\n" + ex);
        }

        return Optional.ofNullable(visit);
    }

    @Override
    public Visit add(Visit visit) {
        try(Session session = sessionFactory.openSession()) {

            try {
                int newVisitId = (Integer) session.save("Visit", visit);
                Visit addedVisit = session.find(Visit.class, newVisitId);

                return addedVisit;
            } catch (Exception ex) {
                throw new RepositoryException("Failed to add visit.\n" + ex);
            }
        }
    }

    @Override
    public boolean deleteVisitById(int visitId) {
        boolean isDeleted = false;
        try(Session session = sessionFactory.openSession()) {

            try {
                Visit visit = session.find(Visit.class, visitId);

                if (visit != null) {
                    session.delete(visit);
                    isDeleted = true;
                }
            } catch (Exception ex) {
                throw new RepositoryException("Failed to delete timeslot by id " + visitId + ".\n" + ex);
            }
        }

        return isDeleted;
    }

}
