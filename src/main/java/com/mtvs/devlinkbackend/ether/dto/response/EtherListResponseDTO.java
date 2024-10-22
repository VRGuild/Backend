package com.mtvs.devlinkbackend.ether.dto.response;

import com.mtvs.devlinkbackend.ether.entity.Ether;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EtherListResponseDTO {
    private List<Ether> data;
}
