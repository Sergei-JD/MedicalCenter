package com.itrex.java.lab.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeslotDTO extends CreateTimeslotDTO {

    private Integer timeslotId;

}
