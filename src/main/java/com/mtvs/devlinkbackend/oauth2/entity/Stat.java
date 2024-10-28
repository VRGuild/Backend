package com.mtvs.devlinkbackend.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "STAT")
@Entity(name = "Stat")
@Getter
@Setter
@NoArgsConstructor
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAT_ID")
    private Long statId;

    @Column(name = "STAT_NAME")
    private String statName;

    @Column(name = "TOTAL_SCORE")
    private Long totalScore;

    @Column(name = "IS_EVALUATING")
    private Boolean isEvaluating;

    @ManyToOne
    @JoinColumn(name = "USER_PARTNER_ID", nullable = false)
    @JsonIgnore
    private UserPartner userPartner;

    public Stat(String statName, Long totalScore, Boolean isEvaluating) {
        this.statName = statName;
        this.totalScore = totalScore;
        this.isEvaluating = isEvaluating;
    }
}
