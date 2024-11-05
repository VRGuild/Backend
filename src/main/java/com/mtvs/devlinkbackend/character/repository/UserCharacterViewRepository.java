package com.mtvs.devlinkbackend.character.repository;

import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCharacterViewRepository extends JpaRepository<UserCharacter, Long> {
    UserCharacter findByUserId(Long userId);
}
