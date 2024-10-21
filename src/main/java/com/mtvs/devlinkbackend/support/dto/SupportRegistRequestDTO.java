package com.mtvs.devlinkbackend.support.dto;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupportRegistRequestDTO {
    private Long projectId;
    private Long teamId;
}
