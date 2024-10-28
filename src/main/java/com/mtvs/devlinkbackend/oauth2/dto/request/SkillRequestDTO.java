package com.mtvs.devlinkbackend.oauth2.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SkillRequestDTO {
    private String skillCategory;
    private String skillName;
    private String skillLevel;
}
