package com.mtvs.devlinkbackend.user.query.model.dto.response;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
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
