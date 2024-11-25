package com.mtvs.devlinkbackend.channel.entity;

import lombok.Data;

@Data
public class Position {
    private float x;
    private float y;
    private float z;

    public Position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
