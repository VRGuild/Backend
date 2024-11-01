package com.mtvs.devlinkbackend.oauth2.dto.response;

import com.mtvs.devlinkbackend.oauth2.entity.Dev;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPartnerSingleResponseDTO {
    private Dev data;
}
