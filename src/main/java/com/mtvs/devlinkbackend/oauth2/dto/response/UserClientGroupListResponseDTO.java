package com.mtvs.devlinkbackend.oauth2.dto.response;

import com.mtvs.devlinkbackend.oauth2.entity.Business;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClientGroupListResponseDTO {
    private List<Business> data;
}
