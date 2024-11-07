package com.mtvs.devlinkbackend.guild.entity;

import com.mtvs.devlinkbackend.common.util.converter.LongListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "GUILD")
@Entity(name = "Guild")
@ToString
@NoArgsConstructor
@Getter
public class Guild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUILD_ID")
    private Long guildId;

    @Column(name = "OWNER_ID")
    private Long masterUserId;

    @Column(name = "GUILD_NAME")
    private String guildName;

    @Column(name = "GUILD_INTRODUCTION")
    private String guildIntroduction;

    @Column(name = "MAXIMUM_MEMBER")
    private Integer maximumMember;

    @Convert(converter = LongListConverter.class)
    @Column(name = "GUILD_MEMBER_LIST", columnDefinition = "TEXT")
    private List<Long> guildMemberList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Guild(Long masterUserId, String guildName, String guildIntroduction, Integer maximumMember, List<Long> guildMemberList) {
        this.masterUserId = masterUserId;
        this.guildName = guildName;
        this.guildIntroduction = guildIntroduction;
        this.maximumMember = maximumMember;
        this.guildMemberList = new ArrayList<>(guildMemberList);
    }

    public void setMasterUserId(Long masterUserId) {
        this.masterUserId = masterUserId;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void setGuildIntroduction(String guildIntroduction) {
        this.guildIntroduction = guildIntroduction;
    }

    public void setMaximumMember(Integer maximumMember) {
        this.maximumMember = maximumMember;
    }

    public void setMemberList(List<Long> guildMemberList) {
        this.guildMemberList = new ArrayList<>(guildMemberList);
    }
}
