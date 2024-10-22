package com.mtvs.devlinkbackend.character.dto.response;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCharacterSingleResponseDTO {
    private UserCharacter data;
}
