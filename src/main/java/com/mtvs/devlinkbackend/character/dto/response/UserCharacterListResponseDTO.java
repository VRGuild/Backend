package com.mtvs.devlinkbackend.character.dto.response;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCharacterListResponseDTO {
    private List<UserCharacter> data;
}
