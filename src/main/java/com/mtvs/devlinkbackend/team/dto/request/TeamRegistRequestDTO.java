package com.mtvs.devlinkbackend.team.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamRegistRequestDTO {
    private String teamName;
    private String introduction;
    private List<String> memberList;
}
