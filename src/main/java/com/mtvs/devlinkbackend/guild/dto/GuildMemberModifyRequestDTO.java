package com.mtvs.devlinkbackend.guild.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildMemberModifyRequestDTO {
    private Long guildId;
    private List<String> newMemberList;
}
