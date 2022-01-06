package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    private static final long serialVersionUID = 1L;
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    @Override
    public String toString() {
        return "{ timestamp: " + this.timestamp + ", \n status: " + this.status +
                ", \n error : " + this.error + ", \n message : " + this.message +
                ", \n path : " + this.path + " }";
    }

}
