package com.mtvs.devlinkbackend.channel.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Document
@Data
public class ObjectInfo {
    @Id
    private String objectId;

    private String objectName;

    private String objectClassName;

    @NotNull
    private Position position;

    @NotNull
    private Position rotator;

    @Indexed
    private String channelId;

    public ObjectInfo(String objectName, String objectClassName, Position position, Position rotator, String channelId) {
        this.objectId = null;
        this.objectName = objectName;
        this.objectClassName = objectClassName;
        this.position = position;
        this.rotator = rotator;
        this.channelId = channelId;
    }
}
