package com.mtvs.devlinkbackend.character.entity;

import com.mtvs.devlinkbackend.util.IntegerListConverter;
import com.mtvs.devlinkbackend.util.LongListConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Table(name = "USER_CHARACTER")
@Entity(name = "UserCharacter")
@Getter
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long characterId;

    @Column(name = "ACCOUNT_ID", unique = true)
    private String accountId;

    @Column(name = "GUILD_ID")
    private Long guildId;

    @Convert(converter = LongListConverter.class)
    @Column(name = "TEAM_ID_LIST")
    private List<Long> teamIdList;

    @Column(name = "CHARACTER_PICTURE", columnDefinition = "TEXT")
    private String characterPicture;

    @ElementCollection
    @CollectionTable(name = "STATUS_LIST", joinColumns = @JoinColumn(name = "CHARACTER_ID"))
    @Column(name = "STATUS")
    private List<Integer> status;

    @Column(name = "USER_ID")
    private Long userId;

    public UserCharacter() {
    }

    public UserCharacter(String accountId, List<Integer> status) {
        this.accountId = accountId;
        this.status = status;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}
