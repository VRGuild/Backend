package com.mtvs.devlinkbackend.experience.command.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExperienceUpdateRequestDTO {
    private Long expId;
    private Long userId;
    private String cause;
    private Integer amount;
}
