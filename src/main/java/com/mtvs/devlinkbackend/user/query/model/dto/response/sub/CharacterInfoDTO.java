package com.mtvs.devlinkbackend.user.query.model.dto.response.sub;

import com.mtvs.devlinkbackend.guild.repository.projection.Guild_GuildName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CharacterInfoDTO {
    private Guild_GuildName guildInfo;
    private String characterPicture;
}
