package com.itrex.java.lab.persistence.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {

    PATIENT,
    DOCTOR,
    ADMIN;

    @JsonValue
    public String jsonValue() {
        return name().toLowerCase();
    }
}
