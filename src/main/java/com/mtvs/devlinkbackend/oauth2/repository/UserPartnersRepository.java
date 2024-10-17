package com.mtvs.devlinkbackend.oauth2.repository;

import com.mtvs.devlinkbackend.oauth2.entity.UserPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPartnersRepository extends JpaRepository<UserPartner, Long> {
    UserPartner findUserPartnerByAccountId(String accountId);

    UserPartner findUserPartnerByPhone(String phone);

    List<UserPartner> findUserPartnersByNameContainingIgnoreCase(String name);

    List<UserPartner> findUserPartnersByNicknameContainingIgnoreCase(String nickname);

    List<UserPartner> findUserPartnersByEmail(String email);

    @Query("SELECT u FROM UserPartners u JOIN u.skillSet s WHERE KEY(s) = :skillName AND VALUE(s) >= :proficiency")
    List<UserPartner> findBySkillSetWithMinProficiency(
            @Param("skillName") String skillName, @Param("proficiency") Integer proficiency
    );

    void deleteUserByAccountId(String accountId);
}
