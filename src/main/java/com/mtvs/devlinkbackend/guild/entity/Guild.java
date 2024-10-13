package com.mtvs.devlinkbackend.guild.entity;

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
    private String ownerId;

    @Column(name = "GUILD_NAME")
    private String guildName;

    @Column(name = "INTRODUCTION")
    private String introduction;

    @Column(name = "MAXIMUM_MEMBER")
    private Long maximumMember;

    @Column(name = "CHANNEL_ID")
    private Long channelId;

    @ElementCollection
    @CollectionTable(name = "GUILD_MEMBER", joinColumns = @JoinColumn(name = "GUILD_ID"))
    private List<String> memberList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Guild(String ownerId, String guildName, String introduction, Long maximumMember, List<String> memberList) {
        this.ownerId = ownerId;
        this.guildName = guildName;
        this.introduction = introduction;
        this.maximumMember = maximumMember;
        this.memberList = memberList;
    }

    public Guild(String ownerId, String guildName, String introduction, Long maximumMember, List<String> memberList, Long channelId) {
        this.ownerId = ownerId;
        this.guildName = guildName;
        this.introduction = introduction;
        this.maximumMember = maximumMember;
        this.memberList = memberList;
        this.channelId = channelId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMaximumMember(Long maximumMember) {
        this.maximumMember = maximumMember;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public void addMemberList(String memberId) {
        this.memberList.add(memberId);
    }

    public void removeMemberList(String memberId) {
        this.memberList.remove(memberId);
    }
}
