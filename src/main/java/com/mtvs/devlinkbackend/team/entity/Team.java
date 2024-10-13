package com.mtvs.devlinkbackend.team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "TEAM")
@Entity(name = "Team")
@NoArgsConstructor
@ToString
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "PM_ID")
    private String pmId;

    @Column(name = "TEAM_NAME", unique = true)
    private String teamName;

    @Column(name = "INTRODUCTION")
    private String introduction;

    @ElementCollection
    @CollectionTable(name = "TEAM_MEMBER", joinColumns = @JoinColumn(name = "TEAM_ID"))
    @Column(name = "MEMBER_LIST")
    private List<String> memberList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Team(String pmId, String teamName, String introduction, List<String> memberList) {
        this.pmId = pmId;
        this.teamName = teamName;
        this.introduction = introduction;
        this.memberList = memberList;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public void addMember(String memberId) {
        this.memberList.add(memberId);
    }

    public void removeMember(String memberId) {
        this.memberList.remove(memberId);
    }
}
