package com.mtvs.devlinkbackend.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Occupation {
    @Column(name = "OCCUPATION_NAME")
    private String occupationName;

    @Column(name = "OCCUPATION_COUNT")
    private Integer occupationCount;
}
