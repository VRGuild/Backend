package com.mtvs.devlinkbackend.user.query.model.dto.response;

import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserDetailResponseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailSingleResponseDTO {
    private UserDetailResponseDTO data;
}
