package com.mtvs.devlinkbackend.oauth2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String githubLink;
    private List<String> portfolioList;
    private String experience;
    @Schema(
            description = "기술 숙련도에 대한 정보. Key는 기술 이름이고, Value는 해당 기술의 숙련도입니다.",
            example = "{ \"Java\": 5, \"Spring\": 4, \"Docker\": 3 }"
    )
    private Map<String, Integer> skillSet;
    private String message;
}
