package com.mtvs.devlinkbackend.support.dto.response;

import com.mtvs.devlinkbackend.support.entity.Support;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupportListResponseDTO {
    private List<Support> data;
}
