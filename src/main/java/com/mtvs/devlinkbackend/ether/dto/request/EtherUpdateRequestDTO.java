package com.mtvs.devlinkbackend.ether.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EtherUpdateRequestDTO {
    private Long etherId;
    private Long userId;
    private Integer goldAmount;
    private Integer
            silverAmount;
    private String cause;
}
