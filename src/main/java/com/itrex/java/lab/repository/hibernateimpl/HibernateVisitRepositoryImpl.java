package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.util.HibernateUtil;
import org.hibernate.Session;

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
            throw new RepositoryException("Visit table is empty");
        }
        return visits;
    }

    @Override
    public Visit getVisitById(int visitId) throws RepositoryException {
        return HibernateUtil.getSessionFactory().openSession().get(Visit.class, visitId);
    }

    @Override
    public void addVisit(Visit visit) throws RepositoryException {

    }

    @Override
    public void deleteVisit(int visitId) throws RepositoryException {

    }
}
