package com.mtvs.devlinkbackend.user.query.model.dto.response;

import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.NicknameAndDevInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevPagingResponseDTO {
    private List<NicknameAndDevInfoDTO> data;
    private Integer totalPages;
}
