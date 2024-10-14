package com.mtvs.devlinkbackend.channel.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Table(name = "CHANNEL")
@Entity(name = "Channel")
@ToString
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHANNEL_ID")
    private Long channelId;

    @Column(name = "JSON_DATA", columnDefinition = "TEXT")
    private String jsonData;

    @Transient
    private List<Map<String, Object>> dataList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Long getChannelId() {
        return channelId;
    }

    public List<Map<String, Object>> getDataList() throws JsonProcessingException {
        if (this.dataList == null && this.jsonData != null) {
            this.dataList = objectMapper.readValue(this.jsonData, List.class);
        }
        return this.dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) throws JsonProcessingException {
        this.dataList = dataList;
        this.jsonData = objectMapper.writeValueAsString(dataList);
    }
}
