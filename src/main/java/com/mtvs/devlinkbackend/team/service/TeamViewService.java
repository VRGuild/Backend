package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.repository.TeamViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;

@Service
public class TeamViewService {
    private final TeamViewRepository teamViewRepository;
    private final UserViewRepository userViewRepository;
    private final UserViewService userViewService;

    public TeamViewService(TeamViewRepository teamViewRepository, UserViewRepository userViewRepository, UserViewService userViewService) {
        this.teamViewRepository = teamViewRepository;
        this.userViewRepository = userViewRepository;
        this.userViewService = userViewService;
    }

    public TeamSingleReponseDTO findTeamByTeamId(Long teamId) {
        TeamSingleReponseDTO team  = new TeamSingleReponseDTO(teamViewRepository.findById(teamId).orElse(null));
        for (Long memberId : team.getData().getTeamMemberList()) {
            System.out.println("Member ID type: " + memberId.getClass().getName());
        }
        return team;
    }

    public TeamListResponseDTO findTeamsByLeaderAccountId(String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        return new TeamListResponseDTO(teamViewRepository.findTeamsByLeaderUserId(user.getUserId()));
    }

    public TeamListResponseDTO findByMemberIdInTeam(Long memberUserId) {
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(memberUserId));
    }

    public TeamListResponseDTO findByAccountIdInTeam(String accountId) {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(foundUser.getUserId()));
    }
}
