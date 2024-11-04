package com.mtvs.devlinkbackend.user.query.model.dto.response;

import com.mtvs.devlinkbackend.user.command.model.entity.Business;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessSingleResponseDTO {
    private Business data;
}
