package com.mtvs.devlinkbackend.experience.query.model.dto.response;

import com.mtvs.devlinkbackend.experience.command.model.entity.Experience;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExperienceListResponseDTO {
    private List<Experience> data;
}
