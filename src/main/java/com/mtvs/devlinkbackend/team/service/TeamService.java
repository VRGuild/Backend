package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.service.MemberService;
import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.repository.TeamRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberService memberService;
    private final UserViewService userViewService;

    public TeamService(TeamRepository teamRepository, MemberService memberService, UserViewService userViewService) {
        this.teamRepository = teamRepository;
        this.memberService = memberService;
        this.userViewService = userViewService;
    }

    @Transactional
    public TeamSingleReponseDTO registTeam(TeamRegistRequestDTO teamRegistRequestDTO) {

        List<Long> initialTeamMemberList = new ArrayList<>();
        return new TeamSingleReponseDTO(teamRepository.save(new Team(
                teamRegistRequestDTO.getLeaderUserId(),
                teamRegistRequestDTO.getTeamIntroduction(),
                initialTeamMemberList
        )));
    }

    @Transactional
    public TeamSingleReponseDTO updateTeam(TeamUpdateRequestDTO teamUpdateRequestDTO) {
        Optional<Team> team = teamRepository.findById(teamUpdateRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            foundTeam.setTeamIntroduction(teamUpdateRequestDTO.getTeamIntroduction());
            foundTeam.setTeamMemberList(
                    teamUpdateRequestDTO.getTeamMemberList().stream()
                            .map(Member::getMemberId).toList());

            return new TeamSingleReponseDTO(foundTeam);
        } else throw new IllegalArgumentException("해당 Team은 존재하지 않음");
    }

    @Transactional
    public TeamSingleReponseDTO applyMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
        Long userId = userViewService.findUserIdByEpicAccountId(accountId);
        Optional<Team> team = teamRepository.findById(teamMemberModifyRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            // Member로 등록되어있는지 확인
            if(!memberService.isMemberExist(foundTeam.getLeaderUserId(), userId, foundTeam.getTeamId(), "team") && !Objects.equals(foundTeam.getLeaderUserId(), userId)) {
                // Member로 등록 안되어있으면 Member에 추가
                Member member = new Member("team", foundTeam.getLeaderUserId(), userId,"",foundTeam.getTeamId(), AcceptStatus.PENDING);
                memberService.regist(member);
                // TeamMemberList에 추가
                List<Long> teamMemberList = foundTeam.getTeamMemberList();
                teamMemberList.add(userId);
                foundTeam.setTeamMemberList(teamMemberList);
                return new TeamSingleReponseDTO(foundTeam);
            }

        } else return null;
        return null;
    }

    @Transactional
    public TeamSingleReponseDTO removeMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO) {
        Optional<Team> team = teamRepository.findById(teamMemberModifyRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            List<Long> memberIdListToRemove = teamMemberModifyRequestDTO.getTeamMemberList().stream()
                    .map(Member::getMemberId).toList();
            foundTeam.getTeamMemberList().removeAll(memberIdListToRemove);

            memberService.deleteAll(memberIdListToRemove);

            return new TeamSingleReponseDTO(foundTeam);
        } else return null;
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}
