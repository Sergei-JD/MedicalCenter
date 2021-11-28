package com.itrex.java.lab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VisitHistoryDTO {

    private Integer visitId;
    private String comment;

}
