package com.mtvs.devlinkbackend.guild.dto.response;

import com.mtvs.devlinkbackend.guild.dto.response.sub.GuildAndMemberDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildDetailSingleResponseDTO {
    private GuildAndMemberDTO data;
}
