package com.mtvs.devlinkbackend.oauth2.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPartnerRequestDTO {
    private String purpose = "UserClientPartner";
    private String nickname;
    private String name;
    private String email;
    private String phone;
    private List<String> portfolioList;
    private String experience;
    private Map<String, Integer> skillSet;
    private String message;
}
