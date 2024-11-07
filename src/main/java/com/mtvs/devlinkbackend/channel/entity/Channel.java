package com.mtvs.devlinkbackend.channel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString
@Document(collection = "channel")
public class Channel {
    @Id
    private String channelId;

    @Field(name = "user_id")
    private Long userId;

    private List<PositionType> positionTypes; // 여러 개의 position과 type 저장

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Channel(Long userId, List<PositionType> positionTypes) {
        this.userId = userId;
        this.positionTypes = positionTypes;
    }

    public void setPositionTypes(List<PositionType> positionTypes) {
        this.positionTypes = positionTypes;
    }
}