//package com.mtvs.devlinkbackend.crud;
//
//import com.mtvs.devlinkbackend.common.model.AcceptStatus;
//import com.mtvs.devlinkbackend.member.command.model.entity.Member;
//import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
//import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
//import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
//import com.mtvs.devlinkbackend.team.service.TeamService;
//import com.mtvs.devlinkbackend.team.service.TeamViewService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.stream.Stream;
//
//@SpringBootTest
//@Transactional
//public class TeamCRUDTest {
//
//    @Autowired
//    private TeamService teamService;
//    @Autowired
//    private TeamViewService teamViewService;
//
//    private static Stream<Arguments> newTeam() {
//        return Stream.of(
//                Arguments.of(new TeamRegistRequestDTO(
//                        "소개00",
//                        1L), "계정2"),
//                Arguments.of(new TeamRegistRequestDTO("소개00",
//                        1L), "계정2")
//        );
//    }
//
//    private static Stream<Arguments> updatedTeam() {
//        return Stream.of(
//                Arguments.of(new TeamUpdateRequestDTO(
//                        1L,
//                        "소개0",
//                        1L,
//                        List.of(new Member(), new Member())), "계정1"),
//                Arguments.of(new TeamUpdateRequestDTO(
//                        1L,
//                        "소개0",
//                        1L,
//                        List.of(new Member(), new Member())), "계정2")
//        );
//    }
//
//    private static Stream<Arguments> modifiedTeam() {
//        return Stream.of(
//                Arguments.of(new TeamMemberModifyRequestDTO(
//                        1L,
//                        1L,
//                        List.of(
//                                new Member(
//                                        "Team",
//                                        1L,
//                                        1L,
//                                        "지원 동기1",
//                                        AcceptStatus.ACCEPTED
//                                ),
//                                new Member(
//                                        "Team",
//                                        1L,
//                                        1L,
//                                        "지원 동기1",
//                                        AcceptStatus.REJECTED
//                                ))), "계정1"),
//                Arguments.of(new TeamMemberModifyRequestDTO(
//                        2L,
//                        1L,
//                        List.of(
//                                new Member(
//                                        "Team",
//                                        1L,
//                                        1L,
//                                        "지원 동기1",
//                                        AcceptStatus.ACCEPTED
//                                ),
//                                new Member(
//                                        "Team",
//                                        1L,
//                                        1L,
//                                        "지원 동기1",
//                                        AcceptStatus.REJECTED
//                                ))), "계정2")
//        );
//    }
//
//    @DisplayName("팀 추가 테스트")
//    @ParameterizedTest
//    @MethodSource("newTeam")
//    @Order(0)
//    public void testCreateTeam(TeamRegistRequestDTO questionRegistRequestDTO, String accountId) {
//        Assertions.assertDoesNotThrow(() -> teamService.registTeam(questionRegistRequestDTO));
//    }
//
//    @DisplayName("PK로 팀 조회 테스트")
//    @ValueSource(longs = {1,2})
//    @ParameterizedTest
//    @Order(1)
//    public void testFindTeamByTeamId(long teamId) {
//        Assertions.assertDoesNotThrow(() ->
//                System.out.println("Team = " + teamViewService.findTeamByTeamId(teamId)));
//    }
//
//    @DisplayName("계정 ID가 멤버인 팀 조회 테스트")
//    @ValueSource(strings = {"계정1", "계정2"})
//    @ParameterizedTest
//    @Order(3)
//    public void testFindTeamsByMemberId(String memberId) {
//        Assertions.assertDoesNotThrow(() ->
//                System.out.println("Team = " + teamViewService.findByAccountIdInTeam(memberId)));
//    }
//
//    @DisplayName("팀 수정 테스트")
//    @MethodSource("updatedTeam")
//    @ParameterizedTest
//    @Order(5)
//    public void testUpdateTeam(TeamUpdateRequestDTO questionUpdateRequestDTO, String accountId) {
//        Assertions.assertDoesNotThrow(() ->
//                System.out.println(teamService.updateTeam(questionUpdateRequestDTO)));
//    }
//
//    @DisplayName("팀 멤버 추가 테스트")
//    @MethodSource("modifiedTeam")
//    @ParameterizedTest
//    @Order(5)
//    public void testAddMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
//        Assertions.assertDoesNotThrow(() ->
//                System.out.println(teamService.applyMemberToTeam(teamMemberModifyRequestDTO)));
//    }
//
//    @DisplayName("팀 멤버 삭제 테스트")
//    @MethodSource("modifiedTeam")
//    @ParameterizedTest
//    @Order(5)
//    public void testRemoveMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
//        Assertions.assertDoesNotThrow(() ->
//                System.out.println(teamService.removeMemberToTeam(teamMemberModifyRequestDTO)));
//    }
//
//    @DisplayName("팀 삭제 테스트")
//    @ValueSource(longs = {0,1})
//    @ParameterizedTest
//    @Order(8)
//    public void testDeleteTeam(long teamId) {
//        Assertions.assertDoesNotThrow(() ->
//                teamService.deleteTeam(teamId));
//    }
//}
