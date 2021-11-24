package com.itrex.java.lab.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeslotDTO {

    private Instant startTime;
    private Instant date;
    private Integer office;

}
