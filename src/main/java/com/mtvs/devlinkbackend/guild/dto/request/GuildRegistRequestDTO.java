package com.mtvs.devlinkbackend.guild.dto.request;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildRegistRequestDTO {
    private String guildName;
    private String introduction;
    private Long maximumMember;
    private List<String> memberList;
}
