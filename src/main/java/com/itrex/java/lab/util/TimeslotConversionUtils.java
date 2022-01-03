package com.itrex.java.lab.util;

import java.util.Optional;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class TimeslotConversionUtils {

    public static TimeslotDTO toTimeslotDTO(Timeslot timeslot) {
        return Optional.ofNullable(timeslot)
                .map(existTimeslot -> TimeslotDTO.builder()
                        .timeslotId(timeslot.getTimeslotId())
                        .startTime(timeslot.getStartTime())
                        .date(timeslot.getDate())
                        .office(timeslot.getOffice())
                        .build())
                .orElse(null);
    }

}
