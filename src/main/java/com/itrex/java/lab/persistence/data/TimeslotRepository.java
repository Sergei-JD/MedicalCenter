package com.itrex.java.lab.persistence.data;

import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface TimeslotRepository extends CrudRepository<Timeslot, Integer> {
}
