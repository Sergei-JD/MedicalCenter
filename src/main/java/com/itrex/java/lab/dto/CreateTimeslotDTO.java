package com.itrex.java.lab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeslotDTO {

    @NonNull
    private Instant startTime;
    @NonNull
    private Instant date;
    @NonNull
    private Integer office;

}
