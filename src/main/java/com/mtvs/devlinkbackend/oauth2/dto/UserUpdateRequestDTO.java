package com.mtvs.devlinkbackend.oauth2.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateRequestDTO {
    private String userName;
    private String email;
}
