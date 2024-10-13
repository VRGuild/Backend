package com.mtvs.devlinkbackend.reply.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtvs.devlinkbackend.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "REPLY")
@Entity(name = "Reply")
@Getter
@NoArgsConstructor
@ToString(exclude = "question") // request 필드를 toString에서 제외
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long replyId;

    @Column(name = "CONTENT")
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @JsonIgnore
    private Question question;

    public Reply(String content, String accountId, Question question) {
        this.content = content;
        this.accountId = accountId;
        this.question = question;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
