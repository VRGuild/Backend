package com.mtvs.devlinkbackend.oauth2.dto.response;

import com.mtvs.devlinkbackend.oauth2.entity.Dev;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPartnerListResponseDTO {
    private List<Dev> data;
}
