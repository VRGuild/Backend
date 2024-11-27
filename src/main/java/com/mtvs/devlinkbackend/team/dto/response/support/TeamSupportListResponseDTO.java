package com.mtvs.devlinkbackend.team.dto.response.support;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamSupportListResponseDTO {
    private List<TeamSupportResponseDTO> result;
}
