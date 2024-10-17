package com.mtvs.devlinkbackend.character.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Table(name = "USER_CHARACTER")
@Entity(name = "UserCharacter")
@Getter
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long characterId;

    @Column(name = "ACCOUNT_ID", unique = true)
    private String accountId;

    @ElementCollection
    @CollectionTable(name = "STATUS_LIST", joinColumns = @JoinColumn(name = "CHARACTER_ID"))
    @Column(name = "STATUS")
    private List<Integer> status;

    public UserCharacter() {
    }

    public UserCharacter(String accountId, List<Integer> status) {
        this.accountId = accountId;
        this.status = status;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }
}
