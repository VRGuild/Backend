package com.mtvs.devlinkbackend.team.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamUpdateRequestDTO {
    private Long teamId;
    private String teamName;
    private String introduction;
    private List<String> memberList;
}
