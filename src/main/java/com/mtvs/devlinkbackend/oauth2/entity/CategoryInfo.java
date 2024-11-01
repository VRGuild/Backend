package com.mtvs.devlinkbackend.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Table(name = "CATEGORY_INFO")
@Entity(name = "CategoryInfo")
@Getter
@Setter
@ToString(exclude = "dev")
@NoArgsConstructor
public class CategoryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_INFO_ID")
    private Long categoryInfoId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "IS_EVALUATED")
    private Boolean isEvaluated;

    @OneToMany(mappedBy = "categoryInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Evaluation> categoryPointList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEV_ID", nullable = false)
    @JsonIgnore
    private Dev dev;

    public CategoryInfo(String categoryName, List<Evaluation> categoryPointList) {
        this.categoryName = categoryName;
        this.isEvaluated = false;
        this.categoryPointList = categoryPointList;
    }
}

