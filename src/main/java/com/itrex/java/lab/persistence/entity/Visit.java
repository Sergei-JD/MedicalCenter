package com.itrex.java.lab.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "visit", schema = "PUBLIC")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Integer visitId;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private User doctor;

    @OneToOne
    @JoinColumn(name = "timeslot_id", referencedColumnName = "timeslot_id")
    private Timeslot timeslot;

    @Column(name = "comment", nullable = true)
    private String comment;

}
