package com.mtvs.devlinkbackend.character.entity;

import com.mtvs.devlinkbackend.common.util.converter.LongListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "USER_CHARACTER")
@Entity(name = "UserCharacter")
@Getter
@Setter
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long characterId;

    @Column(name = "GUILD_ID")
    private Long guildId;

    @Convert(converter = LongListConverter.class)
    @Column(name = "TEAM_ID_LIST")
    private List<Long> teamIdList;

    @Column(name = "CHARACTER_PICTURE", columnDefinition = "TEXT")
    private String characterPicture;

    @ElementCollection
    @CollectionTable(name = "CUSTOM_LIST", joinColumns = @JoinColumn(name = "CHARACTER_ID"))
    private List<CustomInfo> customList;

    @Column(name = "USER_ID")
    private Long userId;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public UserCharacter() {
    }

    public UserCharacter(Long guildId, List<Long> teamIdList, String characterPicture, List<CustomInfo> customList, Long userId) {
        this.guildId = guildId;
        this.teamIdList = teamIdList;
        this.characterPicture = characterPicture;
        this.customList = customList;
        this.userId = userId;
    }
}
