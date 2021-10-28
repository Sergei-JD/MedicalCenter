package com.itrex.java.lab.entity;

import javax.persistence.*;

import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "visit", schema = "public")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Integer visitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    private User doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id", referencedColumnName = "timeslot_id")
    private Timeslot timeslot;

    @Column(name = "comment", nullable = true)
    private String comment;

}
