package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.repository.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateVisitRepositoryImpl implements VisitRepository {

    private final Session session;

    public HibernateVisitRepositoryImpl(Session session) {
        this.session = session;
    }

    private static final String FIND_ALL_VISIT_QUERY = "select v from Visit v ";

    @Override
    public List<Visit> getAllVisit() throws RepositoryException {
        List<Visit> visits;
        try {
            visits = session.createQuery(FIND_ALL_VISIT_QUERY, Visit.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Visit table is empty");
        }

        return visits;
    }

    @Override
    public Visit getVisitById(int visitId) throws RepositoryException {
        Visit visit;
        try {
            visit = session.find(Visit.class, visitId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RepositoryException("Visit does not exist by id = " + visitId);
        }

        return visit;
    }

    @Override
    public void add(Visit visit) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int newVisitId = (Integer) session.save("Visit", visit);
            session.find(Visit.class, newVisitId);

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
    public boolean deleteVisitById(int visitId) throws RepositoryException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Visit visit = session.find(Visit.class, visitId);

            if (visit != null) {
                session.delete(visit);
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            throw new RepositoryException(ex.getMessage());
        }
        return false;
    }

}
