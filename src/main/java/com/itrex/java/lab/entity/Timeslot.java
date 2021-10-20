package com.itrex.java.lab.entity;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Timeslot {

    private Integer timeslotId;
    private Time startTime;
    private Date date;
    private Integer office;

    public Integer getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(Integer timeslotId) {
        this.timeslotId = timeslotId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOffice() {
        return office;
    }

    public void setOffice(Integer office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "timeslotId=" + timeslotId +
                ", startTime=" + startTime +
                ", date=" + date +
                ", office=" + office +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return timeslotId.equals(timeslot.timeslotId) && startTime.equals(timeslot.startTime) && date.equals(timeslot.date) && office.equals(timeslot.office);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeslotId, startTime, date, office);
    }

}
