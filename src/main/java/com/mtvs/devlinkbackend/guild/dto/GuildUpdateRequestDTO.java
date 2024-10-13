package com.mtvs.devlinkbackend.guild.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildUpdateRequestDTO {
    private Long guildId;
    private String guildName;
    private String introduction;
    private Long maximumMember;
    private List<String> memberList;
    private Long channelId;
}
