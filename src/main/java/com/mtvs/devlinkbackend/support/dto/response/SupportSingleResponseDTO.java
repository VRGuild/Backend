package com.mtvs.devlinkbackend.support.dto.response;

import com.mtvs.devlinkbackend.support.entity.Support;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupportSingleResponseDTO {
    private Support data;
}
