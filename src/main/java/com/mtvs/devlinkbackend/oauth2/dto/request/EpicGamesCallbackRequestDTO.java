package com.mtvs.devlinkbackend.oauth2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EpicGamesCallbackRequestDTO {
    @Schema(description = "Authorization code returned from Epic Games", example = "cfd1de1a8d224203b0445fe977838d81")
    private String code;
}
