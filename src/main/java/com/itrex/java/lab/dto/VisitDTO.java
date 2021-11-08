package com.itrex.java.lab.dto;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.entity.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitDTO {

    private Integer visitId;
    private User patient;
    private User doctor;
    private Timeslot timeslot;
    private String comment;

}
