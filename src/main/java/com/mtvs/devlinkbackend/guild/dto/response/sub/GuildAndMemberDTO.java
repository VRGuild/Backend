package com.mtvs.devlinkbackend.guild.dto.response.sub;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildAndMemberDTO {
    private Long guildId;
    private String guildName;
    private String guildIntroduction;
    private Long masterUserId;
    private Integer maximumMember;
    private List<Member> guildMemberList;
}
