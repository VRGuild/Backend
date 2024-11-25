package com.mtvs.devlinkbackend.channel.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Document
@Data
public class TileInfo {
    @Id
    private String tileId;

    @Indexed
    private String channelId;

    @NotNull
    @Field
    private Position position;

    public TileInfo(String channelId, Position position) {
        this.channelId = channelId;
        this.position = position;
    }
}
