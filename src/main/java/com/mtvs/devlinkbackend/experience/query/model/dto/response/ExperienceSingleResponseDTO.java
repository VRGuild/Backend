package com.mtvs.devlinkbackend.experience.query.model.dto.response;

import com.mtvs.devlinkbackend.experience.command.model.entity.Experience;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExperienceSingleResponseDTO {
    private Experience data;
}
