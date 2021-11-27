package com.itrex.java.lab.persistence.hibernateimpl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Deprecated
@Repository
@RequiredArgsConstructor
public class HibernateTimeslotRepositoryImpl implements TimeslotRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_TIMESLOT_QUERY = "SELECT t FROM Timeslot t";

    @Override
    public List<Timeslot> getAllTimeslots() {
        List<Timeslot> timeslots;
        try {
            timeslots = entityManager.createQuery(FIND_ALL_TIMESLOT_QUERY, Timeslot.class).getResultList();
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get all timeslots.\n" + ex);
        }

        return timeslots;
    }

    @Override
    public Optional<Timeslot> getTimeslotById(Integer timeslotId) {
        Timeslot timeslot;
        try {
            timeslot = entityManager.find(Timeslot.class, timeslotId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to get timeslot by id " + timeslotId + ".\n" + ex);
        }

        return Optional.ofNullable(timeslot);
    }

    @Override
    public Timeslot add(Timeslot timeslot) {
        try {
            Session session = entityManager.unwrap(Session.class);
            int newTimeslotId = (Integer) session.save("Timeslot", timeslot);

            return session.find(Timeslot.class, newTimeslotId);
        } catch (Exception ex) {
            throw new RepositoryException("Failed to add timeslot.\n" + ex);
        }
    }

    @Override
    public Timeslot update(Timeslot timeslot) {
        Timeslot updateTimeslot;
        try {
            updateTimeslot = entityManager.merge(timeslot);

            return updateTimeslot;
        } catch (Exception ex) {
            throw new RepositoryException("Failed to update timeslot.\n" + ex);
        }
    }

    @Override
    public boolean deleteTimeslotById(Integer timeslotId) {
        boolean isDeleted = false;
        try {
            Timeslot timeslot = entityManager.find(Timeslot.class, timeslotId);

            if (timeslot != null) {
                entityManager.remove(timeslot);
                isDeleted = true;
            }
        } catch (Exception ex) {
            throw new RepositoryException("Failed to delete timeslot by id " + timeslotId + ".\n" + ex);
        }

        return isDeleted;
    }

}
