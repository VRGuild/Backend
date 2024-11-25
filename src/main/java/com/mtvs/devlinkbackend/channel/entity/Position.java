package com.mtvs.devlinkbackend.channel.entity;

import lombok.Data;

@Data
public class Position {
    private float x;
    private float y;
    private float z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
