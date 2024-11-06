package com.mtvs.devlinkbackend.user.query.model.dto.response.sub;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserIdAndBusinessAndNicknameDTO {
    private Long userId;
    private Business businessInfo;
    private String nickname;
}
