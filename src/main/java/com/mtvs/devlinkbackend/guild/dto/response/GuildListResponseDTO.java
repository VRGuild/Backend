package com.mtvs.devlinkbackend.guild.dto.response;

import com.mtvs.devlinkbackend.guild.entity.Guild;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildListResponseDTO {
    private List<Guild> data;
}
