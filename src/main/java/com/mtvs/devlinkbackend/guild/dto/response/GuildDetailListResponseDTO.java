package com.mtvs.devlinkbackend.guild.dto.response;

import com.mtvs.devlinkbackend.guild.dto.response.sub.GuildAndMemberDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildDetailListResponseDTO {
    private List<GuildAndMemberDTO> data;
}
