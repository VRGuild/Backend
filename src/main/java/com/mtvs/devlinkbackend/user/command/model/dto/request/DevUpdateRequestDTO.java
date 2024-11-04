package com.mtvs.devlinkbackend.user.command.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevUpdateRequestDTO {
    private String nickname;
    private DevInfoRequestDTO devInfo;
}
