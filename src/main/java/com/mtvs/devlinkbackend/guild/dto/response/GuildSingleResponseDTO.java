package com.mtvs.devlinkbackend.guild.dto.response;

import com.mtvs.devlinkbackend.guild.entity.Guild;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuildSingleResponseDTO {
    private Guild data;
}
