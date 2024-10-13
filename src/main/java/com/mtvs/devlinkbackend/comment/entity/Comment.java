package com.mtvs.devlinkbackend.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtvs.devlinkbackend.request.entity.Request;
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
@ToString(exclude = "request") // request 필드를 toString에서 제외
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
    @JoinColumn(name = "REQUEST_ID", nullable = false)
    @JsonIgnore
    private Request request;

    public Comment(String content, String accountId, Request request) {
        this.content = content;
        this.accountId = accountId;
        this.request = request;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
