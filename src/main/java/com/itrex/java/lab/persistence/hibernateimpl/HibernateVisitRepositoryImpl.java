package com.itrex.java.lab.persistence.hibernateimpl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.VisitRepository;

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
            throw new RepositoryException("Failed to get all visits!", ex);
        }

        return visits;
    }

    @Override
    public Optional<Visit> getVisitById(Integer visitId) {
        try {
            return Optional.ofNullable(entityManager.find(Visit.class, visitId));
        } catch (Exception ex) {
            throw new RepositoryException(String.format("Failed to get visit by id: %d!", visitId), ex);
        }
    }

    @Override
    public Visit add(Visit visit) {
        try {
            Session session = entityManager.unwrap(Session.class);
            int newVisitId = (Integer) session.save("Visit", visit);

            return session.find(Visit.class, newVisitId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add visit!", ex);
        }
    }

    @Override
    public Visit update(Visit visit) {
        Visit updateVisit;
        try {
            updateVisit = entityManager.merge(visit);

            return updateVisit;
        } catch (Exception ex) {
            throw new RepositoryException("Failed to update visit!", ex);
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
            throw new RepositoryException(String.format("Failed to delete timeslot by id: %d!", visitId), ex);
        }

        return isDeleted;
    }

}
