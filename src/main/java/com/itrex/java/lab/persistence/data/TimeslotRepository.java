package com.itrex.java.lab.persistence.data;


import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Integer> {
}
