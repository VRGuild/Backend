package com.mtvs.devlinkbackend.team.dto.response;

import com.mtvs.devlinkbackend.team.entity.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamSingleReponseDTO {
    private Team data;
}
