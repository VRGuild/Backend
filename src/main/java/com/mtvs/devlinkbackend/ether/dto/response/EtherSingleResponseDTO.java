package com.mtvs.devlinkbackend.ether.dto.response;

import com.mtvs.devlinkbackend.ether.entity.Ether;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EtherSingleResponseDTO {
    private Ether data;
}
