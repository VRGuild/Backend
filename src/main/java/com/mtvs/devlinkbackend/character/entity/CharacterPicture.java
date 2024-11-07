package com.mtvs.devlinkbackend.character.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CharacterPicture {
    @Column(name = "RED")
    private Float r;

    @Column(name = "GREEN")
    private Float g;

    @Column(name = "BLUE")
    private Float b;

    @Column(name = "ALPHA")
    private Float a;

    public CharacterPicture(Float r, Float g, Float b, Float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
