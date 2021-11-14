package com.itrex.java.lab.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class TimeslotDTO extends CreateTimeslotDTO {

    private Integer timeslotId;

}
