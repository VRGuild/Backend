package com.mtvs.devlinkbackend.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtvs.devlinkbackend.project.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "COMMENT")
@Entity(name = "Comment")
@NoArgsConstructor
@Getter
@ToString(exclude = "project") // request 필드를 toString에서 제외
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @JsonIgnore
    private Project project;

    public Comment(String content, String accountId, Project project) {
        this.content = content;
        this.accountId = accountId;
        this.project = project;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
