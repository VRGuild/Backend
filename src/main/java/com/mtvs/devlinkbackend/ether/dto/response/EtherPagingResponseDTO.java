package com.mtvs.devlinkbackend.ether.dto.response;

import com.mtvs.devlinkbackend.ether.entity.Ether;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EtherPagingResponseDTO {
    private List<Ether> data;
    private Integer totalPages;
    private Long totalEtherCnt;
}
