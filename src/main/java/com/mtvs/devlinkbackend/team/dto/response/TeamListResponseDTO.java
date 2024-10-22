package com.mtvs.devlinkbackend.team.dto.response;

import com.mtvs.devlinkbackend.team.entity.Team;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamListResponseDTO {
    private List<Team> data;
}
