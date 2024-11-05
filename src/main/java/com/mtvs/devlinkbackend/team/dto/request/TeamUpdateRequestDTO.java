package com.mtvs.devlinkbackend.team.dto.request;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamUpdateRequestDTO {
    private Long teamId;
    private String teamIntroduction;
    private Long leaderUserId;
    private List<Member> teamMemberList;
}
