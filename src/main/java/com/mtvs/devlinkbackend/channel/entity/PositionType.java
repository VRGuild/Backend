package com.mtvs.devlinkbackend.channel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
public class PositionType {
    private Position position;

    @Field("type")
    private String type;

    public PositionType(Position position, String type) {
        this.position = position;
        this.type = type;
    }

    @Data
    @NoArgsConstructor
    public static class Position {
        private int x;
        private int y;
        private int z;

        public Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
