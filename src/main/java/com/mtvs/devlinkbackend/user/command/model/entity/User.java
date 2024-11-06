package com.mtvs.devlinkbackend.user.command.model.entity;

import com.mtvs.devlinkbackend.common.util.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "USER")
@Entity(name = "User")
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EPIC_ACCOUNT_ID", unique = true)
    private String epicAccountId;

    @Column(name = "STEAM_ACCOUNT_ID", unique = true)
    private String steamAccountId;

    @Column(name = "CHARACTER_ID", unique = true)
    private Long characterId;

    @Column(name = "DEV_ID")
    private Long devId;

    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "NICKNAME")
    private String nickname;

    @Convert(converter = StringListConverter.class)
    @Column(name = "CHANNEL_LIST", columnDefinition = "TEXT")
    private List<String> channelList;

    @Column(name = "EXPERIENCE_VALUE")
    private Integer experienceValue;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public User(String epicAccountId) {
        this.epicAccountId = epicAccountId;
    }

    public User(String epicAccountId, String steamAccountId, Long characterId, String nickname) {
        this.epicAccountId = epicAccountId;
        this.steamAccountId = steamAccountId;
        this.characterId = characterId;
        this.nickname = nickname;
    }
}
