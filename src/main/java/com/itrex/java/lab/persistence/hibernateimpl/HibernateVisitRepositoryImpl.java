package com.itrex.java.lab.persistence.hibernateimpl;

import org.hibernate.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.VisitRepository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

@Deprecated
@Repository
@RequiredArgsConstructor
public class HibernateVisitRepositoryImpl implements VisitRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_VISIT_QUERY = "SELECT v FROM Visit v";

    @Override
    public List<Visit> getAllVisits() throws RepositoryException {
        List<Visit> visits;
        try {
            visits = entityManager.createQuery(FIND_ALL_VISIT_QUERY, Visit.class).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all visits.\n" + ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(Integer visitId) {
        try {
            return Optional.ofNullable(entityManager.find(Visit.class, visitId));
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get timeslot by id " + visitId + ".\n" + ex);
        }
    }

    @Override
    public Visit add(Visit visit) {
        try {
            Session session = entityManager.unwrap(Session.class);
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
            Visit visit = entityManager.find(Visit.class, visitId);

            if (visit != null) {
                entityManager.remove(visit);
                isDeleted = true;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to delete timeslot by id " + visitId + ".\n" + ex);
        }

        return isDeleted;
    }

    @Override
    public Visit update(Visit visit) {
        Visit updateVisit;
        try {
            updateVisit = entityManager.merge(visit);

            return updateVisit;
        } catch (Exception ex) {
            throw new RepositoryException("Failed to update visit.\n" + ex);
        }
    }

}
