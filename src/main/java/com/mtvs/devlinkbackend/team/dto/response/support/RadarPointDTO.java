package com.mtvs.devlinkbackend.team.dto.response.support;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RadarPointDTO {
    private String categoryName;
    private Double pointAvg;
}
