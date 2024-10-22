package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.service.TeamService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class TeamCRUDTest {

    @Autowired
    private TeamService teamService;

    private static Stream<Arguments> newTeam() {
        return Stream.of(
                Arguments.of(new TeamRegistRequestDTO("팀 이름00", "소개00", List.of("계정1","계정3")), "계정2"),
                Arguments.of(new TeamRegistRequestDTO("팀 이름00", "소개00", List.of("계정1","계정3")), "계정2")
        );
    }

    private static Stream<Arguments> updatedTeam() {
        return Stream.of(
                Arguments.of(new TeamUpdateRequestDTO(1L,"팀 이름0", "소개0", List.of("계정2","계정3")), "계정1"),
                Arguments.of(new TeamUpdateRequestDTO(2L,"팀 이름00", "소개00", List.of("계정1","계정3")), "계정2")
        );
    }

    private static Stream<Arguments> modifiedTeam() {
        return Stream.of(
                Arguments.of(new TeamMemberModifyRequestDTO(1L, List.of("계정2","계정3")), "계정1"),
                Arguments.of(new TeamMemberModifyRequestDTO(2L, List.of("계정3","계정4")), "계정2")
        );
    }

    @DisplayName("팀 추가 테스트")
    @ParameterizedTest
    @MethodSource("newTeam")
    @Order(0)
    public void testCreateTeam(TeamRegistRequestDTO questionRegistRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() -> teamService.registTeam(questionRegistRequestDTO, accountId));
    }

    @DisplayName("PK로 팀 조회 테스트")
    @ValueSource(longs = {1,2})
    @ParameterizedTest
    @Order(1)
    public void testFindTeamByTeamId(long teamId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Team = " + teamService.findTeamByTeamId(teamId)));
    }

    @DisplayName("계정 ID가 PM인 팀 조회 테스트")
    @ValueSource(strings = {"계정1","계정2"})
    @ParameterizedTest
    @Order(2)
    public void testFindTeamByPmId(String pmId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Team = " + teamService.findTeamsByPmId(pmId)));
    }

    @DisplayName("계정 ID가 멤버인 팀 조회 테스트")
    @ValueSource(strings = {"계정1", "계정2"})
    @ParameterizedTest
    @Order(3)
    public void testFindTeamsByMemberId(String memberId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Team = " + teamService.findTeamsByMemberIdContaining(memberId)));
    }

    @DisplayName("팀 이름으로 팀 조회 테스트")
    @ValueSource(strings = {"팀 이름0", "팀 이름1"})
    @ParameterizedTest
    @Order(4)
    public void testFindTeamsByTeamNameContaining(String teamName) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println("Team = " + teamService.findTeamsByTeamNameContaining(teamName)));
    }

    @DisplayName("팀 수정 테스트")
    @MethodSource("updatedTeam")
    @ParameterizedTest
    @Order(5)
    public void testUpdateTeam(TeamUpdateRequestDTO questionUpdateRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(teamService.updateTeam(questionUpdateRequestDTO, accountId)));
    }

    @DisplayName("팀 멤버 추가 테스트")
    @MethodSource("modifiedTeam")
    @ParameterizedTest
    @Order(5)
    public void testAddMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(teamService.addMemberToTeam(teamMemberModifyRequestDTO, accountId)));
    }

    @DisplayName("팀 멤버 삭제 테스트")
    @MethodSource("modifiedTeam")
    @ParameterizedTest
    @Order(5)
    public void testRemoveMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
        Assertions.assertDoesNotThrow(() ->
                System.out.println(teamService.removeMemberToTeam(teamMemberModifyRequestDTO, accountId)));
    }

    @DisplayName("팀 삭제 테스트")
    @ValueSource(longs = {0,1})
    @ParameterizedTest
    @Order(8)
    public void testDeleteTeam(long teamId) {
        Assertions.assertDoesNotThrow(() ->
                teamService.deleteTeam(teamId));
    }
}
