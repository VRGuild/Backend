package com.mtvs.devlinkbackend.guild.dto.request;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildRegistRequestDTO {
    private String guildName;
    private String guildIntroduction;
    private Long masterUserId;
    private Integer maximumMember;
}
