package com.mtvs.devlinkbackend.user.query.model.dto.response.sub;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailResponseDTO {
    private Long userId;
    private CharacterInfoDTO characterInfo;
    private DevInfoDTO devInfoDTO;
    private Integer experienceValue;
    private Long businessId;
    private String nickname;
}
