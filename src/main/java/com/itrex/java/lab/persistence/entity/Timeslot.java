package com.itrex.java.lab.persistence.entity;

import javax.persistence.*;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "timeslot", schema = "public")
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id", nullable = false)
    private Integer timeslotId;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "date")
    private Date date;

    @Column(name = "office")
    private Integer office;

}
