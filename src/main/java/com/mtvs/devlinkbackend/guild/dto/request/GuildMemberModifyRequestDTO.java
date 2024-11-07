package com.mtvs.devlinkbackend.guild.dto.request;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildMemberModifyRequestDTO {
    private Long guildId;
    private Long masterUserId;
    private List<Member> guildMemberList;
}
