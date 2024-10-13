package com.mtvs.devlinkbackend.team.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamMemberModifyRequestDTO {
    private Long teamId;
    private List<String> newMemberList;
}
