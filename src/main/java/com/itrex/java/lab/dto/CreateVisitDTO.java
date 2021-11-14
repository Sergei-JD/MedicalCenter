package com.itrex.java.lab.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Timeslot;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitDTO {

    private User patientId;
    private User doctorId;
    private Timeslot timeslotId;
    private String comment;

}
