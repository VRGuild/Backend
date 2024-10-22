package com.mtvs.devlinkbackend.ether.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EtherUpdateRequestDTO {
    private Long etherId;
    private Long amount;
    private String reason;
}
