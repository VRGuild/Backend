package com.mtvs.devlinkbackend.user.query.model.dto.response;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevPagingResponseDTO {
    private List<Dev> data;
    private Integer totalPages;
}
