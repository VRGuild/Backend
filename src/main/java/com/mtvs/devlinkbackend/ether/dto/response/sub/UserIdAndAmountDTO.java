package com.mtvs.devlinkbackend.ether.dto.response.sub;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserIdAndAmountDTO {
    private Long userId;
    private Integer goldAmount;
    private Integer silverAmount;
}
