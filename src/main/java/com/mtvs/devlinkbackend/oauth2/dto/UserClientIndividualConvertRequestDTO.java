package com.mtvs.devlinkbackend.oauth2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClientIndividualConvertRequestDTO {
    private String purpose = "UserClientIndividual";
    private String name;
    private String phone;
}
