package com.itrex.java.lab.dto;

import java.time.Instant;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeslotDTO {

    private Instant startTime;
    private Instant date;
    private Integer office;

}
