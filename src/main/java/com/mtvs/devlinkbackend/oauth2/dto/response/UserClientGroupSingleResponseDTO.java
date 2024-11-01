package com.mtvs.devlinkbackend.oauth2.dto.response;

import com.mtvs.devlinkbackend.oauth2.entity.Business;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClientGroupSingleResponseDTO {
    private Business data;
}
