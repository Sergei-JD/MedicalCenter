package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.itrex.java.lab.persistence.repository.VisitRepository;

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
        try {
            Session session = sessionFactory.getCurrentSession();
            visits = session.createQuery(FIND_ALL_VISIT_QUERY, Visit.class).list();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all visits.\n" + ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(Integer visitId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return Optional.ofNullable(session.find(Visit.class, visitId));
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get timeslot by id " + visitId + ".\n" + ex);
        }
    }

    @Override
    public Visit add(Visit visit) {
        try {
            Session session = sessionFactory.getCurrentSession();
            int newVisitId = (Integer) session.save("Visit", visit);

            return session.find(Visit.class, newVisitId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add visit.\n" + ex);
        }
    }

    @Override
    public boolean deleteVisitById(Integer visitId) {
        boolean isDeleted = false;
        try {
            Session session = sessionFactory.getCurrentSession();
            Visit visit = session.find(Visit.class, visitId);

            if (visit != null) {
                session.delete(visit);
                isDeleted = true;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to delete timeslot by id " + visitId + ".\n" + ex);
        }

        return isDeleted;
    }

    @Override
    public void update(Visit visit) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(visit);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to update visit.\n" + ex);
        }
    }

}
