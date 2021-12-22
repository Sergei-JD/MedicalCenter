package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class VisitHistoryDTO {

    private Integer visitId;
    private String comment;

}
