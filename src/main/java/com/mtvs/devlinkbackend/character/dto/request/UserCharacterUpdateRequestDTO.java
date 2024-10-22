package com.mtvs.devlinkbackend.character.dto.request;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCharacterUpdateRequestDTO {
    private Long characterId;
    private List<Integer> status;
}