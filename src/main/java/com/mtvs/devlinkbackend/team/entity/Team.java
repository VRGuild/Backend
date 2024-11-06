package com.mtvs.devlinkbackend.team.entity;

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

    @Column(name = "LEADER_USER_ID")
    private Long leaderUserId;

    @Column(name = "TEAM_INTRODUCTION")
    private String teamIntroduction;

    @Convert(converter = LongListConverter.class)
    @Column(name = "TEAM_MEMBER_LIST", columnDefinition = "TEXT")
    private List<Long> teamMemberList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Team(Long leaderUserId, String teamIntroduction, List<Long> teamMemberList) {
        this.leaderUserId = leaderUserId;
        this.teamIntroduction = teamIntroduction;
        this.teamMemberList = teamMemberList;
    }

    public void setLeaderUserId(Long leaderUserId) {
        this.leaderUserId = leaderUserId;
    }

    public void setTeamIntroduction(String teamIntroduction) {
        this.teamIntroduction = teamIntroduction;
    }

    public void setTeamMemberList(List<Long> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }
}
