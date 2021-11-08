package com.itrex.java.lab.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDTO {

    private Integer roleId;
    private String name;

}
