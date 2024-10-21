package com.mtvs.devlinkbackend.support.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "SUPPORT")
@Entity(name = "Support")
@NoArgsConstructor
@Getter
@ToString
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPORT_ID")
    private Long supportId;

    @Column(name = "PROJECT_ID", nullable = false)
    private Long projectId;

    @Column(name = "TEAM_ID", nullable = false)
    private Long teamId;

    @Column(name = "SUPPORT_CONFIRMATION")
    private String supportConfirmation; // 상태 : waiting, accepted, rejected

    public Support(Long projectId, Long teamId, String supportConfirmation) {
        this.projectId = projectId;
        this.teamId = teamId;
        this.supportConfirmation = supportConfirmation;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setSupportConfirmation(String supportConfirmation) {
        this.supportConfirmation = supportConfirmation;
    }
}
