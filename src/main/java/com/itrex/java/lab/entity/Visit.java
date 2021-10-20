package com.itrex.java.lab.entity;

import java.util.Objects;

public class Visit {

    private Integer visitId;
    private Integer doctorId;
    private Integer patientId;
    private Integer timeslotId;

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(Integer timeslotId) {
        this.timeslotId = timeslotId;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitId=" + visitId +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", timeslotId=" + timeslotId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return visitId.equals(visit.visitId) && doctorId.equals(visit.doctorId) && patientId.equals(visit.patientId) && timeslotId.equals(visit.timeslotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, doctorId, patientId, timeslotId);
    }
}
