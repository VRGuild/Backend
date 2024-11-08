package com.mtvs.devlinkbackend.character.dto.request;

import com.mtvs.devlinkbackend.character.entity.CustomInfo;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCharacterUpdateRequestDTO {
    private Long userId;
    private Long guildId;
    private List<Long> teamIdList;
    private List<CustomInfo> customList;
    private String characterPicture;
}