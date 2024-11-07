package com.mtvs.devlinkbackend.ether.dto.response;

import com.mtvs.devlinkbackend.ether.dto.response.sub.UserIdAndAmountDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEtherAmountResponseDTO {
    private UserIdAndAmountDTO data;
}
