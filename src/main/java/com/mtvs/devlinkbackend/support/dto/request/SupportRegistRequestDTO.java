package com.mtvs.devlinkbackend.support.dto.request;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupportRegistRequestDTO {
    private Long projectId;
    private Long teamId;
}
