package com.itrex.java.lab.persistence.data;


import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends CrudRepository<Timeslot, Integer> {
}
