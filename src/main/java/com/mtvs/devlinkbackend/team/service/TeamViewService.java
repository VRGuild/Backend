package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.repository.TeamViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamViewService {
    private final TeamViewRepository teamViewRepository;
    private final UserViewRepository userViewRepository;

    public TeamViewService(TeamViewRepository teamViewRepository, UserViewRepository userViewRepository) {
        this.teamViewRepository = teamViewRepository;
        this.userViewRepository = userViewRepository;
    }

    public TeamSingleReponseDTO findTeamByTeamId(Long teamId) {
        return new TeamSingleReponseDTO(teamViewRepository.findById(teamId).orElse(null));
    }

    public TeamSingleReponseDTO findTeamsByLeaderUserId(Long leaderUserId) {
        return new TeamSingleReponseDTO(teamViewRepository.findTeamByLeaderUserId(leaderUserId));
    }

    public TeamListResponseDTO findByMemberIdInTeam(Long memberUserId) {
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(memberUserId));
    }

    public TeamListResponseDTO findByAccountIdInTeam(String accountId) {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(foundUser.getUserId()));
    }
}
