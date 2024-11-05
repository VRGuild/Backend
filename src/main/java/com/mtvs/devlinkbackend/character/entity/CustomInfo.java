package com.mtvs.devlinkbackend.character.entity;

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
public class CustomInfo {
    @Column(name = "CUSTOM_TYPE")
    private Integer customType;

    @Column(name = "CUSTOM_NAME")
    private String customName;

    @Column(name = "CUSTOM_TEXTURE")
    private String customTexture;

    @Column(name = "CUSTOM_INDEX")
    private Integer customIndex;
}
