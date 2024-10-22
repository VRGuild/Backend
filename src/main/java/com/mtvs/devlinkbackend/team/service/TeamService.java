package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.team.dto.request.TeamMemberModifyRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamRegistRequestDTO;
import com.mtvs.devlinkbackend.team.dto.request.TeamUpdateRequestDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public TeamSingleReponseDTO registTeam(TeamRegistRequestDTO teamRegistRequestDTO, String accountId) {
        return new TeamSingleReponseDTO(teamRepository.save(new Team(
                accountId,
                teamRegistRequestDTO.getTeamName(),
                teamRegistRequestDTO.getIntroduction(),
                teamRegistRequestDTO.getMemberList()
        )));
    }

    public TeamSingleReponseDTO findTeamByTeamId(Long teamId) {
        return new TeamSingleReponseDTO(teamRepository.findById(teamId).orElse(null));
    }

    public TeamListResponseDTO findTeamsByPmId(String pmId) {
        return new TeamListResponseDTO(teamRepository.findTeamByPmId(pmId));
    }

    public TeamListResponseDTO findTeamsByTeamNameContaining(String teamName) {
        return new TeamListResponseDTO(teamRepository.findTeamsByTeamNameContaining(teamName));
    }

    public TeamListResponseDTO findTeamsByMemberIdContaining(String memberId) {
        return new TeamListResponseDTO(teamRepository.findTeamsByMemberIdContaining(memberId));
    }

    @Transactional
    public TeamSingleReponseDTO updateTeam(TeamUpdateRequestDTO teamUpdateRequestDTO, String accountId) {
        Optional<Team> team = teamRepository.findById(teamUpdateRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            if(foundTeam.getPmId().equals(accountId)) {
                foundTeam.setTeamName(teamUpdateRequestDTO.getTeamName());
                foundTeam.setIntroduction(teamUpdateRequestDTO.getIntroduction());
                foundTeam.setMemberList(teamUpdateRequestDTO.getMemberList());
                return new TeamSingleReponseDTO(foundTeam);
            } else throw new IllegalArgumentException("pm이 아닌 계정으로 team 수정 시도");
        } else throw new IllegalArgumentException("해당 Team은 존재하지 않음");
    }

    @Transactional
    public TeamSingleReponseDTO addMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
        Optional<Team> team = teamRepository.findById(teamMemberModifyRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            if(foundTeam.getPmId().equals(accountId)) {
                foundTeam.getMemberList().addAll(teamMemberModifyRequestDTO.getNewMemberList());
                return new TeamSingleReponseDTO(foundTeam);
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    @Transactional
    public TeamSingleReponseDTO removeMemberToTeam(TeamMemberModifyRequestDTO teamMemberModifyRequestDTO, String accountId) {
        Optional<Team> team = teamRepository.findById(teamMemberModifyRequestDTO.getTeamId());
        if (team.isPresent()) {
            Team foundTeam = team.get();
            if(foundTeam.getPmId().equals(accountId)) {
                foundTeam.getMemberList().removeAll(teamMemberModifyRequestDTO.getNewMemberList());
                return new TeamSingleReponseDTO(foundTeam);
            } else throw new IllegalArgumentException("owner가 아닌 계정으로 Guild 수정 시도");
        } else return null;
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}
