package com.mtvs.devlinkbackend.oauth2.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClientGroupConvertRequestDTO {
    private String purpose = "UserClientGroup";
    private String clientType;
    private String groupName;
    private String managerName;
    private String managerPhone;
}

