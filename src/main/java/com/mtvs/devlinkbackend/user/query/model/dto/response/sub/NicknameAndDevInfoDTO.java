package com.mtvs.devlinkbackend.user.query.model.dto.response.sub;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NicknameAndDevInfoDTO {
    private String nickname;
    private Dev devInfo;
}
