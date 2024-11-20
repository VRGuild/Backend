package com.mtvs.devlinkbackend.team.dto.response.support;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamSupportResponseDTO {
    private Long teamId;
    private Integer experienceValue;
    private String nickname;
    private List<RadarPointDTO> radarPoints;
    private List<String> portfolioList;
    private List<TeamMemberDTO> members;
    private String teamIntroduction;
}
