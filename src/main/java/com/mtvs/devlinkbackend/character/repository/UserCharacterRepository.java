package com.mtvs.devlinkbackend.character.repository;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {
    UserCharacter findByAccountId(String accountId);
    void deleteByAccountId(String accountId);
}
